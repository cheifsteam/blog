package com.qrx.blog.service;

import com.qrx.blog.dto.BlogDto;
import com.qrx.blog.dto.PageDto;

/**
 * @author 屈燃希
 * @version 1.0
 * @project
 */
public interface BlogService {
    /**
     * 添加博客
     * @param blogDto
     * @return
     */
    String addBlog(BlogDto blogDto);

    /**
     * 删除博客
     * @param Ids
     * @return
     */

    Boolean deleteBlog(Integer[] Ids);

    /**
     * 更新博客
     * @param blogDto
     * @return
     */
   String updateBlog(BlogDto blogDto);

    /**
     * 获取所有博客
     * @param pageDto
     */
    void getAllBlogs(PageDto pageDto);

    void getLikeBlogs(String filterName ,PageDto pageDto);

    PageDto getHomeBlog(Integer currentPage);

    PageDto getBlogByCategoryName(Integer currentPage,String categoryName);

    BlogDto getBlogDetail(Integer id);

    PageDto getBlogByTagName(String tagName,Integer currentPage);
}
