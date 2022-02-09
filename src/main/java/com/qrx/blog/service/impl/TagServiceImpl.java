package com.qrx.blog.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qrx.blog.domain.Tag;
import com.qrx.blog.domain.TagExample;
import com.qrx.blog.dto.PageDto;
import com.qrx.blog.dto.TagDto;
import com.qrx.blog.mapper.TagMapper;
import com.qrx.blog.service.TagService;
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
public class TagServiceImpl implements TagService {
    @Resource
    public TagMapper tagMapper;
    public TagExample tagExample;
    @Override
    public Boolean addTag(TagDto tagDto) {
        Tag temp = tagMapper.selectByTagName(tagDto.getTagName());
        if (temp==null)
        {
            Tag tag = BeanCopyUtils.copyBean(tagDto, Tag.class);
            return tagMapper.insertSelective(tag)>0;
        }
        return false;

    }

    @Override
    public Boolean deleteTag(Integer[] ids) {

        return tagMapper.deleteBatch(ids) > 0;
    }

    @Override
    public void getAllTag(PageDto pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getPageSize());
        tagExample=new TagExample();
        List<Tag> tags = tagMapper.selectByExample(tagExample);
        List<TagDto> tagDtos = BeanCopyUtils.copyListProperties(tags, TagDto::new);
        PageInfo<Tag> pageInfo=new PageInfo(tags);
        pageDto.setData(tagDtos);
        pageDto.setTotal((int) pageInfo.getTotal());
    }
}
