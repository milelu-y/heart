<template>
  <div>
    <el-divider/>
    <p>
      {{ tinymceName }}
      <el-icon type="right"/>
    </p>
    <Editor :id="id" :init="init" v-model="content"></Editor>
  </div>
</template>

<script>
import tinymce from 'tinymce/tinymce';
import 'tinymce/icons/default/icons';
import Editor from '@tinymce/tinymce-vue'
import 'tinymce/themes/silver';
import 'tinymce/plugins/image'; // 插入上传图片插件
import 'tinymce/plugins/media'; // 插入视频插件
import 'tinymce/plugins/table'; // 插入表格插件
import 'tinymce/plugins/lists'; // 列表插件
import 'tinymce/plugins/advlist';
import 'tinymce/plugins/autoresize';
import 'tinymce/plugins/wordcount'; // 字数统计插件
import 'tinymce/plugins/fullscreen'; // 显示全屏插件
import 'tinymce/plugins/link'; // 超链接
import 'tinymce/plugins/anchor'; // 锚点
import 'tinymce/plugins/code'; // 代码
import 'tinymce/plugins/codesample'; // 代码
import 'tinymce/plugins/preview'; // 超链接
import 'tinymce/plugins/searchreplace'; // 超链接
import './langs/zh_CN.js';
import {uploadFile} from "@/api/upload/upload";

export default {
  name: 'index',
  components: {Editor},
  props: {
    id: String,
    value: {
      type: String,
      default: 'tinymce-' + new Date() + ((Math.random() * 1000).toFixed(0) + '')
    },
    tinymceName: {
      type: String,
      default: ''
    },
    tinymceCode: {
      type: String,
      default: ''
    },
    disabled: {
      type: Boolean,
      default: false
    },
  },

  data() {
    return {
      init: {
        selector: '#' + this.id,
        language: 'zh_CN',
        height: 500,
        skin_url: '/tinymce/skins/ui/oxide',
        content_css: ['/tinymce/skins/content/default/content.css', '/tinymce/skins/ui/oxide-dark/content.css'],
        menubar: 'file edit insert view format table',
        plugins: 'advlist anchor autolink autosave code codesample colorpicker colorpicker contextmenu directionality emoticons fullscreen hr image imagetools insertdatetime link lists media nonbreaking noneditable pagebreak paste preview print save searchreplace spellchecker tabfocus table template textcolor textpattern visualblocks visualchars wordcount',
        toolbar: 'undo redo searchreplace bold italic underline strikethrough alignleft aligncenter alignright outdent indent  blockquote  removeformat subscript superscript code codesample\', \'hr bullist numlist link image charmap preview anchor pagebreak insertdatetime media table emoticons forecolor backcolor fullscreen',
        branding: false, // 技术支持(Powered by Tiny || 由Tiny驱动)
        theme: 'silver', // 主题
        images_upload_credentials: true,
        images_upload_handler: this.uploadImgFile,
      },
      content: ''
    };
  },
  created() {
    console.log("id",this.id)
  },
  methods: {
    getContent() {
      return this.content
    },
    setContent(content) {
      this.content = content
    },
    uploadImgFile(blobInfo, success, failure) {
      const fd = new FormData()
      fd.append('file', blobInfo.blob())
      uploadFile(fd)
        .then(response => {
          const result = response.data
          success(result.url)
        })
        .catch(err => {
          console.log(err)
        })
    }
  },
  mounted() {
    //配置的初始化
    tinymce.init(this.init)
  },

}
</script>

<style scoped>

</style>
