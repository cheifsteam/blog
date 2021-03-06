package com.qrx.blog.controller.admin;

import com.qrx.blog.dto.LinkDto;
import com.qrx.blog.dto.PageDto;
import com.qrx.blog.service.LinkService;
import com.qrx.blog.util.AjaxResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author 屈燃希
 * @version 1.0
 * @project
 */
@RestController
public class LinkController {
    @Resource
    public LinkService LinkService;
    @PostMapping("/admin/link/add")
    public AjaxResult addLink(@RequestBody LinkDto linkDto)
    {
        if (LinkService.addLink(linkDto))
        {
            return AjaxResult.success("添加成功");
        }
        return AjaxResult.error("添加失败");
    }
    @PostMapping("/admin/link/delete")
    public AjaxResult deleteLink(@RequestBody Integer[] ids )
    {
        if (LinkService.deleteLink(ids))
        {
            return AjaxResult.success("删除成功");
        }
        return AjaxResult.error("删除失败");
    }
    @PostMapping("/admin/link/getAll")
    public AjaxResult getAllLink(@RequestBody PageDto pageDto)
    {
        LinkService.getAllLink(pageDto);
        return AjaxResult.success("获取成功",pageDto);
    }
    @PostMapping("/admin/link/update")
    public AjaxResult updateLink(@RequestBody LinkDto linkDto)
    {
        if (  LinkService.updateLink(linkDto))
        {
            return AjaxResult.success("更新成功");
        }
        return AjaxResult.success("更新失败");
    }

}
