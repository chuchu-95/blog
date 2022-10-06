package com.chuchu.blog.service;

import com.chuchu.blog.entity.Comment;

import java.util.List;

/**
 * @program: blog
 * @description:
 * @author: ChuChu
 * @create: 2022-06-04
 **/
public interface CommentService {

    List<Comment> listTopCommentByBlogId(Long blogId);

    Comment saveComment(Comment comment);

    void deleteComment(Long id);
}
