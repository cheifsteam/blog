package com.qrx.blog.controller.admin;

import com.qrx.blog.domain.Tag;
import com.qrx.blog.dto.PageDto;
import com.qrx.blog.dto.TagDto;
import com.qrx.blog.service.impl.TagService;
import com.qrx.blog.util.AjaxResult;
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
public class TagController {
    @Resource
    public TagService tagService;
    @PostMapping("/admin/tag/add")
    public AjaxResult addTag(@RequestBody TagDto tagDto){
        if (tagService.addTag(tagDto))
        {
            return AjaxResult.success("添加成功");
        }
        return AjaxResult.error("添加失败");
    }
    @PostMapping("/admin/tag/delete")
    public AjaxResult deleteTag(@RequestBody Integer[] ids){
        if(tagService.deleteTag(ids))
        {
            return AjaxResult.success("删除成功");
        }
        return AjaxResult.error("删除成功");
    }
    @PostMapping("/admin/tag/getAll")
    public AjaxResult getAllTags(@RequestBody PageDto pageDto)
    {
        tagService.getAllTag(pageDto);
        return AjaxResult.success("获取成功",pageDto);
    }
}
