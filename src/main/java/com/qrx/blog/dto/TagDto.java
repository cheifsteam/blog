package com.qrx.blog.dto;

import java.util.Date;

/**
 * @author 屈燃希
 * @version 1.0
 * @project
 */
public class TagDto {
    private Integer id;

    private String tagName;

    private Date createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName == null ? null : tagName.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
