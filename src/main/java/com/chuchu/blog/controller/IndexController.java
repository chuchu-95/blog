package com.chuchu.blog.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @program: blog
 * @description:
 * @author: ChuChu
 * @create: 2022-01-28
 **/

@Controller
public class IndexController {
    @GetMapping("/index")
    public String index(){
////        int i = 9 / 0;
//        String blog = null;
//        if(blog == null){
//            throw new NotFoundException("The blog is not exist");
//        }
        System.out.println("-----------index--------------");
        return "index";
    }

    @GetMapping("/blog")
    public String blog(){
        System.out.println("-----------blog--------------");
        return "blog";
    }

    @GetMapping("/categories")
    public  String category(){
        System.out.println("-----------category--------------");
        return "category";
    }

    @GetMapping("/tags")
    public  String tags(){
        System.out.println("-----------tags--------------");
        return "tags";
    }
    @GetMapping("/archive")
    public  String archive(){
        System.out.println("-----------archive--------------");
        return "archive";
    }

    @GetMapping("/aboutMe")
    public  String aboutMe(){
        System.out.println("-----------aboutMe--------------");
        return "aboutMe";
    }
}
