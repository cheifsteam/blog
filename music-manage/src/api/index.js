import { get, post } from './request'

const HttpManager = {
  // =======================> 管理员 API
  // 是否登录成功
  getLoginStatus: (params) => post(`admin/admin/login`, params),

  // =======================> 用户 API
  // 返回所有用户
  getAllBlogs: (params) => post(`/admin/blog/getAll`, params),
  // 返回指定ID的用户
  getUserOfId: (id) => get(`user/detail?id=${id}`),
  // 添加用户
  setUser: (params) => post(`user/add`, params),
  // 更新用户信息
  updateUserMsg: (params) => post(`user/update`, params),
  // 删除用户
  deleteUser: (id) => get(`user/delete?id=${id}`),

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

  // =======================> 歌手 API
  // 返回所有歌手
  getAllTags: () => post(`/admin/tag/getAll`),
  // 添加歌手
  setSinger: (params) => post(`singer/add`, params),
  // 更新歌手信息
  updateSingerMsg: (params) => post(`singer/update`, params),
  // 删除博客
  deleteBlog: (id) => get(`/admin/blog/delete`, id),

  // =======================> 歌曲 API
  // 返回所有歌曲
  getAllCategory: () => post(`/admin/category/getAll`),
  // 返回指定歌手ID的歌曲
  getSongOfSingerId: (id) => get(`song/singer/detail?singerId=${id}`),
  // 返回的指定用户ID收藏列表
  getSongOfId: (id) => get(`song/detail?id=${id}`),
  // 返回指定歌手名的歌曲
  getSongOfSingerName: (id) => get(`song/singerName/detail?name=${id}`),
  // 更新歌曲信息
  uploadFileByEditormd: (params) => post(`/admin/blog/uploadFileByEditormd`, params),
  // 删除歌曲
  deleteSong: (id) => get(`song/delete?id=${id}`),

  // =======================> 歌单 API
  // 添加博客
  addBlog: (param) => post(`/admin/blog/add`, param),
  // 获取全部歌单
  getAllLinks: () => post(`/admin/link/getAll`),
  // 上传图片
  uploadFile: (params) => post(`/admin/upload/file`, params),
  // 删除歌单
  deleteSongList: (id) => get(`songList/delete?id=${id}`),

  // 更新博客
  updateBlog: (params) => post(`/admin/blog/update`, params),
  // 返回歌单里指定歌单ID的歌曲
  getListSongOfSongId: (songListId) => get(`listSong/detail?songListId=${songListId}`),
  // 删除歌单里的歌曲
  deleteListSong: (songId) => get(`ListSong/delete?songId=${songId}`)

}

export { HttpManager }
