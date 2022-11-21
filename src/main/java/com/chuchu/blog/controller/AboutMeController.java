package com.chuchu.blog.controller;

import com.chuchu.blog.entity.Message;
import com.chuchu.blog.entity.User;
import com.chuchu.blog.service.MessageService;
import com.sun.org.apache.xerces.internal.util.SynchronizedSymbolTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @program: blog
 * @description:
 * @author: ChuChu
 * @create: 2022-11-14
 **/
@Controller
public class AboutMeController {
    private String avatar = "/images/cmtPict.png";

    @Autowired
    private MessageService messageService;

    @GetMapping("/aboutMe")
    public String aboutMe(){
        return "aboutMe";
    }

    @GetMapping("/messagecomment")
    public String messageComment(Model model){
        model.addAttribute("messages",messageService.listMessage());
        return "aboutMe :: messageList";
    }

    @PostMapping("/postMessage")
    public String postMessage(Message message, HttpSession session){
        //System.out.println("======POST MESSAGR1=================");
        User user = (User) session.getAttribute("user");
        if (user != null) {
            message.setAvatar(user.getAvatar());
            message.setAdminComment(true);
        } else {
            message.setAvatar(avatar);
        }
        messageService.saveMessage(message);
        System.out.println("======POST MESSAGR1=================");
        return "redirect:/messagecomment";
    }
}
