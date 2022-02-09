package com.qrx.blog.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qrx.blog.domain.*;
import com.qrx.blog.dto.BlogDto;
import com.qrx.blog.dto.PageDto;
import com.qrx.blog.mapper.BlogMapper;
import com.qrx.blog.mapper.CategoryMapper;
import com.qrx.blog.mapper.TagMapper;
import com.qrx.blog.service.BlogService;
import com.qrx.blog.service.BlogTagRelationService;
import com.qrx.blog.util.MarkDownUtil;
import com.qrx.blog.util.copyUtils.BeanCopyUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 屈燃希
 * @version 1.0
 * @project
 */
@Service
public class BlogServiceImpl implements BlogService {
    @Resource
    public BlogMapper blogMapper;
    @Resource
    public CategoryMapper categoryMapper;
    @Resource
    public TagMapper tagMapper;
    @Resource
    public BlogTagRelationService blogTagRelationService;
    public BlogExample blogExample;
    @Override
    public String addBlog(BlogDto blogDto) {
        Category category = categoryMapper.selectByCategoryName(blogDto.getCategoryName());
        if (category==null)
        {
            category=new Category();
            category.setId(1);
            category.setCategoryName("默认分类");
            blogDto.setCategoryId(category.getId());
        }
        final Blog blog = BeanCopyUtils.copyBean(blogDto, Blog.class);
        String[] tags = blogDto.getBlogTag().split(",");
        if (tags.length>6)
        {
            return "标签不能超过6个";
        }
        if (blogMapper.insertSelective(blog)>0) {

            List<Tag> tagList=new ArrayList<>();
            List<Tag> allTagList=new ArrayList<>();

            for (int i = 0; i < tags.length; i++) {
                Tag temp = tagMapper.selectByTagName(tags[i]);
                if (temp==null)
                {   Tag tag=new Tag();
                    tag.setTagName(tags[i]);
                    tagList.add(tag);
                }else {
                    allTagList.add(temp);
                }
            }
            System.out.println("开始打印");
            for (Tag tag :tagList) {
                System.out.println("开始打印");
                System.out.println(tag.getTagName());
            }

            if (!tagList.isEmpty())
            {
                tagMapper.batchInsertBlogTag(tagList);

            }
            allTagList.addAll(tagList);
            for (int i = 0; i <allTagList.size(); i++) {
                blogTagRelationService.addBlogTagRelation(blog.getId(),allTagList.get(i).getId());
            }
            return "保存成功";

        }
        return "保存失败";

    }

    @Override
    public Boolean deleteBlog(Integer[] Ids) {
        for (Integer integer :Ids) {
            blogTagRelationService.deleteBlogTagRelationByBlogId(integer);
        }
        return blogMapper.deleteBatch(Ids)>0;
    }

    @Override
    public String updateBlog(BlogDto blogDto) {
        final Category category = categoryMapper.selectByCategoryName(blogDto.getCategoryName());
        if (category==null)
        {
            category.setId(1);
            category.setCategoryName("默认分类");
            blogDto.setCategoryId(category.getId());
        }
        Blog exist=blogMapper.selectByPrimaryKey(blogDto.getId());
          if (exist==null)
          {
              return "该博客不存在";
          }
        Blog blog = BeanCopyUtils.copyBean(blogDto, Blog.class);
        String[] tags = blog.getBlogTag().split(",");
        if (tags.length>6)
        {
            return "标签不能超过6个";
        }
        if (blogMapper.updateByPrimaryKeySelective(blog)>0) {

            List<Tag> tagList=new ArrayList<>();
            List<Tag> allTagList=new ArrayList<>();

            for (int i = 0; i < tags.length; i++) {
                Tag temp = tagMapper.selectByTagName(tags[i]);
                if (temp==null)
                {   Tag tag=new Tag();
                    tag.setTagName(tags[i]);
                    tagList.add(tag);
                }else {
                    allTagList.add(temp);
                }
            }
            blogTagRelationService.deleteBlogTagRelationByBlogId(blog.getId());
            if (!tagList.isEmpty())
            {
                tagMapper.batchInsertBlogTag(tagList);

            }
            allTagList.addAll(tagList);
            for (int i = 0; i <allTagList.size(); i++) {
                blogTagRelationService.addBlogTagRelation(blog.getId(),allTagList.get(i).getId());
            }
            return "保存成功";

        }
        return "保存失败";


    }

