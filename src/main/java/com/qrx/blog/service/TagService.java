package com.qrx.blog.service;

import com.qrx.blog.dto.PageDto;
import com.qrx.blog.dto.TagDto;

/**
 * @author 屈燃希
 * @version 1.0
 * @project
 */
public interface TagService   {
    /**
     * 添加标签
     * @param tagDto
     */
    Boolean addTag(TagDto tagDto);

    /**
     * 删除标签
     * @param id
     */
    Boolean deleteTag(Integer[] id);

    /**
     * 获取所有标签
     * @param pageDto
     */
    void getAllTag(PageDto pageDto);

}
