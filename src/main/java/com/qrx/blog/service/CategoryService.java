package com.qrx.blog.service;

import com.qrx.blog.dto.CategoryDto;
import com.qrx.blog.dto.PageDto;

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
    String addCategory(CategoryDto categoryDto);

    /**
     * 删除分类
     * @param ids
     * @return
     */
    Boolean deleteCategory(Integer[] ids);

    /**
     * 更新分类
     * @param categoryDto
     * @return
     */
    Boolean updateCategory(CategoryDto categoryDto);

    List<CategoryDto> getAllCategory();

    CategoryDto getCategory(Integer Id);
}
