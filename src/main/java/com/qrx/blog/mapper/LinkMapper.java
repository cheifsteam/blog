package com.qrx.blog.mapper;

import com.qrx.blog.domain.Link;
import com.qrx.blog.domain.LinkExample;
import java.util.List;

public interface LinkMapper {
    int deleteByPrimaryKey(Integer linkId);

    int insert(Link record);

    int insertSelective(Link record);

    List<Link> selectByExample(LinkExample example);

    Link selectByPrimaryKey(Integer linkId);

    int updateByPrimaryKeySelective(Link record);

    int updateByPrimaryKey(Link record);
    int deleteBatch(Integer[] ids);
}