package com.chuchu.blog.controller.admin;

import com.chuchu.blog.entity.User;
import com.chuchu.blog.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

/**
 * @program: blog
 * @description:
 * @author: ChuChu
 * @create: 2022-05-14
 **/
@RequestMapping("/admin")
@Controller
public class LoginController {
    @Autowired
    private LoginService loginService;

    @RequestMapping()
    public String loginPage(){
        return "admin/login";
    }

    @PostMapping("/login")
    public String loginJudge(@RequestParam String userName,
                             @RequestParam String password,
                             HttpSession session,
                             RedirectAttributes redirectAttributes){
        User user = loginService.findUser(userName, password);
        if (user != null){
            user.setPassword(null);
            session.setAttribute("user", user);
            return "admin/index";
        }else{
            redirectAttributes.addFlashAttribute("message", "Username or password wrong!");
            return "redirect:/admin";
        }
    }

    @RequestMapping("logout")
    public String logout(HttpSession session){
        session.removeAttribute("user");
        return "redirect:/admin";
    }
}
