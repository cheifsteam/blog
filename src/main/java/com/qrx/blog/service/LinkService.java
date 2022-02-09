package com.qrx.blog.service;

import com.qrx.blog.dto.LinkDto;
import com.qrx.blog.dto.PageDto;

/**
 * @author 屈燃希
 * @version 1.0
 * @project
 */
public interface LinkService {
    /**
     * 添加友链
     * @param linkDto
     * @return
     */
    Boolean addLink(LinkDto linkDto);

    /**
     * 删除友链
     * @param ids
     * @return
     */
    Boolean deleteLink(Integer[] ids);

    /**
     * 获取所有友链
     * @param pageDto
     * @return
     */
    void getAllLink(PageDto pageDto);

    /**
     * 更新友链
     * @param linkDto
     * @return
     */
    Boolean updateLink(LinkDto linkDto);

}
