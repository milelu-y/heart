<template>
  <el-row :gutter="24">
    <el-col :span="12" v-for="(field, index) in pictures">
      <input-picture :ref="'inputPicture'+index" :form="form" :data="data"
                     :key="index"/>
      <el-input v-model="field.description" placeholder="描述" :style="{width: '21%'}"/>
      <el-input-number v-model="field.minute" placeholder="分" controls-position=right :style="{width: '21%'}"/>
      <div style="margin-top: 15%">
        <el-button @click.prevent="remove(field)">删除</el-button>
      </div>
    </el-col>
  </el-row>
</template>

<script>
import InputPicture from "@/components/DynamicForms/FormComponent/InputPicture";
import _ from 'lodash'

export default {
  name: "SelectPicture",
  components: {InputPicture},
  data() {
    return {
      pictures: [],
      form: {},
      arr: {},
      data: {},
      test: {}
    }
  },
  created() {
    console.log(this.test)
  },
  methods: {
    addTopicSelect() {
      this.pictures.push({
        key: Date.now(),
        minute: 0,
        description:''
      });
    },
    get() {
      for (let i = 0; i < this.pictures.length; i++) {
        var picture = this.$refs['inputPicture' + i][0].filterField()
        picture[0].minute = this.pictures[i].minute
        picture[0].description = this.pictures[i].description
        this.arr[i] = picture;
      }
      return this.arr
    },
    set(data) {
      var keys = _.keys(data);
      let that = this;
      for (let i = 0; i < keys.length; i++) {
        let newVar = _.get(data, i);
        this.addTopicSelect()
        this.pictures[i].minute = newVar[0].minute
        this.pictures[i].description = newVar[0].description
        setTimeout(function () {
          that.$refs[`inputPicture` + i][0].setFieldValue(newVar)
        }, 300)
      }
    },
    remove(item) {
      var index = this.pictures.indexOf(item)
      if (index !== -1) {
        this.pictures.splice(index, 1)
        _.unset(this.arr, index);
      }
    }
  }
}
</script>

<style scoped>

</style>
