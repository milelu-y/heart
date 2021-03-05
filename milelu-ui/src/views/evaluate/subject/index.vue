<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="题目名称" prop="categoryCode">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入题目名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="题目类型" prop="subjectType">
        <el-select v-model="queryParams.subjectType" placeholder="请选择题目类型" clearable size="small">
          <el-option label="请选择字典生成" value=""/>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="cyan" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['evaluate:subject:add']"
        >新增
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['evaluate:subject:edit']"
        >修改
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['evaluate:subject:remove']"
        >删除
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['evaluate:subject:export']"
        >导出
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="subjectList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <!--      <el-table-column label="分类编码" align="center" prop="categoryCode"/>-->
      <el-table-column label="题目类别" align="center" prop="categoryName"/>
      <el-table-column label="题目类型" align="center" prop="subjectType" :formatter="subjectTypeFormatter"/>
      <el-table-column label="题目" align="center" prop="subject" :show-overflow-tooltip="true"/>
      <el-table-column label="答案" align="center" prop="answer"/>
      <el-table-column label="分值" align="center" prop="score"/>
      <!--      <el-table-column label="提选" align="center" prop="select"/>-->
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['evaluate:subject:edit']"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['evaluate:subject:remove']"
          >删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改题库对话框 -->
    <el-dialog :title="title" :visible.sync="open" @open="dialogOpenEvent" @close="dialogCloseEvent" width="1000px"
               append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-row :gutter="15">
          <el-col :span="12">
            <el-form-item label="测评分类" prop="categoryId">
              <el-select v-model="form.categoryId" placeholder="请选择测评分类" @change="CategoryChange" clearable
                         :style="{width: '100%'}">
                <el-option v-for="(item, index) in evalTypes" :key="item.id" :label="item.name+':'+item.flag"
                           :value="item.id"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="题目类型" prop="subjectType">
              <el-select v-model="form.subjectType" @change="subjectTypeChange" placeholder="请选择题目类型" clearable
                         :style="{width: '100%'}">
                <el-option v-for="(item, index) in subjectTypes" :key="item.dictValue" :label="item.dictLabel"
                           :value="item.dictValue"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="15">
          <topic :form="form" :type="categoryType"></topic>
          <el-col :span="12">
            <el-form-item label="答案" prop="answer">
              <el-input v-model="form.answer" placeholder="答案可以是空" clearable :style="{width: '100%'}">
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item v-if="judgment" label="分值" prop="score">
              <el-input-number v-model="form.score" placeholder="分值" controls-position=right>
              </el-input-number>
            </el-form-item>
          </el-col>
        </el-row>
        <selects v-if="select" ref="selects"></selects>
        <select-picture v-if="select" ref="picture"></select-picture>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="addTopicSelect" v-if="select">新增</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>


<script>

import {listSubject, getSubject, delSubject, addSubject, updateSubject, exportSubject} from "@/api/evaluate/subject";
import {listCategory, getCategory} from "@/api/evaluate/category";
import Selects from "@/views/evaluate/subject/Selects";
import TreeSelect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
import topic from "@/views/evaluate/subject/topic";
import judge from "@/views/evaluate/subject/judge";
import SelectPicture from "@/views/evaluate/subject/SelectPicture";

