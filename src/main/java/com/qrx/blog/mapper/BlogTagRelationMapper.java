package com.qrx.blog.mapper;

import com.qrx.blog.domain.BlogTagRelation;
import com.qrx.blog.domain.BlogTagRelationExample;
import java.util.List;

public interface BlogTagRelationMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(BlogTagRelation record);

    int insertSelective(BlogTagRelation record);

    List<BlogTagRelation> selectByExample(BlogTagRelationExample example);

    BlogTagRelation selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BlogTagRelation record);

    int updateByPrimaryKey(BlogTagRelation record);
    int deleteByTagId(Integer tagId);
    int deleteByBlogId(Integer blogId);
}