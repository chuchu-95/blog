package com.chuchu.blog.service.impl;

import com.chuchu.blog.dao.CommentRepository;
import com.chuchu.blog.entity.Comment;
import com.chuchu.blog.service.CommentService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @program: blog
 * @description:
 * @author: ChuChu
 * @create: 2022-06-04
 **/
@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentRepository commentRepository;

    @Override
    public List<Comment> listTopCommentByBlogId(Long blogId) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createTime");
        List<Comment> topComments = commentRepository.findByBlogIdAndParentCommentNull(blogId, sort);
        List<Comment> dummyComments = iterateEachTopComments((topComments));
        return dummyComments;
    }

    @Override
    public Comment saveComment(Comment comment) {
        //judge parent or child
        Long parentCommentId = comment.getParentComment().getId();
        //child
        if(parentCommentId != null){
            comment.setParentComment(commentRepository.findById(parentCommentId).orElse(null));
        }else{ //parent
            comment.setParentComment(null);
        }
        comment.setCreateTime(new Date());
        return commentRepository.save(comment);
    }

    private List<Comment> iterateEachTopComments(List<Comment> topComments){
        List<Comment> dummyComments = new ArrayList<>();
        //iterate top comments and add it to afterCombineComments
        for(Comment comments: topComments){
            //not just simply add, but deep copy using BeanUtils
            Comment newComment = new Comment();
            BeanUtils.copyProperties(comments, newComment);
            dummyComments.add(newComment);
        }
        combineChildrenComments((dummyComments));
        return dummyComments;
    }

    private void combineChildrenComments(List<Comment> dummyComments){
        for(Comment comment: dummyComments){
            List<Comment> replyComments = comment.getReplyComments();
            for(Comment reply: replyComments){
                recursiveChildrenComments(reply);
            }
            comment.setReplyComments(tempComments);
            tempComments.clear();
        }
    }

    private List<Comment> tempComments = new ArrayList<>();

    private void recursiveChildrenComments(Comment replyComment){
        tempComments.add(replyComment);
        if(replyComment.getReplyComments().size() > 0){
            List<Comment> replyAndReplyComments = new ArrayList<>();
            for(Comment rrComment: replyAndReplyComments){
                if(rrComment.getReplyComments().size() > 0){
                    recursiveChildrenComments(rrComment);
                }else{
                    tempComments.add(rrComment);
                }
//                tempComments.add(rrComment);
//                if(rrComment.getReplyComment().size() > 0){
//                    recursiveChildrenComments(rrComment);
//                }
            }
        }
    }
}