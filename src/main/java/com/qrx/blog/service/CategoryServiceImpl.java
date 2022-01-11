package com.qrx.blog.service;

import com.qrx.blog.domain.Category;
import com.qrx.blog.domain.CategoryExample;
import com.qrx.blog.dto.CategoryDto;
import com.qrx.blog.mapper.CategoryMapper;
import com.qrx.blog.service.impl.CategoryService;
import com.qrx.blog.util.copyUtils.BeanCopyUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 屈燃希
 * @version 1.0
 * @project
 */
@Service
public class CategoryServiceImpl implements CategoryService {
    @Resource
    public CategoryMapper categoryMapper;
    public CategoryExample categoryExample;
    public Boolean addCategory(CategoryDto categoryDto){
        Category temp = categoryMapper.selectByCategoryName(categoryDto.getCategoryName());
        if (temp==null)
        {
            Category category = BeanCopyUtils.copyBean(categoryDto, Category.class);
            return  categoryMapper.insert(category)>0;
        }
        return false;

    }

    @Override
    public Boolean deleteCategory(Integer Id) {
            return categoryMapper.deleteByPrimaryKey(Id)>0;
    }

    @Override
    public Boolean updateCategory(CategoryDto categoryDto) {
        Category category = categoryMapper.selectByCategoryName(categoryDto.getCategoryName());
        if (category!=null)
        {
            category.setCategoryName(categoryDto.getCategoryName());
            return categoryMapper.updateByPrimaryKeySelective(category)>0;
        }
        return false;
    }

    @Override
    public List<CategoryDto> getAllCategory() {
      categoryExample=new CategoryExample();
      List<Category> categories = categoryMapper.selectByExample(categoryExample);
      List<CategoryDto> categoryDtos = BeanCopyUtils.copyListProperties(categories, CategoryDto::new);
        return categoryDtos;
    }
}
