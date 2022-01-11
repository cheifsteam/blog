package com.qrx.blog.service.impl;

import com.qrx.blog.dto.CategoryDto;

import java.util.List;

/**
 * @author 屈燃希
 * @version 1.0
 * @project
 */
public interface CategoryService {
    /**
     * 添加分类
     * @param categoryDto
     * @return
     */
    Boolean addCategory(CategoryDto categoryDto);

    /**
     * 删除分类
     * @param Id
     * @return
     */
    Boolean deleteCategory(Integer Id);

    /**
     * 更新分类
     * @param categoryDto
     * @return
     */
    Boolean updateCategory(CategoryDto categoryDto);

    List<CategoryDto> getAllCategory();
}
