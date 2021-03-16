<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="标题" prop="title">
        <el-input
          v-model="queryParams.title"
          placeholder="请输入标题"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="测评级别" prop="testingLevel">
        <el-input
          v-model="queryParams.testingLevel"
          placeholder="请输入测评级别"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="cyan" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['evaluate:result:add']"
        >新增
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['evaluate:result:edit']"
        >修改
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['evaluate:result:remove']"
        >删除
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['evaluate:result:export']"
        >导出
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="resultList" border @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="题目类别" align="center" prop="categoryName"/>
      <el-table-column label="标题" show-overflow-tooltip align="center" prop="title"/>
      <el-table-column label="开始分数" align="center" prop="startMinute"/>
      <el-table-column label="结束分数" align="center" prop="endMinute"/>
      <el-table-column label="测评级别" align="center" prop="testingLevel"/>
      <el-table-column label="测评描述" align="center" show-overflow-tooltip prop="description"/>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['evaluate:result:edit']"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['evaluate:result:remove']"
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

    <!-- 添加或修改得分对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="1000px" @open="dialogOpenEvent" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="测评分类" prop="categoryId">
          <el-select v-model="form.categoryId" placeholder="请选择测评分类" @change="CategoryChange" clearable
                     :style="{width: '100%'}">
            <el-option v-for="(item, index) in evalTypes" :key="item.id" :label="item.name+':'+item.flag"
                       :value="item.id"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="标题" prop="title">
          <el-input v-model="form.title" placeholder="请输入标题"/>
        </el-form-item>
        <el-form-item label="开始分数" prop="startMinute">
          <el-input v-model="form.startMinute" placeholder="请输入开始分数"/>
        </el-form-item>
        <el-form-item label="结束分数" prop="endMinute">
          <el-input v-model="form.endMinute" placeholder="请输入结束分数"/>
        </el-form-item>
        <el-form-item label="测评级别" prop="testingLevel">
          <el-input v-model="form.testingLevel" placeholder="请输入测评级别"/>
        </el-form-item>
        <h4 style="margin-left: 12px">测评结果：</h4>
        <tinymce-editor v-if="editor" :id="Math.ceil(Math.random() * 10000) + new Date().getTime().toString()" ref="tinymce"></tinymce-editor>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {listResult, getResult, delResult, addResult, updateResult, exportResult} from "@/api/evaluate/result";
import TinymceEditor from '@/components/TinymceEditor'
import {getCategory, listCategory} from "@/api/evaluate/category";

export default {
  name: "Result",
  components: {
    TinymceEditor
  },
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
      // 测评分类
      evalTypes: [],
      // 总条数
      total: 0,
      editor:false,
      // 得分表格数据
      resultList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        categoryId: null,
        title: null,
        startMinute: null,
        endMinute: null,
        testingLevel: null,
        description: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        testingLevel: [
          {required: true, message: "测评级别不能为空", trigger: "blur"}
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
  },
  methods: {
    /** 窗口打开事件*/
    dialogOpenEvent() {
      //加载分类
      this.loadEvalCategory()
      this.editor=true;
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
    CategoryChange(id) {
      if (id) {
        getCategory(id).then(response => {
          this.category = response.data
          this.categoryType = this.category.type
        })
      }
    },
    /** 查询得分列表 */
    getList() {
      this.loading = true;
      listResult(this.queryParams).then(response => {
        this.resultList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.editor=false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        categoryId: null,
        title: null,
        startMinute: null,
        endMinute: null,
        testingLevel: null,
        description: null,
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
      this.title = "添加得分";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getResult(id).then(response => {
        this.open = true;
        this.title = "修改得分";
        this.form = response.data;
        this.form.description=this.setFieldValue(response.data.description)
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.building();
          if (this.form.id != null) {
            updateResult(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addResult(this.form).then(response => {
              this.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    building() {
      this.form.description = this.getFieldValue()
    },
    getFieldValue() {
      const content = this.$refs.tinymce.getContent()
      return content
    },
    setFieldValue(value) {
      if (value) {
        setTimeout(s=>{
          this.$refs.tinymce.setContent(value)
        },300)
      }
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$confirm('是否确认删除得分编号为"' + ids + '"的数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function () {
        return delResult(ids);
      }).then(() => {
        this.getList();
        this.msgSuccess("删除成功");
      })
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有得分数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function () {
        return exportResult(queryParams);
      }).then(response => {
        this.download(response.msg);
      })
    }
  }
}
;
</script>
