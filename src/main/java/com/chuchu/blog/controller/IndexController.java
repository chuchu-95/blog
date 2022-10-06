package com.chuchu.blog.controller;


import com.chuchu.blog.service.BlogService;
import com.chuchu.blog.service.CategoryService;
import com.chuchu.blog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @program: blog
 * @description:
 * @author: ChuChu
 * @create: 2022-01-28
 **/

@Controller
public class IndexController {
    @Autowired
    BlogService blogService;
    @Autowired
    CategoryService categoryService;
    @Autowired
    TagService tagService;

    @GetMapping("/")
    public String indexShow(@PageableDefault(size = 8, sort = {"updateTime"}, direction = Sort.Direction.DESC)Pageable pageable,
                            Model model){
        model.addAttribute("page", blogService.listBlog(pageable));
        model.addAttribute("tagList", tagService.listTagTop(8));
        model.addAttribute("categoryList", categoryService.listCategoryTop(8));
        model.addAttribute("recommendBlogList", blogService.listRecommendBlogs(8));
        return "index";
    }

    @GetMapping("/blog/{id}")
    public String showBlog(@PathVariable("id")Long id,
                           Model model){
        model.addAttribute("blog", blogService.convertAndShow(id));
        return "blog";
    }

    @PostMapping("/search")
    public String search(@PageableDefault(size = 8, sort = {"updateTime"},
                         direction = Sort.Direction.DESC) Pageable pageable,
                         @RequestParam String query, Model model) {
        model.addAttribute("page", blogService.listBlog("%"+query+"%", pageable));
        model.addAttribute("query", query);
        return "search";
    }
}
