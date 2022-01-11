package com.qrx.blog.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qrx.blog.domain.Link;
import com.qrx.blog.domain.LinkExample;
import com.qrx.blog.dto.LinkDto;
import com.qrx.blog.dto.PageDto;
import com.qrx.blog.mapper.LinkMapper;
import com.qrx.blog.service.impl.LinkService;
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
public class LinkServiceImpl implements LinkService {
    @Resource
    public LinkMapper linkMapper;

    public LinkExample linkExample;

    @Override
    public Boolean addLink(LinkDto linkDto) {
        final Link link = BeanCopyUtils.copyBean(linkDto, Link.class);
       return linkMapper.insertSelective(link)>0;
    }

    @Override
    public Boolean deleteLink(Integer[] ids) {
        return linkMapper.deleteBatch(ids)>0;
    }

    @Override
    public void getAllLink(PageDto pageDto) {
        PageHelper.startPage(pageDto.getPage(),pageDto.getPageSize());
        linkExample=new LinkExample();
         List<Link> links = linkMapper.selectByExample(linkExample);
        final List<LinkDto> linkDtos = BeanCopyUtils.copyListProperties(links, LinkDto::new);
        PageInfo<Link> pageInfo=new PageInfo<>(links);
        pageDto.setData(linkDtos);
        pageDto.setTotal((int) pageInfo.getTotal());
    }

    @Override
    public Boolean updateLink(LinkDto linkDto) {
        final Link temp= linkMapper.selectByPrimaryKey(linkDto.getLinkId());
        if (temp!=null)
        {
             Link link= BeanCopyUtils.copyBean(linkDto, Link.class);
             linkMapper.updateByPrimaryKeySelective(link);
             return true;
        }
        return false;
    }
}
