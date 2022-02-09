package com.qrx.blog.controller.blog;

import com.qrx.blog.domain.Category;
import com.qrx.blog.dto.BlogDto;
import com.qrx.blog.dto.PageDto;
import com.qrx.blog.service.BlogService;
import com.qrx.blog.util.AjaxResult;
import jnr.ffi.util.EnumMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 屈燃希
 * @version 1.0
 * @project
 */
@RestController
public class MyBlogController {
    @Autowired
    public BlogService blogService;
    @PostMapping("/blog/blog/home")
    public AjaxResult getHomeBlog(@RequestParam Integer currentPage)
    {
        PageDto homeBlog = blogService.getHomeBlog(currentPage);
        return AjaxResult.success("获取成功",homeBlog);
    }
    @PostMapping("/blog/blog/getBlogByCategoryName")
    public AjaxResult getCategoryName(@RequestParam Integer currentPage, @RequestParam String categoryName)
    {
        final PageDto blogByCategoryName = blogService.getBlogByCategoryName(currentPage, categoryName);
        return  AjaxResult.success("获取成功",blogByCategoryName);
    }
    @PostMapping("/blog/blog/getBlogDetail")
    public AjaxResult getBlogDetail(@RequestParam Integer id){
        final BlogDto blogDetail = blogService.getBlogDetail(id);
        return AjaxResult.success("获取成功",blogDetail);
    }
    @PostMapping("/blog/blog/getBlogByTagName")
    public AjaxResult getBlogByTagName(@RequestParam String tagName,@RequestParam Integer currentPage){
        PageDto blog= blogService.getBlogByTagName(tagName,currentPage);
        return AjaxResult.success("获取成功",blog);
    }
    @PostMapping("/blog/blog/getLike")
    public AjaxResult getLikeBlog(@RequestParam String filterName ,@RequestParam Integer currentPage){
        PageDto<Object> pageDto = new PageDto<>();
        pageDto.setPage(1);
        pageDto.setPageSize(currentPage);
        blogService.getLikeBlogs(filterName,pageDto);
        return AjaxResult.success("获取成功",pageDto);
    }
}
