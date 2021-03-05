<template>
    <el-row :gutter="24">
      <el-col :span="12" v-if="domains.length>0"  v-for="(domain, index) in domains">
        <el-form-item
          :label="''+(index+1)+''"
          :key="domain.key"
          >
          <el-input size="mini" v-model="domain.value" type="textarea" placeholder="请输入内容" :style="{width: '60%'}"/>
          <el-input-number v-model="domain.minute"  placeholder="分"  controls-position=right :style="{width: '21%'}"/>
          <el-button @click.prevent="remove(domain)">删除</el-button>
        </el-form-item>
      </el-col>
    </el-row>
</template>

<script>
export default {
  name: "selects",
  data() {
    return {
      // 动态
      domains: [],
      form:{}
    }
  },
  methods:{
    add(){
      console.log("add")
    },
    /**添加题目选项*/
    addTopicSelect() {
      this.domains.push({
        value: '',
        key: Date.now()
      });
    },
    remove(item){
      var index = this.domains.indexOf(item)
      if (index !== -1) {
        this.domains.splice(index, 1)
      }
    }
  }
}
</script>

<style scoped>

</style>
