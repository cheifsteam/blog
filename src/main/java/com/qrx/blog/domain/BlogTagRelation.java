package com.qrx.blog.domain;

public class BlogTagRelation {
    private Integer id;

    private Integer blogId;

    private Integer tagId;

    public BlogTagRelation(Integer blogId, Integer tagId) {
        this.blogId = blogId;
        this.tagId = tagId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBlogId() {
        return blogId;
    }

    public void setBlogId(Integer blogId) {
        this.blogId = blogId;
    }

    public Integer getTagId() {
        return tagId;
    }

    public void setTagId(Integer tagId) {
        this.tagId = tagId;
    }

}