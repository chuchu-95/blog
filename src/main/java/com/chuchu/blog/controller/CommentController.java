package com.chuchu.blog.controller;

import com.chuchu.blog.entity.Comment;
import com.chuchu.blog.entity.User;
import com.chuchu.blog.service.BlogService;
import com.chuchu.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
/**
 * @program: blog
 * @description:
 * @author: ChuChu
 * @create: 2022-06-04
 **/
@Controller
public class CommentController {
    @Autowired
    private CommentService commentService;
    @Autowired
    private BlogService blogService;

    //@Value("${comment.avatar}")
    private String avatar = "/images/cmtPict.png";

    @GetMapping("/comments/{blogId}")
    public String showComments(@PathVariable Long blogId,
                               Model model){
        model.addAttribute("comments", commentService.listTopCommentByBlogId(blogId));
        return "blog :: commentList";
    }

    @PostMapping("/comments")
    public String postComment(Comment comment, HttpSession session){
        //comment.setNickName("temp");
        Long blogId = comment.getBlog().getId();
        comment.setBlog(blogService.getBlog(blogId));
        User user = (User) session.getAttribute("user");
        if (user != null) {
            comment.setAvatar(user.getAvatar());
            comment.setAdminComment(true);
        } else {
            comment.setAvatar(avatar);
        }
        commentService.saveComment(comment);
        return "redirect:/comments/" + blogId;
    }


    // delete comment
    @GetMapping("/comments/{commentId}/delete/{blogId}")
    public String deleteComments(@PathVariable Long commentId, @PathVariable Long blogId){

        commentService.deleteComment(commentId);

        return "redirect:/blog/" + blogId;
    }
}
