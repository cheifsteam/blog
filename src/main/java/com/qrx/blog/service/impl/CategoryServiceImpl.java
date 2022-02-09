package com.qrx.blog.service.impl;

import com.qrx.blog.domain.Category;
import com.qrx.blog.domain.CategoryExample;
import com.qrx.blog.dto.CategoryDto;
import com.qrx.blog.mapper.CategoryMapper;
import com.qrx.blog.service.CategoryService;
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
    public String addCategory(CategoryDto categoryDto){
        Category temp = categoryMapper.selectByCategoryName(categoryDto.getCategoryName());
        if (temp==null)
        {
            Category category = BeanCopyUtils.copyBean(categoryDto, Category.class);
            return  categoryMapper.insertSelective(category)>0?"添加成功":"添加失败";
        }
        return "分类已经存在";

    }

    @Override
    public Boolean deleteCategory(Integer[] ids) {
            return categoryMapper.deleteBatch(ids)>0;
    }

    @Override
    public Boolean updateCategory(CategoryDto categoryDto) {
        Category category = categoryMapper.selectByPrimaryKey(categoryDto.getId());
        if (category!=null)
        {
            Category category1 = BeanCopyUtils.copyBean(categoryDto, Category.class);
            return categoryMapper.updateByPrimaryKeySelective(category1)>0;
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

    @Override
    public CategoryDto getCategory(Integer Id) {
        categoryExample=new CategoryExample();
        final Category category = categoryMapper.selectByPrimaryKey(Id);
        final CategoryDto categoryDto = BeanCopyUtils.copyBean(category, CategoryDto.class);
        return categoryDto;
    }

}
