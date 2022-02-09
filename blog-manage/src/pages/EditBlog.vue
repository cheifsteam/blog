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
<!--            <el-form-item prop="blogTag">-->
            <el-tag
              :key="tag"
              v-for="tag in arr"
              closable
              :disable-transitions="false"
              @close="handleClose(tag)"
              @input="inputOnInput($event)">
              {{tag}}
            </el-tag>
            <el-input
              class="input-new-tag"
              v-if="inputVisible"
              v-model="blog.blogTag"
              ref="saveTagInput"
              size="small"
              @keyup.enter.native="handleInputConfirm"
              @blur="handleInputConfirm"
              @input="inputOnInput($event)"
            >
            </el-input>
            <el-button v-else class="button-new-tag" size="small" @click="showInput">+ New Tag</el-button>
<!--            </el-form-item>-->
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
            <p style=""><mavon-editor v-model="blog.blogContent" ref="md" @imgAdd="imgAdd" @change="change" style="min-height: 600px"/></p>
          </div>
          </el-form-item>
          <el-button  @click="handleEdit('blog')">保存文章</el-button>
          <el-dialog title="保存文章" :visible.sync="dialogFormVisible">
            <el-form-item label="文章图床" label-width="80px" size="mini">
              <el-input v-model="blog.blogImg" placeholder="文章图床"></el-input>
            </el-form-item>
            <span slot="footer" class="dialog-footer">
            <el-button size="mini" @click="dialogFormVisible=false">取 消</el-button>
            <el-button type="primary" size="mini" @click="submit()">确 定</el-button>
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
  data () {
    return {
      blog: {
        blogContent: '',
        categoryName: '',
        blogTag: '',
        blogTitle: '',
        blogImg: '',
        tags: [],
        id: 0
      },
      categorys: [],
      html: '',
      fromRule: {
        blogContent: [{required: true, message: '博客内容为空', tiger: 'blur'}],
        categoryName: [{required: true, message: '分类为空', tiger: 'change'}],
        blogTitle: [{required: true, message: '标题为空', tiger: 'blur'}]
      },
      dialogFormVisible: false,
      inputVisible: false,
      inputValue: '',
      arr: []
    }
  },
  mounted () {
    this.getAllCategory()
    this.ini()
  },
  methods: {
    ini () {
      let data = this.$route.query.blog
      console.log(JSON.stringify(data))
      if (JSON.stringify(data) !== undefined) {
        this.blog = data || ''
        this.blog.tags = data.blogTag.split(',')
        this.arr = this.blog.tags
      }
    },
    handleEdit (formName) {
      this.$refs[formName].validate((valid) => {
        if (valid && this.validForm()) {
          this.dialogFormVisible = true // 开启弹出层
        } else {
          this.dialogFormVisible = false
        }
      })
    },
    getAllCategory () {
      HttpManager.getAllCategory().then(res => {
        this.categorys = res.data
        console.log(this.categorys)
      })
    },
    imgAdd (pos, file) {
      let formdata = new FormData()
      formdata.append('file', file)
      HttpManager.uploadFileByEditormd(file).then(res => {
        console.log(res.data)
        this.$refs.md.$img2Url(pos, res.data)
      }).catch(err => {
        console.log(err)
      })
    },
    uploadSectionFile (param) {
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
    beforeAvatarUpload (file) {
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
    change (value, render) {
      // render 为 markdown 解析后的结果[html]
      this.html = render
    },
    submit () {
      this.blog.blogTag = this.blog.tags.toString()
      console.log(this.validImg())
      if (this.validImg()) {
        let isUpdate = this.blog.id
        if (!isUpdate) {
          console.log(isUpdate)
          HttpManager.addBlog(this.blog).then(res => {
            if (res.code === 200) {
              console.log(res.data)
              this.notify('添加成功', 'success')
              this.reload()
            } else {
              this.notify('添加失败', 'error')
            }
          })
        } else {
          HttpManager.updateBlog(this.blog).then(res => {
            if (res.code === 200) {
              console.log(res.data)
              this.notify('更新成功', 'success')
              this.reload()
            } else {
              console.log(res.data)
              this.notify('更新失败', 'error')
            }
          })
        }
      } else {
        this.notify('文章图床不为空','error')
      }
    },
    getUrl (url) {
      return `${this.$store.state.HOST}` + url
    },
    validImg () {
      return this.blog.blogImg !== ''
    },
    validForm () {
      if (this.blog.blogTitle.length > 100) {
        this.notify('题目字数不能超过100字', 'error')
        return false
      }
      if (this.blog.blogContent.length > 100000) {
        this.notify('文章不能超过10万字', 'error')
        return false
      }
      if (this.blog.tags.length === 0) {
        this.notify('标签不能为空', 'error')
        return false
      }
      return true
    },
    handleClose (tag) {
      this.blog.tags.splice(this.blog.tags.indexOf(tag), 1)
      this.arr = this.blog.tags
      console.log(this.blog.tags.length)
    },
    showInput () {
      this.inputVisible = true
      this.$nextTick(_ => {
        this.$refs.saveTagInput.$refs.input.focus()
      })
    },
    handleInputConfirm () {
      let inputValue = this.blog.blogTag
      if (inputValue.length >= 20) {
        this.notify('标签字数不能超过20字', 'error')
        this.inputVisible = false
        return
      }
      if (this.blog.tags.length >= 5) {
        this.notify('标签个数不能超过5个', 'error')
        this.inputVisible = false
        return
      }
      if (inputValue) {
        this.blog.tags.push(inputValue)
      }
      this.inputVisible = false
      this.blog.blogTag = ''
      this.arr = this.blog.tags
    },
    inputOnInput (e) {
      this.$forceUpdate()
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
.avatar {
  width: 178px;
  height: 178px;
  display: flex;
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
