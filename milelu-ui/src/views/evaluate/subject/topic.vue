<template>
  <div>
    <el-form-item v-if="type===0 || type===2" label="题目(文)" prop="subject"
                  :rules="[{required:true,message: '请输入题目', trigger: 'blur'}]">
      <el-divider/>
      <el-input v-model="form.subject" type="textarea" placeholder="请输入题目" :maxlength="1000"
                :autosize="{minRows: 4, maxRows: 4}" :style="{width: '100%'}"></el-input>
    </el-form-item>

<!--    <el-form-item v-if="type===1" label="题目(图)">-->
<!--      <input-picture ref="picture" :form="form" :data="data"/>-->
<!--    </el-form-item>-->
  </div>
</template>

<script>
import InputPicture from "@/components/DynamicForms/FormComponent/InputPicture";

export default {
  name: "topic",
  components: {InputPicture},
  props: {
    form: {
      type: Object,
      required: true
    },
    type: {
      type: Number,
      required: true
    }
  },
  data() {
    return {
      data: {fieldAliase: ''},
      dialogImageUrl: '',
      dialogVisible: false,
      disabled: false
    }
  },
  watch: {
    type: function (n, o) {

    }
  }
}
</script>

<style scoped>

</style>