    @Override
    public void getAllBlogs(PageDto pageDto) {
        blogExample=new BlogExample();
        PageHelper.startPage(pageDto.getPage(),pageDto.getPageSize());
        final List<Blog> blogs = blogMapper.selectByExampleWithBLOBs(blogExample);
        PageInfo pageInfo=new PageInfo(blogs);
        final List<BlogDto> blogDtos = BeanCopyUtils.copyListProperties(blogs, BlogDto::new);
        pageDto.setData(blogDtos);
        pageDto.setTotal((int) pageInfo.getTotal());
    }
    @Override
    public void getLikeBlogs(String filterName ,PageDto pageDto) {
        blogExample = new BlogExample();
        String name="%"+filterName+"%";
        blogExample.or().andBlogTagLike(name);
        blogExample.or().andBlogTitleLike(name);
        List<Blog> blogs = blogMapper.selectByExampleWithBLOBs(blogExample);
        PageHelper.startPage(pageDto.getPage(),pageDto.getPageSize());
        PageInfo pageInfo=new PageInfo(blogs);
        final List<BlogDto> blogDtos = BeanCopyUtils.copyListProperties(blogs, BlogDto::new);
        pageDto.setData(blogDtos);
        pageDto.setTotal((int) pageInfo.getTotal());
    }
    @Override
    public PageDto getHomeBlog(Integer currentPage) {
        blogExample=new BlogExample();
        PageHelper.startPage(1,currentPage);
        PageDto<Object> pageDto = new PageDto<>();
        pageDto.setPage(1);
        pageDto.setPageSize(currentPage);
        List<Blog> blogs = blogMapper.selectByExampleWithBLOBs(blogExample);
        PageInfo pageInfo=new PageInfo(blogs);
        List<BlogDto> blogDtos = BeanCopyUtils.copyListProperties(blogs, BlogDto::new);
        pageDto.setData(blogDtos);
        pageDto.setTotal((int) pageInfo.getTotal());
        return pageDto;
    }
    @Override
    public PageDto getBlogByCategoryName(Integer currentPage,String categoryName) {
        blogExample=new BlogExample();
        blogExample.createCriteria().andCategoryNameEqualTo(categoryName);
        PageHelper.startPage(1,currentPage);
        PageDto<Object> pageDto = new PageDto<>();
        pageDto.setPage(1);
        pageDto.setPageSize(currentPage);
        List<Blog> blogs = blogMapper.selectByExampleWithBLOBs(blogExample);
        PageInfo pageInfo=new PageInfo(blogs);
        List<BlogDto> blogDtos = BeanCopyUtils.copyListProperties(blogs, BlogDto::new);
        pageDto.setData(blogDtos);
        pageDto.setTotal((int) pageInfo.getTotal());
        return pageDto;
    }
    @Override
    public BlogDto getBlogDetail(Integer id){
        Blog blog = blogMapper.selectByPrimaryKey(id);
        final BlogDto blogDto = BeanCopyUtils.copyBean(blog, BlogDto.class);
        blog.setBlogViews(blogDto.getBlogViews()+1);
        blogMapper.updateByPrimaryKeySelective(blog);
        String mdToHtml = MarkDownUtil.markdownExtToHtml(blogDto.getBlogContent());
        blogDto.setBlogContent(mdToHtml);
        return blogDto;
    }

    @Override
    public PageDto getBlogByTagName(String tagName,Integer currentPage) {
        Tag tag = tagMapper.selectByTagName(tagName);
        List<BlogTagRelation> blogIdByTagId = blogTagRelationService.getBlogIdByTagId(tag.getId());
        System.out.println(blogIdByTagId);
        List<Blog> blogList=new ArrayList<>();
        for (BlogTagRelation blogTagRelation :blogIdByTagId) {
            Blog blog = blogMapper.selectByPrimaryKey(blogTagRelation.getBlogId());
            blogList.add(blog);
        }
        PageHelper.startPage(1,currentPage);
        PageDto<Object> pageDto = new PageDto<>();
        pageDto.setPage(1);
        pageDto.setPageSize(currentPage);

        PageInfo pageInfo=new PageInfo(blogList);
        System.out.println("blogList"+blogList);
        List<BlogDto> blogDtos = BeanCopyUtils.copyListProperties(blogList, BlogDto::new);
        pageDto.setData(blogDtos);
        pageDto.setTotal((int) pageInfo.getTotal());
        return pageDto;
    }

}
