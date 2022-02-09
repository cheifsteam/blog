package com.qrx.blog.controller.blog;

import com.qrx.blog.dto.CategoryDto;
import com.qrx.blog.service.CategoryService;
import com.qrx.blog.util.AjaxResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 屈燃希
 * @version 1.0
 * @project
 */
@RestController
public class MyBlogCategoryController {
    @Resource
    public CategoryService categoryService;
    @PostMapping("/blog/category/getAll")
    public AjaxResult getAllCategory()
    {
        List<CategoryDto> allCategory = categoryService.getAllCategory();
        return AjaxResult.success("获取成功",allCategory);
    }
    @PostMapping("/blog/category/get")
    public AjaxResult getCategoryById(@RequestParam Integer Id)
    {
        final CategoryDto categoryDto= categoryService.getCategory(Id);
        return AjaxResult.success("获取成功",categoryDto);

    }
}
