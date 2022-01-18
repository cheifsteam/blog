import { get, post } from './request'

const HttpManager = {
  // =======================> 管理员 API
  // 是否登录成功
  getLoginStatus: (params) => post(`admin/admin/login`, params),

  // =======================> 博客 API
  // 返回所有博客
  getAllBlogs: (params) => post(`/admin/blog/getAll`, params),
  // 添加博客
  addBlog: (param) => post(`/admin/blog/add`, param),
  // 删除博客
  deleteBlog: (ids) => post(`/admin/blog/delete`, ids),
  // 更新博客
  updateBlog: (params) => post(`/admin/blog/update`, params),
  // md转html
  uploadFileByEditormd: (params) => post(`/admin/blog/uploadFileByEditormd`, params),
  // 上传图片
  uploadFile: (params) => post(`/admin/upload/file`, params),
  //模糊查询博客
  getLike: (filterName,params) => post(`/admin/blog/getLike?filterName=${filterName}`, params),
  // getUserOfId: (id) => get(`user/detail?id=${id}`),
  // // 添加用户
  // setUser: (params) => post(`user/add`, params),
  // // 更新用户信息
  // updateUserMsg: (params) => post(`user/update`, params),
  // // 删除用户
  // deleteUser: (id) => get(`user/delete?id=${id}`),

  // =======================> 收藏列表 API
  // 返回的指定用户ID收藏列表
  getCollectionOfUser: (userId) => get(`collection/detail?userId=${userId}`),
  // 删除收藏的歌曲
  deleteCollection: (userId, songId) => get(`/collection/delete?userId=${userId}&&songId=${songId}`),

  // =======================> 评论列表 API
  // 获得指定歌曲ID的评论列表
  getCommentOfSongId: (songId) => get(`comment/song/detail?songId=${songId}`),
  // 获得指定歌单ID的评论列表
  getCommentOfSongListId: (songListId) => get(`comment/songList/detail?songListId=${songListId}`),
  // 更新评论
  updateCommentMsg: (params) => post(`comment/update`, params),
  // 删除评论
  deleteComment: (id) => get(`comment/delete?id=${id}`),

  // =======================> 标签 API
  // 返回所有标签
  getAllTags: (params) => post(`/admin/tag/getAll`, params),
  // 添加歌手
  addTags: (params) => post(`/admin/tag/add`, params),
  // 删除标签
  deleteTags: (ids) => post(`/admin/tag/delete`, ids),
  // 更新歌手信息
  updateSingerMsg: (params) => post(`singer/update`, params),

  // =======================> 分类 API
  // 返回所有类别
  getAllCategory: () => post(`/admin/category/getAll`),
  // 添加类别
  addCategory: (params) => post(`/admin/category/add`, params),
  // 更新类别
  updateCategory: (params) => post(`/admin/category/update`, params),
  // 删除类别
  deleteCategory: (ids) => post(`/admin/category/delete`, ids),
  // 返回的指定用户ID收藏列表
  getSongOfId: (id) => get(`song/detail?id=${id}`),
  // 返回指定歌手名的歌曲
  getSongOfSingerName: (id) => get(`song/singerName/detail?name=${id}`),

  // 删除歌曲
  deleteSong: (id) => get(`song/delete?id=${id}`),

  // =======================> 友链 API

  // 获取全部链接
  getAllLinks: (params) => post(`/admin/link/getAll`, params),
  // 添加链接
  addLinks: (params) => post(`/admin/link/add`, params),
  // 删除链接
  deleteLinks: (ids) => post(`/admin/link/delete`, ids),
  //更新链接
  updateLink: (params) => post(`/admin/link/update`, params),
  // 返回歌单里指定歌单ID的歌曲
  getListSongOfSongId: (songListId) => get(`listSong/detail?songListId=${songListId}`),
  // 删除歌单里的歌曲
  deleteListSong: (songId) => get(`ListSong/delete?songId=${songId}`)

}

export { HttpManager }
