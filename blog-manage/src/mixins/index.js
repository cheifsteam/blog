import moment from 'moment'

export const mixin = {
  methods: {
    // 提示信息
    notify (title, type) {
      this.$notify({
        title: title,
        type: type
      })
    },
    getUrl (url) {
      return `${this.$store.state.HOST}` + url
    },
    // 获取要删除列表的id
    handleDelete (ids) {
      if (ids.length === 0) {
        this.notify('至少选择一条记录', 'error')
        this.delVisible = false
      } else {
        this.delVisible = true
        this.ids = ids
      }
    },
    // 获取批量要删除的列表
    handleSelectionChange (val) {
      this.multipleSelection = val
      console.log(val)
    },
    // 批量删除
    delAll () {
      let arr = []
      for (let item of this.multipleSelection) {
        arr.push(item.id)
      }
      this.multipleSelection = []
      this.handleDelete(arr)
    },
    // 得到歌曲名字
    replaceFName (str) {
      let arr = str.split('-')
      return arr[1]
    },
    // 得到歌手名字
    replaceLName (str) {
      let arr = str.split('-')
      return arr[0]
    },
    attachBirth (val) {
      let birth = String(val).match(/[0-9-]+(?=\s)/)
      return Array.isArray(birth) ? birth[0] : birth
    },
    changeSex (value) {
      if (value === 0) {
        return '女'
      } else if (value === 1) {
        return '男'
      } else if (value === 2) {
        return '组合'
      } else if (value === 3) {
        return '不明'
      } else if (value === '男' || value === '女') {
        return value
      }
    },
    toggleSex (value) {
      if (value === '女') {
        return 0
      } else if (value === '男') {
        return 1
      }
    },
    // 更新图片
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
    dateFormat (data) {
      return moment(new Date(data)).utcOffset( 0).format('YYYY-MM-DD HH:mm')
    }
  }
}
