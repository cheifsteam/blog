package com.qrx.blog.service.impl;

import com.qrx.blog.domain.BlogTagRelation;
import com.qrx.blog.domain.BlogTagRelationExample;
import com.qrx.blog.mapper.BlogTagRelationMapper;
import com.qrx.blog.service.BlogTagRelationService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 屈燃希
 * @version 1.0
 * @project
 */
@Service
public class BlogTagRelationServiceImpl implements BlogTagRelationService {
    @Resource
    public BlogTagRelationMapper blogTagRelationMapper;

    public BlogTagRelationExample blogTagRelationExample;

    @Override
    public Boolean addBlogTagRelation(Integer blogId, Integer tagId) {
        final BlogTagRelation blogTagRelation = new BlogTagRelation(blogId,tagId);
        return blogTagRelationMapper.insert(blogTagRelation)>0;
    }

    @Override
    public Boolean deleteBlogTagRelationByBlogId(Integer blogId) {
        return blogTagRelationMapper.deleteByBlogId(blogId)>0;
    }

    @Override
    public Boolean deleteBlogTagRelationByTagId(Integer tagId) {
        return blogTagRelationMapper.deleteByTagId(tagId)>0;
    }

    @Override
    public List<BlogTagRelation> getBlogIdByTagId(Integer TagId) {
         List<BlogTagRelation> blogTagRelations = blogTagRelationMapper.selectByTagId(TagId);
        return blogTagRelations;
    }
}
