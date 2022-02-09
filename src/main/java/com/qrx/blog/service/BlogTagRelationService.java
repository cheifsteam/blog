package com.qrx.blog.service;

import com.qrx.blog.domain.BlogTagRelation;

import java.util.List;

/**
 * @author 屈燃希
 * @version 1.0
 * @project
 */
public interface BlogTagRelationService {
    /**
     * 添加关联到BlogTagRelation表
     * @param blog_id
     * @param tag_id
     * @return
     */
    Boolean addBlogTagRelation(Integer blog_id,Integer tag_id);

    /**
     * 删除博客并删除该博客在BlogTagRelation表所有标签
     * @param blog_id
     * @return
     */
    Boolean deleteBlogTagRelationByBlogId(Integer blog_id);

    /**
     * 删除标签并删除该标签在BlogTagRelation表所有博客
     * @param TagId
     * @return
     */
    Boolean deleteBlogTagRelationByTagId(Integer TagId);

    List<BlogTagRelation> getBlogIdByTagId(Integer TagId);
}