export default {
  name: "Subject",
  components: {SelectPicture, Selects, TreeSelect, topic, judge},
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      isPicture: false,
      // 题库表格数据
      subjectList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 选择题
      select: false,
      judgment: false,
      // 测评分类
      evalTypes: [],
      // 分类
      category: {},
      categoryType: 0,
      // 题目类型
      subjectTypes: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        categoryId: null,
        categoryCode: null,
        subjectType: null,
        subject: null,
        answer: null,
        score: null,
        select: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        categoryId: [
          {required: true, message: "分类不能为空", trigger: "blur"}
        ],
        categoryCode: [
          {required: true, message: "分类编码不能为空", trigger: "blur"}
        ],
        subjectType: [
          {required: true, message: "题目类型不能为空", trigger: "change"}
        ],
        createTime: [
          {required: true, message: "创建时间不能为空", trigger: "blur"}
        ],
        updateTime: [
          {required: true, message: "修改时间不能为空", trigger: "blur"}
        ]
      }
    };
  },
  created() {
    this.getList();
    this.getDicts("subject_type").then(response => {
      this.subjectTypes = response.data;
    });
  },
  methods: {
    /** 查询题库列表 */
    getList() {
      this.loading = true;
      listSubject(this.queryParams).then(response => {
        this.subjectList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        categoryId: null,
        categoryCode: null,
        subjectType: null,
        subject: null,
        answer: null,
        score: null,
        select: null,
        createTime: null,
        updateTime: null
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加题库";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getSubject(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改题库";
        this.categoryType = response.data.type;
        this.handleSubject(response)
      });

    },
    handleSubject(response) {
      var that = this;
      //处理提选
      this.subjectTypeChange(this.form.subjectType);
      if (this.categoryType === 0) {
        if (this.form.subjectType === '1') {
          this.select = true;
          setTimeout(function () {
            if (that.form.select) {
              that.$refs.selects.domains = JSON.parse(that.form.select);
            }
          }, 200)
        }
      } else if (this.categoryType === 2) {
        if (this.form.subjectType === '1') {
          this.select = true;
          setTimeout(function () {
            // if (that.form.select) {
              that.$refs.picture.set(JSON.parse(that.form.select));
            // }
          }, 200)
        }
      }
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            this.build()
            console.log("form", this.form)
            updateSubject(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            this.build()
            console.log("form", this.form)
            addSubject(this.form).then(response => {
              this.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    build() {
      var json = {};
      if (this.categoryType === 0) {
        if (this.select) {
          this.$set(this.form, 'select', JSON.stringify(this.$refs.selects.domains))
        } else {
          json.key = "text";
          json.subjectObject = this.form.subject
          this.$set(this.form, 'subjectObject', JSON.stringify(json))
          var judge = [{value: '是'}, {value: '不是'}]
          this.$set(this.form, 'select', JSON.stringify(judge))
        }
      } else if (this.categoryType === 2) {
        if (this.select) {
          this.$set(this.form, 'select', JSON.stringify(this.$refs.picture.get()))
        } else {
          var judge = [{value: '是'}, {value: '不是'}]
          this.$set(this.form, 'select', JSON.stringify(judge))
        }
      }
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$confirm('是否确认删除题库编号为"' + ids + '"的数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function () {
        return delSubject(ids);
      }).then(() => {
        this.getList();
        this.msgSuccess("删除成功");
      })
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有题库数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function () {
        return exportSubject(queryParams);
      }).then(response => {
        this.download(response.msg);
      })
    },
    /** 窗口打开事件*/
    dialogOpenEvent() {
      //加载分类
      this.loadEvalCategory()
    },
    dialogCloseEvent() {
      this.reset();
      this.judgment = false
      this.select = false
    },
    loadEvalCategory() {
      listCategory().then(response => {
        this.evalTypes = response.rows;
        this.evalTypes.forEach(function (row, index) {
          if (row.type === 0) {
            row.flag = "文字";
          } else if (row.type === 1) {
            row.flag = '图片';
          } else {
            row.flag = '图文并茂';
          }
        })
      })
    },
    /**添加题目选项*/
    addTopicSelect() {
      if (this.categoryType === 0) {
        this.$refs.selects.addTopicSelect();
      } else if (this.categoryType === 2) {
        console.log("123")
        this.$refs.picture.addTopicSelect();
      }

    },
    subjectTypeChange(item) {
      console.log(item)
      //选择题
      if (item === '1') {
        this.judgment = false;
        this.select = true;
      } else { //判断题
        this.select = false;
        this.judgment = true;
      }
    },
    CategoryChange(id) {
      if (id) {
        getCategory(id).then(response => {
          this.category = response.data
          this.categoryType = this.category.type
        })
      }
    },
    subjectTypeFormatter(row) {
      if (row.subjectType === '1') {
        return <el-tag type="info">选择题</el-tag>
      } else if (row.subjectType === '2') {
        return <el-tag type="info">判断题</el-tag>
      }
      // return <el-tag type="info">图文并茂</el-tag>
    },
    subjectFormatter(row) {
      console.log(row)
      let s = JSON.parse(row.subject)
      return s.subjectObject
    }
  }
};
</script>
