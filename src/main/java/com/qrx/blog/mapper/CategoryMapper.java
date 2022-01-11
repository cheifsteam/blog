package com.qrx.blog.mapper;

import com.qrx.blog.domain.Category;
import com.qrx.blog.domain.CategoryExample;
import java.util.List;

public interface CategoryMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Category record);

    int insertSelective(Category record);

    List<Category> selectByExample(CategoryExample example);

    Category selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Category record);

    int updateByPrimaryKey(Category record);

    Category selectByCategoryName( String categoryName);
    int deleteBatch(Integer [] ids);
}