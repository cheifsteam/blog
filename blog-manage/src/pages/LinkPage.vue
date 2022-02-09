<template>
  <div class="table">
    <div class="container">
      <div class="handle-box">
        <el-button type="primary" size="mini" class="handle-del mr10" @click="delAll">批量删除</el-button>
        <el-button type="primary" size="mini" @click="handleAdd">添加</el-button>
      </div>
      <el-table :data="tableData" border size="mini" style="width: 100%" height="550px" :row-style="{height:'130px'}" ref="multipleTable" @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="40"></el-table-column>
        <el-table-column prop="title" label="网站名称" width="200" align="center"><template slot-scope="scope">
          {{ scope.row.linkName }}
        </template></el-table-column>
        <el-table-column label="网站链接" width="400" align="center">
          <template slot-scope="scope">
            {{ scope.row.linkUrl }}
          </template>
        </el-table-column>
        <el-table-column label="网站描述" width="250" align="center">
          <template slot-scope="scope">
            {{ scope.row.linkDescription }}
          </template>
        </el-table-column>
        <el-table-column label="发布时间" width="150" align="center">
          <template slot-scope="scope">
            <div>{{
                dateFormat(scope.row.createTime)
              }}</div>
          </template>
        </el-table-column>
        <el-table-column label="友链图片" width="250" align="center">
          <template slot-scope="scope">
            {{
               scope.row.linkImg
              }}
          </template>
        </el-table-column>
        <el-table-column label="操作" width="150" align="center">
          <template slot-scope="scope">
            <el-button
              size="mini"
              @click="handleEdit(scope.row)">编辑
            </el-button>
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

    <!--添加友链-->
    <el-dialog title="添加友链" :visible.sync="centerDialogVisible" width="600px" center>
      <el-form ref="link" :model="link" label-width="80px" :rules="rule">
        <el-form-item label="网站类型" prop="linkType" size="mini">
        <el-select v-model.number="link.linkType" class="form-control">
          <el-option v-for="o in type" :key="o.key" :label="o.value" :value="o.key" ></el-option>
        </el-select>
      </el-form-item>
        <el-form-item label="网站名称" prop="linkName" size="mini">
          <el-input v-model="link.linkName" placeholder="网站名称"></el-input>
        </el-form-item>
        <el-form-item label="网站链接" prop="linkUrl" size="mini">
          <el-input v-model="link.linkUrl" placeholder="网站链接"></el-input>
        </el-form-item>
        <el-form-item label="网站描述" prop="linkDescription" size="mini">
          <el-input v-model="link.linkDescription" placeholder="网站描述"></el-input>
        </el-form-item>
        <el-form-item label="友链图床" prop="linkImg" size="mini">
          <el-input v-model="link.linkImg" placeholder="友链图床"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button size="mini" @click="centerDialogVisible = false">取 消</el-button>
        <el-button type="primary" size="mini" @click="addLink('link')">确 定</el-button>
      </span>
    </el-dialog>

    <!-- 编辑友链 -->
    <el-dialog title="编辑" :visible.sync="editVisible" width="600px">
      <el-form ref="link" :model="link" label-width="80px" :rules="rule">
        <el-form-item label="网站类型" prop="linkType" size="mini">
          <el-select v-model.number="link.linkType" class="form-control">
            <el-option v-for="o in type" :key="o.key" :label="o.value" :value="o.key" ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="网站名称" prop="linkName" size="mini">
          <el-input v-model="link.linkName" placeholder="网站名称"></el-input>
        </el-form-item>
        <el-form-item label="网站链接" prop="linkUrl" size="mini">
          <el-input v-model="link.linkUrl" placeholder="网站链接"></el-input>
        </el-form-item>
        <el-form-item label="网站描述" prop="linkDescription" size="mini">
          <el-input v-model="link.linkDescription" placeholder="网站描述"></el-input>
        </el-form-item>
        <el-form-item label="友链图床" prop="linkImg" size="mini">
          <el-input v-model="link.linkImg" placeholder="友链图床"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button size="mini" @click="editVisible = false">取 消</el-button>
        <el-button type="primary" size="mini" @click="saveEdit('link')">确 定</el-button>
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
  name: 'link-page',
  mixins: [mixin],
  inject: ['reload'],
  data () {
    return {
      link: {
        linkId: 0,
        linkName: '',
        linkUrl: '',
        linkDescription: '',
        linkType: 0,
        linkImg: ''
      },
      type: {FriendLink: {key: 0, value: '友链'}, Recommend: {key: 1, value: '推荐'}, PersonalWebsites: {key: 2, value: '个人网站'}},
      rule: {
        linkName: [{required: true, message: '网站名称为空', tiger: 'blur'}],
        linkUrl: [{required: true, message: '网站链接为空', tiger: 'blur'}],
        linkDescription: [{required: true, message: '网站描述为空', tiger: 'blur'}],
        linkType: [{type: 'number', required: true, message: '网站类型为空', tiger: 'change'}],
        linkImg: [{required: true, message: '友链图床为空', tiger: 'blur'}]
      },
      tableData: [],
      tempDate: [],
      multipleSelection: [], // 记录要删除的歌单
      centerDialogVisible: false,
      editVisible: false,
      delVisible: false,
      select_word: '',
      form: {},
      pageSize: 5, // 页数
      currentPage: 1, // 当前页
      ids: [],
      total: 0
    }
  },
  created () {
    this.getData(1)
  },
  methods: {
    // 获取友链信息
    getData (val) {
      let data = {
        'page': val,
        'pageSize': this.pageSize
      }
      HttpManager.getAllLinks(data).then((res) => {
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
    // 编辑啊
    handleEdit (row) {
      this.link.linkId = row.linkId
      this.link.linkName = row.linkName
      this.link.linkUrl = row.linkUrl
      this.link.linkType = row.linkType
      this.link.linkDescription = row.linkDescription
      this.link.linkImg = row.linkImg
      this.editVisible = true
    },
    handleAdd () {
      this.link = {}
      this.centerDialogVisible = true
    },
    // 保存编辑
    saveEdit (formName) {
      let params = this.link
      console.log(params)
      this.$refs[formName].validate((valid) => {
        if (valid) {
          HttpManager.updateLink(params)
            .then(res => {
              if (res.code === 200) {
                this.notify('编辑成功', 'success')
                this.getData(1)
                this.reload()
                this.link = {}
              } else {
                this.notify('编辑失败', 'error')
              }
            })
            .catch(err => {
              console.log(err)
            })
          this.editVisible = false
        }
      })
    },
    // 添加分类
    addLink (formName) {
      let params = this.link
      console.log(params.linkType)
      this.$refs[formName].validate((valid) => {
        if (valid) {
          HttpManager.addLinks(params).then(res => {
            if (res.code === 200) {
              this.getData(1)
              this.link = {}
              this.notify('添加成功', 'success')
            } else {
              this.notify('添加失败', 'error')
            }
          }).catch(err => {
            console.log(err)
          })
          this.centerDialogVisible = false
        }
      })
    },
    // 确定删除
    deleteRow () {
      HttpManager.deleteLinks(this.ids)
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
    delAll () {
      let arr = []
      for (let item of this.multipleSelection) {
        arr.push(item.linkId)
      }
      this.multipleSelection = []
      this.handleDelete(arr)
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
