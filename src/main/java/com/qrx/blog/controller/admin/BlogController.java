package com.qrx.blog.controller.admin;

import com.qrx.blog.dto.BlogDto;
import com.qrx.blog.dto.PageDto;
import com.qrx.blog.service.impl.BlogService;
import com.qrx.blog.util.AjaxResult;
import org.apache.ibatis.annotations.Delete;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author 屈燃希
 * @version 1.0
 * @project
 */
@RestController
public class BlogController {
    @Resource
    public BlogService blogService;
    @PostMapping("/admin/blog/add")
    public AjaxResult addBlog(@RequestBody BlogDto blogDto)
    {
         String msg = blogService.addBlog(blogDto);
        return AjaxResult.success(msg);
    }
    @DeleteMapping("/admin/blog/delete")
    public AjaxResult deleteBlog(@RequestBody Integer[] ids)
    {
        if(blogService.deleteBlog(ids)){
            return AjaxResult.success("删除成功");
        }
        return AjaxResult.error("删除失败");
    }
    @PostMapping("/admin/blog/update")
    public AjaxResult updateBlog(@RequestBody BlogDto blogDto)
    {
        String msg = blogService.updateBlog(blogDto);
        return  AjaxResult.success(msg);
    }
    @PostMapping("/admin/blog/getAll")
    public AjaxResult getAll(@RequestBody PageDto pageDto)
    {
        blogService.getAllBlogs(pageDto);
        return AjaxResult.success("获取成功",pageDto);

    }
}
