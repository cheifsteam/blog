<template>
  <div class="table">
    <div class="container">
      <div class="handle-box">
        <el-button class="handle-del mr10" type="primary" size="mini" @click="delAll">批量删除</el-button>
        <el-input v-model="select_word" class="handle-input mr10" size="mini" placeholder="筛选关键词"></el-input>
       <router-link to="/EditBlog"> <el-button type="primary" size="mini" >添加博客</el-button></router-link>
      </div>
      <el-table ref="multipleTable" size="mini" border style="width: 100%" height="550px"   :data="tableData" @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="40"></el-table-column>

        <el-table-column label="标题" width="170" align="center">
          <template slot-scope="scope">
            <div>{{scope.row.blogTitle}}</div>
          </template>
        </el-table-column>
        <el-table-column label="发布时间" width="120" align="center">
          <template slot-scope="scope">
              <div>{{scope.row.updateTime}}</div>
          </template>
        </el-table-column>
        <el-table-column label="预览图" width="210" align="center">
          <template slot-scope="scope">
            <div class="singer-img">
              <img :src="getUrl(scope.row.blogImg)" alt="" style="width: 100%;"/>
            </div>
          </template>
        </el-table-column>
        <el-table-column label="简介">
          <template slot-scope="scope">
            <p style="height: 200px; overflow: scroll">{{ scope.row.blogContent }}</p>
          </template>
        </el-table-column>
        <el-table-column label="阅览量" width="120" align="center">
          <template slot-scope="scope">
            <div>{{scope.row.blogViews}}</div>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="150" align="center">
          <template slot-scope="scope">
            <el-button size="mini" @click="handleEdit(scope.row)">编辑</el-button>
            <el-button size="mini" type="danger" @click="handleDelete(scope.row.id)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <div class="pagination">
        <el-pagination
          background
          layout="total, prev, pager, next"
          :current-page="currentPage"
          :page-size="pageSize"
          :total="total"
          @current-change="handleCurrentChange">
        </el-pagination>
      </div>
    </div>
    <!-- 编辑弹出框 -->
    <el-dialog title="编辑" :visible.sync="editVisible" width="400px">
      <el-form ref="form" :model="form" label-width="60px">
        <el-form-item label="歌手" size="mini">
          <el-input v-model="form.name"></el-input>
        </el-form-item>
        <el-form-item label="性别" size="mini">
          <el-radio-group v-model="form.sex">
            <el-radio :label="0">女</el-radio>
            <el-radio :label="1">男</el-radio>
            <el-radio :label="2">组合</el-radio>
            <el-radio :label="3">不明</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="生日" size="mini">
          <el-date-picker type="date" placeholder="选择日期" v-model="form.birth" style="width: 100%;"></el-date-picker>
        </el-form-item>
        <el-form-item label="地区" size="mini">
          <el-input v-model="form.location"></el-input>
        </el-form-item>
        <el-form-item label="简介" size="mini">
          <el-input type="textarea" v-model="form.introduction"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button size="mini"  @click="editVisible = false">取 消</el-button>
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
  name: 'singer-page',
  mixins: [mixin],
  data () {
    return {
      registerForm: {
        // 添加框信息
        name: '',
        sex: '',
        birth: '',
        location: '',
        introduction: ''
      },
      tableData: [],
      total: 0,
      tempDate: [],
      multipleSelection: [],
      centerDialogVisible: false,
      editVisible: false,
      delVisible: false,
      select_word: '',
      form: {
        // 编辑框信息
        id: '',
        name: '',
        sex: '',
        pic: '',
        birth: '',
        location: '',
        introduction: ''
      },
      pageSize: 5, // 页数
      currentPage: 1, // 当前页
      idx: -1
    }
  },
  computed: {
    // 计算当前表格中的数据
    data () {
      return this.tableData.slice((this.currentPage - 1) * this.pageSize, this.currentPage * this.pageSize)
    }
  },
  watch: {
    select_word: function () {
      if (this.select_word === '') {
        this.tableData = this.tempDate
      } else {
        this.tableData = []
        for (let item of this.tempDate) {
          if (item.name.includes(this.select_word)) {
            this.tableData.push(item)
          }
        }
      }
    }
  },
  created () {
    this.getData(1)
  },
  methods: {
    // 获取当前页
    handleCurrentChange (val) {
      this.currentPage = val
      this.getData(val)
    },
    uploadUrl (id) {
      return `${this.$store.state.HOST}/singer/avatar/update?id=${id}`
    },
    // 添加歌手
    addsinger () {
      let d = this.registerForm.birth
      let datetime = d.getFullYear() + '-' + (d.getMonth() + 1) + '-' + d.getDate()
      let params = new URLSearchParams()
      params.append('name', this.registerForm.name)
      params.append('sex', this.registerForm.sex)
      params.append('pic', '/img/singerPic/hhh.jpg')
      params.append('birth', datetime)
      params.append('location', this.registerForm.location)
      params.append('introduction', this.registerForm.introduction)
      HttpManager.setSinger(params)
        .then(res => {
          if (res.code === 1) {
            this.getData()
            this.registerForm = {}
            this.notify('添加成功', 'success')
          } else {
            this.notify('添加失败', 'error')
          }
        })
        .catch(err => {
          console.log(err)
        })
      this.centerDialogVisible = false
    },
    // 获取所有博客
    getData (val) {
      let data = {
        'page': val,
        'pageSize': this.pageSize
      }
      HttpManager.getAllBlogs(data).then(res => {
        console.log(res)
        this.total = res.data.total
        console.log(res.data.total)
        this.tableData = res.data.data
        this.currentPage = val
      })
    },
    // 编辑
    handleEdit (row) {
      console.log(row)
      this.$router.push({path: '/EditBlog', query: {blog: row}})
    },
    // 保存编辑
    saveEdit () {
      let d = new Date(this.form.birth)
      let datetime = d.getFullYear() + '-' + (d.getMonth() + 1) + '-' + d.getDate()
      let params = new URLSearchParams()
      params.append('id', this.form.id)
      params.append('name', this.form.name)
      params.append('sex', this.form.sex)
      params.append('pic', this.form.pic)
      params.append('birth', datetime)
      params.append('location', this.form.location)
      params.append('introduction', this.form.introduction)
      HttpManager.updateSingerMsg(params)
        .then(res => {
          if (res.code === 1) {
            this.getData()
            this.notify('编辑成功', 'success')
          } else {
            this.notify('编辑失败', 'error')
          }
        })
        .catch(err => {
          console.log(err)
        })
      this.editVisible = false
    },
    // 确定删除
    deleteRow () {
      HttpManager.deleteBlog(this.idx)
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
    },
    songEdit (id, name) {
      this.$router.push({path: `/song`, query: {id: id, name: name}})
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

.singer-img {
  width: 75%;
  height: 150px;
  border-radius: 5px;
  margin-bottom: 5px;
  overflow: hidden;
}

.pagination {
  display: flex;
  justify-content: center;
}
</style>
