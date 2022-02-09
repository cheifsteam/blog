<template>
  <div class="table">
    <div class="container">
      <div class="handle-box">
        <el-input v-model="tag.tagName" size="mini" placeholder="标签名称" class="handle-input mr10"></el-input>
        <el-button type="primary" size="mini" @click="addTags">添加标签</el-button>
        <el-button type="primary" size="mini" class="handle-del mr10" @click="delAll">批量删除</el-button>
      </div>
      <el-table :data="tableData" border size="mini" style="width: 100%" height="550px" :row-style="{height:'100px'}" ref="multipleTable" @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="40"></el-table-column>
        <el-table-column prop="title" label="标签名称" width="700" align="center">
          <template slot-scope="scope">
          {{ scope.row.tagName }}
        </template>
        </el-table-column>
        <el-table-column label="时间" width="700" align="center">
          <template slot-scope="scope">
            <div>{{
                dateFormat(scope.row.createTime)
              }}</div>
          </template>
        </el-table-column>
      </el-table>
      <div class="pagination">
        <el-pagination
          @current-change="handleCurrentChange"
          background
          layout="total, prev, pager, next"
          :current-page="currentPage"
          :page-size="pageSize"
          :total="total">
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
  name: 'tag-page',
  mixins: [mixin],
  inject: ['reload'],
  data () {
    return {
      tag: {
        tagName: '',
        id: 0
      },
      tableData: [],
      multipleSelection: [], // 记录要删除的标签
      centerDialogVisible: false,
      editVisible: false,
      delVisible: false,
      pageSize: 5, // 页数
      currentPage: 1, // 当前页
      ids: [],
      total: 0
    }
  },
  computed: {
    // 计算当前表格中的数据
    data () {
      return this.tableData.slice((this.currentPage - 1) * this.pageSize, this.currentPage * this.pageSize)
    }
  },
  created () {
    this.getData(1)
  },
  methods: {
    // 获取歌单信息
    getData (val) {
      let data = {
        'page': val,
        'pageSize': this.pageSize
      }
      HttpManager.getAllTags(data).then(res => {
        console.log(res)
        this.total = res.data.total
        console.log(res.data.total)
        this.tableData = res.data.data
        this.currentPage = val
      })
    },
    // 获取当前页
    handleCurrentChange (val) {
      this.currentPage = val
      this.getData(val)
    },
    // 保存编辑
    addTags () {
      let params = this.tag
      console.log(params)
      if (params.tagName === '') {
        this.notify('标签名称不为空', 'error')
        this.editVisible = false
        return
      }
      HttpManager.addTags(params)
        .then(res => {
          if (res.code === 200) {
            this.notify('编辑成功', 'success')
            this.getData()
            this.reload()
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
      HttpManager.deleteTags(this.ids)
        .then(res => {
          if (res) {
            this.getData()
            this.reload()
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
