<template>
  <div class="table">
    <div class="container">
      <div class="handle-box">
        <el-button type="primary" size="mini" class="handle-del mr10" @click="delAll">批量删除</el-button>
        <el-button type="primary" size="mini" @click="handleAdd">添加分类</el-button>
      </div>
      <el-table :data="tableData" border size="mini" style="width: 100%" height="850px" :row-style="{height:'200px'}" ref="multipleTable" @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="40"></el-table-column>
        <el-table-column prop="title" label="标题" width="400" align="center"><template slot-scope="scope">
          {{ scope.row.categoryName }}
        </template></el-table-column>
        <el-table-column label="预览图" width="500" align="center">
          <template slot-scope="scope">
            <img :src="getUrl(scope.row.categoryImg)" alt="" style="width: 200px;height: 150px;"/>
          </template>
        </el-table-column>
        <el-table-column label="时间" width="300" align="center">
          <template slot-scope="scope">
            <div>{{
                dateFormat(scope.row.createTime)
              }}</div>
          </template>
        </el-table-column>

        <el-table-column label="操作" width="200" align="center">
          <template slot-scope="scope">
            <el-button
              size="mini"
              @click="handleEdit(scope.row)">编辑
            </el-button>
          </template>
        </el-table-column>
      </el-table>

    </div>

    <!--添加分类-->
    <el-dialog title="添加分类" :visible.sync="centerDialogVisible" width="400px" center>
      <el-form :model="category" status-icon ref="category" label-width="70px" class="demo-ruleForm" >
        <el-form-item label="分类名称" prop="categoryName" size="mini">
          <el-input v-model="category.categoryName" placeholder="分类名称"></el-input>
        </el-form-item>
        <el-form-item label="分类图标" prop="categoryImg" size="mini">
        </el-form-item>
        <el-upload
          action="upload"
          :http-request="uploadSectionFile"
          :before-upload="beforeAvatarUpload">
          <div style="font-size: 90px;">
            <img v-if="category.categoryImg" :src="getUrl(category.categoryImg)" alt="" class="pic" />
            <i v-else class="el-icon-camera avatar-uploader-icon" ></i>
          </div>
          <div class="el-upload__text">将文件拖到此处，或<em>修改头像</em></div>
          <div class="el-upload__tip" slot="tip">只能上传jpg/png文件，且不超过10M</div>
        </el-upload>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button size="mini" @click="centerDialogVisible = false">取 消</el-button>
        <el-button type="primary" size="mini" @click="addCategory">确 定</el-button>
      </span>
    </el-dialog>

    <!-- 编辑弹出框 -->
    <el-dialog title="编辑" :visible.sync="editVisible" width="400px">
      <el-form :model="category" status-icon ref="category" label-width="70px" class="demo-ruleForm" >
        <el-form-item label="分类名称" prop="categoryName" size="mini">
          <el-input v-model="category.categoryName" placeholder="分类名称"></el-input>
        </el-form-item>
        <el-form-item label="分类图标" prop="categoryImg" size="mini">
        </el-form-item>
        <el-upload
          action="upload"
          :http-request="uploadSectionFile"
          :before-upload="beforeAvatarUpload">
          <div style="font-size: 90px;">
            <img v-if="category.categoryImg" :src="getUrl(category.categoryImg)" alt="" class="pic" />
            <i v-else class="el-icon-camera avatar-uploader-icon" ></i>
          </div>
          <div class="el-upload__text">将文件拖到此处，或<em>修改头像</em></div>
          <div class="el-upload__tip" slot="tip">只能上传jpg/png文件，且不超过10M</div>
        </el-upload>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button size="mini" @click="editVisible = false">取 消</el-button>
        <el-button type="primary" size="mini" @click="saveEdit">确 定</el-button>
      </span>
    </el-dialog>

    <!-- 删除提示框 -->
    <el-dialog title="提示" :visible.sync="delVisible" width="300px" center>
      <div class="del-dialog-cnt" align="center">删除不可恢复，是否确定删除？</div>
      <span slot="footer" class="dialog-footer">
        <el-button size="mini" @click="delVisible = false">取 消</el-button>
        <el-button type="primary" size="mini" @click="deleteRow">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import { mixin } from '../mixins'
import { HttpManager } from '../api/index'

export default {
  name: 'song-list-page',
  mixins: [mixin],
  inject: ['reload'],
  data () {
    return {
      category: {
        categoryName: '',
        categoryImg: '',
        id: 0
      },
      tableData: [],
      tempDate: [],
      multipleSelection: [], // 记录要删除的歌单
      centerDialogVisible: false,
      editVisible: false,
      delVisible: false,
      ids: []
    }
  },
  created () {
    this.getData()
  },
  methods: {
    // 获取所有分类信息
    getData () {
      this.tableData = []
      HttpManager.getAllCategory().then((res) => {
        this.tableData = res.data
      })
    },
    // 编辑啊
    handleEdit (row) {
      this.category.id = row.id
      this.category.categoryName = row.categoryName
      this.category.categoryImg = row.categoryImg
      this.editVisible = true
    },
    handleAdd () {
      this.category.categoryImg=''
      this.category.categoryName=''
      this.category.Id=0
      this.centerDialogVisible = true
    },
    uploadSectionFile (param) {
      let form = new FormData()
      form.append('file', param.file)
      HttpManager.uploadFile(form).then((res) => {
        this.notify('上传成功', 'success')
        this.category.categoryImg = res.data
        console.log("pic"+this.category.categoryImg)
        console.log(res)
      }).catch((err) => {
        this.notify('添加失败', err)
        console.log('上传错误')
        console.log(err)
      })
    },
    // 保存编辑
    saveEdit () {
      let params = this.category
      console.log(params)
      if (params.categoryName === '') {
        this.notify('分类名称不为空', 'error')
        this.editVisible = false
        return
      }
      if (params.categoryImg === '') {
        this.notify('添加图片不为空', 'error')
        this.editVisible = false
        return
      }
      HttpManager.updateCategory(params)
        .then(res => {
          if (res.code === 200) {
            this.notify('编辑成功', 'success')
            this.getData()
            this.reload()
            this.category = {}
          } else {
            this.notify('编辑失败', 'error')
          }
        })
        .catch(err => {
          console.log(err)
        })
      this.editVisible = false
    },
    // 添加分类
    addCategory () {
      let params = this.category
      console.log(params)
      if (params.categoryName === '') {
        this.notify('分类名称不为空', 'error')
        this.centerDialogVisible = false
        return
      }
      if (params.categoryImg === '') {
        this.notify('添加图片不为空', 'error')
        this.centerDialogVisible = false
        return
      }
      HttpManager.addCategory(params).then(res => {
        if (res.code === 200) {
          this.getData(1)
          this.category = {}
          this.reload()
          this.notify('添加成功', 'success')
        } else {
          this.notify('添加失败', 'error')
        }
      }).catch(err => {
        console.log(err)
      })
      this.centerDialogVisible = false
    },
    // 确定删除
    deleteRow () {
      HttpManager.deleteCategory(this.ids)
        .then(res => {
          if (res) {
            this.getData()
            this.notify('删除成功', 'success')
          } else {
            this.notify('删除失败', 'error')
          }
        })
        .catch(err => {
          console.log(err)
        })
      this.delVisible = false
    }
  }
}
</script>

<style scoped>
.handle-box {
    margin-bottom: 20px;
}

.handle-input {
    width: 300px;
    display: inline-block;
}
.pagination {
    display: flex;
    justify-content: center;
}
.pic{
  width: 160px;
  height: 178px;
  display: flex;
}
</style>
