package com.qrx.blog.controller.admin;

import com.qrx.blog.constant.Constants;
import com.qrx.blog.dto.BlogDto;
import com.qrx.blog.dto.PageDto;
import com.qrx.blog.service.impl.BlogService;
import com.qrx.blog.util.AjaxResult;
import com.qrx.blog.util.MyBlogUtils;
import org.apache.ibatis.annotations.Delete;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

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
    @PostMapping("/admin/blog/uploadFileByEditormd")
    public AjaxResult uploadFileByEditormd(
            HttpServletRequest request,
            HttpServletResponse response, @RequestPart("file")MultipartFile file) throws URISyntaxException {
        String fileName=file.getOriginalFilename();
        String suffixName= fileName.substring(fileName.lastIndexOf("."));
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd_HHmmss");
        Random r=new Random();
        StringBuilder tempName = new StringBuilder();
        tempName.append(sdf.format(new Date())).append(r.nextInt(100)).append(suffixName);
        String newFileName = tempName.toString();
        //创建文件
        File destFile = new File(Constants.RESOURCE_WIN_PATH + newFileName);
        String fileUrl = MyBlogUtils.getHost(new URI(request.getRequestURL() + "")) + "/upload/" + newFileName;
        File fileDirectory = new File(Constants.RESOURCE_WIN_PATH);
        try {
            if (!fileDirectory.exists()) {
                if (!fileDirectory.mkdir()) {
                    throw new IOException("文件夹创建失败,路径为：" + fileDirectory);
                }
            }
            file.transferTo(destFile);
            System.out.println(destFile);
            request.setCharacterEncoding("utf-8");
            response.setHeader("Content-Type", "text/html");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return AjaxResult.success("获取成功",fileUrl);
    }
}
