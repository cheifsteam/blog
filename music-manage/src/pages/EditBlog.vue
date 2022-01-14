<template>
  <div class="container">
    <div class="card-hear">
      <div><h3 class="card-title">发布文章</h3></div>
      <div style=" box-sizing: border-box "></div>
    </div>
    <div>
      <el-form
        ref="blog"
        class="demo-ruleForm"
        :model="blog"
        :rules="fromRule"
      >
        <div class="inputCommon" >
        <el-form-item prop="blogTitle" >
          <el-input class="inputCommon" v-model="blog.blogTitle" placeholder="文章标题"></el-input>
        </el-form-item>
        </div>
          <div class="inputCommon" >
          <el-form-item prop="blogTag" >
            <el-select v-model="blog.blogTag" clearable multiple filterable allow-create default-first-option placeholder="文章标签，用回车添加">
            </el-select>
          </el-form-item>
          </div>
        <div class="inputCommon">
          <el-form-item prop="categoryName">
          <el-select v-model="blog.categoryName" class="form-control">
            <el-option v-for="o in categorys" :key="o.key" :value="o.categoryName"></el-option>
          </el-select>
          </el-form-item>
        </div>
        <div class="markdown" style="height: 600px">
          <el-form-item prop="blogContent">
          <div style="padding-top: 20px">
            <mavon-editor v-model="blog.blogContent" ref="md" @imgAdd="imgAdd" @change="change" style="min-height: 600px"/>
          </div>
          </el-form-item>
          <el-button @click="handleEdit()">保存文章</el-button>
          <el-dialog title="保存文章" :visible.sync="dialogFormVisible">
            <el-upload
              action="upload"
              :http-request="uploadSectionFile"
              :before-upload="beforeAvatarUpload">
              <div style="font-size: 90px;">
                <img v-if="blog.blogImg" :src="getUrl(blog.blogImg)" alt=""/>
                <i v-else class="el-icon-camera avatar-uploader-icon" ></i>
              </div>
              <div class="el-upload__text">将文件拖到此处，或<em>修改头像</em></div>
              <div class="el-upload__tip" slot="tip">只能上传jpg/png文件，且不超过10M</div>
            </el-upload>

            <span slot="footer" class="dialog-footer">
            <el-button size="mini" @click="dialogFormVisible=false">取 消</el-button>
            <el-button type="primary" size="mini" @click="submit('blog')">确 定</el-button>
            </span>
          </el-dialog>
        </div>
      </el-form>

    </div>
  </div>

</template>

<script>
import { mixin } from '../mixins'
import { HttpManager } from '../api/index'
import { mavonEditor } from 'mavon-editor'
import 'mavon-editor/dist/css/index.css'
export default {
  name: 'edit-blog',
  mixins: [mixin],
  inject: ['reload'],
  components: {
    mavonEditor
  },
  data() {
    return {
      blog: {
        blogContent: '',
        categoryName: '',
        blogTag: [],
        blogTitle: '',
        blogImg: ''
      },
      categorys: [],
      html: '',
      fromRule: {
        blogContent: [{required: true, message: '博客内容为空', tiger: 'blur'}],
        categoryName: [{required: true, message: '分类为空', tiger: 'change'}],
        blogTag: [{type: 'array', required: true, message: '标签为空', tiger: 'change'}],
        blogTitle: [{required: true, message: '标题为空', tiger: 'blur'}]
      },
      dialogFormVisible: false
    }
  },
  mounted() {
    this.getAllCategory()
  },
  methods: {
    // handleClose (tag) {
    //   this.dynamicTags.splice(this.dynamicTags.indexOf(tag), 1)
    // },
    //
    // showInput () {
    //   this.inputVisible = true
    //   this.$nextTick(_ => {
    //     this.$refs.saveTagInput.$refs.input.focus()
    //   })
    // },
    //
    // handleInputConfirm () {
    //   let inputValue = this.inputValue
    //   if (inputValue && this.dynamicTags.length < 6) {
    //     this.dynamicTags.push(inputValue)
    //   } else {
    //     this.notify('添加失败，标签不能多于6个', 'error')
    //   }
    //   this.inputVisible = false
    //   this.inputValue = ''
    // },
    handleEdit() {
      this.dialogFormVisible = true // 开启弹出层
    },
    getAllCategory() {
      HttpManager.getAllCategory().then(res => {
        this.categorys = res.data
        console.log(this.categorys)
      })
    },
    imgAdd(pos, file) {
      let formdata = new FormData()
      formdata.append('file', file)
      HttpManager.uploadFileByEditormd(file).then(res => {
        console.log(res.data)
        this.$refs.md.$img2Url(pos, res.data)
      }).catch(err => {
        console.log(err)
      })
    },
    uploadSectionFile(param) {
      let form = new FormData()
      form.append('file', param.file)
      HttpManager.uploadFile(form).then((res) => {
        this.notify('上传成功', 'success')
        this.blog.blogImg = res.data
        console.log(this.blog.img)
        console.log(res)
      }).catch((err) => {
        this.notify('添加失败', err)
        console.log('上传错误')
        console.log(err)
      })
    },
    beforeAvatarUpload(file) {
      const isJPG = file.type === 'image/jpeg'
      const isLt2M = file.size / 1024 / 1024 < 10
      if (!isJPG) {
        this.notify('上传头像图片只能是 JPG 格式!')
      }
      if (!isLt2M) {
        this.notify('上传头像图片大小不能超过 10MB!')
      }
      return isJPG && isLt2M
    },
    change(value, render) {
      // render 为 markdown 解析后的结果[html]
      this.html = render
    },
    submit(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          HttpManager.addBlog(this.blog).then(res => {
            if (res.code === 200) {
              console.log(res.data)
              this.notify('添加成功', 'success')
              this.reload()
            } else {
              this.notify('添加失败', res.msg)
            }
          })
        } else {
          console.log('error submit!!')
        }
      })
    },
    getUrl(url) {
      return `${this.$store.state.HOST}`+url
    }
  }
}
</script>

<style scoped>
.inputCommon{
  width: 600px;
  display: inline-block;
}
.el-tag + .el-tag {
  margin-left: 10px;
}
.button-new-tag {
  margin-left: 10px;
  height: 32px;
  line-height: 30px;
  padding-top: 0;
  padding-bottom: 0;
}
.input-new-tag {
  width: 90px;
  margin-left: 10px;
  vertical-align: bottom;
}
</style>
