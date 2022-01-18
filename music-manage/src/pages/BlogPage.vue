<template>
  <div class="table">
    <div class="container">
      <div class="handle-box">
        <el-button class="handle-del mr10" type="primary" size="mini" @click="delAll">批量删除</el-button>
        <el-input v-model="select_word" class="handle-input mr10" size="mini" placeholder="筛选关键词"></el-input>
        <el-button type="primary" size="mini" @click="getLike(1)" >搜索</el-button>
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
              <div>{{dateFormat(scope.row.updateTime)}}</div>
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
  inject: ['reload'],
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
      ids: []
    }
  },
  computed: {
    // 计算当前表格中的数据
    data () {
      return this.tableData.slice((this.currentPage - 1) * this.pageSize, this.currentPage * this.pageSize)
    }
  },
  // watch: {
  //   select_word: function () {
  //     if (this.select_word === '') {
  //       this.tableData = this.tempDate
  //     } else {
  //       this.tableData = []
  //       for (let item of this.tempDate) {
  //         if (item.name.includes(this.select_word)) {
  //           this.tableData.push(item)
  //         }
  //       }
  //     }
  //   }
  // },
  created () {
    this.getData(1)
  },
  methods: {
    // 获取当前页
    handleCurrentChange (val) {
      this.currentPage = val
      this.getData(val)
      this.getLike(val)
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
    // 确定删除
    deleteRow () {
      HttpManager.deleteBlog(this.ids)
        .then(res => {
          if (res) {
            this.getData()
            this.notify('删除成功', 'success')
            this.reload()
          } else {
            this.notify('删除失败', 'error')
          }
        })
        .catch(err => {
          console.log(err)
        })
      this.delVisible = false
    },
    //模糊查询
    getLike (val) {
      let data = {
        'page': val,
        'pageSize': this.pageSize
      }
      let filterName = this.select_word
      this.tableData = []
      HttpManager.getLike(filterName,data).then(
        res => {
          if (res) {
            console.log(res)
            this.tableData = res.data.data
            this.notify('获取成功', 'success')
          } else {
            this.notify('获取失败' , 'error')
          }
        }
      )
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
