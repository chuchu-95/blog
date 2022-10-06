package com.chuchu.blog.service.impl;

import com.chuchu.blog.NotFoundException;
import com.chuchu.blog.dao.BlogRepository;
import com.chuchu.blog.entity.Blog;
import com.chuchu.blog.service.BlogService;
import com.chuchu.blog.util.MarkdownUtils;
import com.chuchu.blog.vo.BlogQuery;
import org.springframework.beans.BeanUtils;
import com.chuchu.blog.util.MyBeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.*;

/**
 * @program: blog
 * @description:
 * @author: ChuChu
 * @create: 2022-05-15
 **/
@Service
public class BlogServiceImpl implements BlogService {
    @Autowired
    private BlogRepository blogRepository;

    //===========Management================
    @Transactional
    @Override
    public Blog saveBlog(Blog blog) {
        System.out.println("=======OutputID==========");
        System.out.println(blog.getId());
        System.out.println("=======ID==========");
        if (blog.getId() == null){
            blog.setCreateTime(new Date());
            blog.setUpdateTime(new Date());
            blog.setViews(0);
        }else{
            blog.setUpdateTime(new Date());
        }
        return blogRepository.save(blog);
    }

    @Transactional
    @Override
    public void deleteBlog(Long id) {
        blogRepository.deleteById(id);
    }

    @Transactional
    @Override
    public Blog updateBlog(Blog blog, Long id) {
        Blog oldBlog = blogRepository.findById(id).orElse(null);
        if(oldBlog == null){
            throw new NotFoundException("The blog is not exist!");
        }
        BeanUtils.copyProperties(blog, oldBlog, MyBeanUtils.getNullPropertyNames(blog));
        oldBlog.setUpdateTime(new Date());
        return blogRepository.save(oldBlog);
    }

    @Override
    public Blog getBlog(Long id) {
        return blogRepository.findById(id).orElse(null);
    }

    //search and list
    @Override
    public Page<Blog> listBlog(Pageable pageable, BlogQuery blogQuery) {
        Specification specification = new Specification() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                List<Predicate> temp = new ArrayList<>();
                //title search
                if(!"".equals(blogQuery.getTitle()) && blogQuery.getTitle() != null){
                    temp.add(criteriaBuilder.like(root.get("title").as(String.class),
                            "%" + blogQuery.getTitle() + "%"));
                }
                //category search
                if(blogQuery.getCategoryId() != null){
                    temp.add(criteriaBuilder.equal(root.get("category").get("id"), blogQuery.getCategoryId()));
                }
                //recommend search
                if(blogQuery.isRecommend()){
                    temp.add(criteriaBuilder.equal(root.get("recommend"), blogQuery.isRecommend()));
                }
                predicates.add(criteriaBuilder.and(temp.toArray(new Predicate[temp.size()])));
                query.where(predicates.toArray(new Predicate[predicates.size()])).getRestriction();
                return null;
            }
        };
        return blogRepository.findAll(specification, pageable);
    }

    //===========Show page================
    @Override
    public Page<Blog> listBlog(Pageable pageable) {
        //return blogRepository.findAll(pageable);
        return blogRepository.findAllPublished(pageable);
    }

    @Override
    public List<Blog> listRecommendBlogs(Integer rBlogNum) {
        Sort sort = Sort.by(Sort.Direction.DESC, "updateTime");
        Pageable pageable = PageRequest.of(0, rBlogNum, sort);
        return blogRepository.findTop(pageable);
    }

    @Transactional
    @Override
    public Blog convertAndShow(Long id) {
        Blog preBlog = blogRepository.findById(id).orElse(null);
        if(preBlog == null){
            throw new NotFoundException("This blog doesn't exist");
        }
        Blog cvtBlog = new Blog();
        BeanUtils.copyProperties(preBlog, cvtBlog);
        String content = cvtBlog.getContent();
        cvtBlog.setContent(MarkdownUtils.markdownToHtmlExtensions(content));
        blogRepository.updateView(id);
        return cvtBlog;
    }


    //search blog
    @Override
    public Page<Blog> listBlog(String query, Pageable pageable) {
        return blogRepository.findByQuery(query,pageable);
    }

    //tag page
    @Override
    public Page<Blog> listBlog(Long tagId, Pageable pageable) {
        return blogRepository.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                Join join = root.join("tagList");
                return cb.equal(join.get("id"),tagId);
            }
        },pageable);
    }
}
