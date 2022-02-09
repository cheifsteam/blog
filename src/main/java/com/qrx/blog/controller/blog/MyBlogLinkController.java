package com.qrx.blog.controller.blog;

import com.qrx.blog.dto.PageDto;
import com.qrx.blog.service.LinkService;
import com.qrx.blog.util.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 屈燃希
 * @version 1.0
 * @project
 */
@RestController
public class MyBlogLinkController {
    @Autowired
    private LinkService linkService;
    @PostMapping("/blog/link/getAll")
    public AjaxResult getAllLink()
    {
        PageDto pageDto=new PageDto();
        pageDto.setPage(1);
        pageDto.setPageSize(9999);
        linkService.getAllLink(pageDto);
        return AjaxResult.success("获取成功",pageDto);
    }
}
