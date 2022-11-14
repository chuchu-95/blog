package com.chuchu.blog.service;

import com.chuchu.blog.entity.Blog;
import com.chuchu.blog.vo.BlogQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.*;

/**
 * @program: blog
 * @description:
 * @author: ChuChu
 * @create: 2022-05-15
 **/
public interface BlogService {
    //=============back end========================
    Blog saveBlog(Blog blog);

    void deleteBlog(Long id);

    Blog updateBlog(Blog blog, Long id);

    //search
    Blog getBlog(Long id);
    //combine search in blogController(chuchu search blogs) or if blogQuery is null, just show blogList
    Page<Blog> listBlog(Pageable pageable, BlogQuery blogQuery);

    //==============show page==============
    //list blogs
    Page<Blog> listBlog(Pageable pageable);

    //list recommend blogs
    List<Blog> listRecommendBlogs(Integer rBlogNum);

    //blog.html change markdown to html
    Blog convertAndShow(Long id);

    //search blogs
    Page<Blog> listBlog(String query,Pageable pageable);

    //tag page
    Page<Blog> listBlog(Long tagId,Pageable pageable);

    //archive page
    public Map<String, List<Blog>> archiveBlog();

    public Long countBlog();

}
