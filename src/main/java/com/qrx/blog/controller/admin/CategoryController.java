package com.qrx.blog.controller.admin;

import com.qrx.blog.dto.CategoryDto;
import com.qrx.blog.service.CategoryService;
import com.qrx.blog.util.AjaxResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 屈燃希
 * @version 1.0
 * @project
 */
@RestController
public class CategoryController {
    @Resource
    public CategoryService categoryService;
    @PostMapping("/admin/category/add")
    public AjaxResult addCategory(@RequestBody CategoryDto categoryDto)
    {
         String msg = categoryService.addCategory(categoryDto);
      return  AjaxResult.success(msg);
    }
    @PostMapping("/admin/category/delete")
    public AjaxResult deleteCategory(@RequestBody Integer[] ids )
    {
        if (categoryService.deleteCategory(ids))
        {
            return AjaxResult.success("删除成功");
        }
        return AjaxResult.error("删除失败");
    }
    @PostMapping("/admin/category/getAll")
    public AjaxResult getAllCategory()
    {
        List<CategoryDto> allCategory = categoryService.getAllCategory();
        return AjaxResult.success("获取成功",allCategory);
    }
    @PostMapping("/admin/category/update")
    public AjaxResult updateCategory(@RequestBody CategoryDto categoryDto)
    {
      if (  categoryService.updateCategory(categoryDto))
      {
          return AjaxResult.success("更新成功");
      }
      return AjaxResult.success("更新失败");
    }
}
