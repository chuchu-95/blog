package com.chuchu.blog.controller.admin;

import com.chuchu.blog.entity.Blog;
import com.chuchu.blog.entity.User;
import com.chuchu.blog.service.BlogService;
import com.chuchu.blog.service.CategoryService;
import com.chuchu.blog.service.TagService;
import com.chuchu.blog.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

/**
 * @program: blog
 * @description:
 * @author: ChuChu
 * @create: 2022-05-15
 **/
@RequestMapping("/admin")
@Controller
public class BlogController {
    @Autowired
    private BlogService blogService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private TagService tagService;

    //list all the blogs
    @GetMapping("/blogs")
    public String blogsPage(@PageableDefault(size = 15,sort = {"id"},direction = Sort.Direction.DESC)Pageable pageable,
                            Model model,
                            BlogQuery blogQuery){
        model.addAttribute("page", blogService.listBlog(pageable, blogQuery));
        model.addAttribute("categories", categoryService.listCategory());
        return "admin/blogs";
    }

    //search
    @PostMapping("/blogs/search")
    public String searchBlogs(@PageableDefault(size = 15, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                              BlogQuery blogQuery,
                              Model model){
        model.addAttribute("page", blogService.listBlog(pageable, blogQuery));
        return "admin/blogs :: blogList";
    }

    //create a new blog
    @GetMapping("/blogs/input")
    public String newBlogInputPage(Model model){
        setTypeAndTag(model);
        model.addAttribute("blog", new Blog());
        return "admin/blogs-input";
    }

    private void setTypeAndTag(Model model) {
        model.addAttribute("categories", categoryService.listCategory());
        model.addAttribute("tags", tagService.listTag());
    }

    //edit a blog
    @GetMapping("/blogs/{id}/input")
    public String editBlogInputPage(@PathVariable Long id, Model model){
        setTypeAndTag(model);
        Blog oldBlog = blogService.getBlog(id);
        //tagIds do not exist in db
        oldBlog.init();
        model.addAttribute("blog", oldBlog);
        return "admin/blogs-input";
    }

    //post a new blog
    @PostMapping("/blogs")
    public String postNewBlog(Blog blog,
                              RedirectAttributes redirectAttributes, HttpSession session){
        blog.setUser((User) session.getAttribute("user"));
        blog.setCategory(categoryService.getCategory(blog.getCategory().getId()));
        blog.setTagList(tagService.listTag(blog.getTagIds()));
        Blog b;
        System.out.println("=======OutputID==========");
        System.out.println(blog.getId());
        System.out.println("=======ID==========");
        if (blog.getId() == null) {
            System.out.println("============saveblog=================");
            b =  blogService.saveBlog(blog);
        } else {
            System.out.println("============updateblog=================");
            b = blogService.updateBlog(blog, blog.getId());
        }

        if (b == null ) {
            redirectAttributes.addFlashAttribute("message", "Fail to post it");
        } else {
            redirectAttributes.addFlashAttribute("message", "Successfully post it");
        }
        return "redirect:/admin/blogs";
    }


    //delete a blog
    @GetMapping("/blogs/{id}/delete")
    public String deleteBlog(@PathVariable Long id, RedirectAttributes redirectAttributes){
        blogService.deleteBlog(id);
        redirectAttributes.addFlashAttribute("message", "Successfully delete!");
        return "redirect:/admin/blogs";
    }
}
