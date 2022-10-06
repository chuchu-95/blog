package com.chuchu.blog.service.impl;

import com.chuchu.blog.dao.CommentRepository;
import com.chuchu.blog.entity.Comment;
import com.chuchu.blog.service.CommentService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        List<Comment> dummyComments = iterateEachTopComments(topComments);
        //temp===========
        /*for(Comment d: dummyComments){
            //d.toString();
            if(d.getReplyComments().size()>0){
                for(Comment r: d.getReplyComments()) {
                    //System.out.println("========GETREPLYCOMMENTS=====");
                }
            }
            //System.out.println("========TEMP=====");
        }*/
        return dummyComments;
    }

    @Transactional
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
            //System.out.println("=============");
            //why not just change in the original?
            //because you have to save the relationship of parents and children(original)
            Comment newComment = new Comment();
            BeanUtils.copyProperties(comments, newComment);
            dummyComments.add(newComment);
        }
        combineChildrenComments(dummyComments);
        return dummyComments;
    }

    private void combineChildrenComments(List<Comment> dummyComments){
        for(Comment comment: dummyComments){
            List<Comment> replyComments = comment.getReplyComments();
            //System.out.println("======REPLY=======");
            for(Comment reply: replyComments){
                //System.out.println("======Iterate reply=======");
                recursiveChildrenComments(reply, false);
            }
/*            if(tempComments.size() > 0){
                System.out.println("========tempComments.size() > 0=====");
            }*/
            comment.setReplyComments(tempComments);
            tempComments = new ArrayList<>();
        }
    }

    private List<Comment> tempComments = new ArrayList<>();

    private void recursiveChildrenComments(Comment replyComment, boolean deleteJudge){
        tempComments.add(replyComment);
        System.out.println("======Iterate Iterate reply=======");
        if(replyComment.getReplyComments().size() > 0){
            List<Comment> replyAndReplyComments = replyComment.getReplyComments();
            for(Comment rrComment: replyAndReplyComments){
                if(rrComment.getReplyComments().size() > 0){
                    recursiveChildrenComments(rrComment, deleteJudge);
                }else{
                    if(deleteJudge == false){
                        tempComments.add(rrComment);
                    }else{
                        System.out.println(rrComment.getId());
                        commentRepository.delete(rrComment);
                    }
                }
//                tempComments.add(rrComment);
//                System.out.println("======Iterate Iterate Iterate reply=======");
//                if(rrComment.getReplyComments().size() > 0){
//                    recursiveChildrenComments(rrComment);
//                }
            }
        }
    }

    @Transactional
    @Override
    public void deleteComment(Long id) {
        //iterate comments
        /*
        * 3 conditions:
        *   leaf/ medium/ grandpa
        * leaf: find its father and delete it from father's replyComments(List<Comment>)
        *       delete itself from db
        * medium: delete from father's reply list
        *         set its replyComments = null;
        *         delete itself from db;
        * grandpa: set its replyComments = null;
        *         delete itself;
        * */

        Comment curCmt = commentRepository.findById(id).orElse(null);
//        for(Comment c: curCmt.getReplyComments()){
//            System.out.println(1);
//        }

        if(curCmt.getParentComment() == null){ //grandpa
            System.out.println("========grandPa========");
            if(!curCmt.getReplyComments().isEmpty()){
                recursiveChildrenComments(curCmt, true);
                curCmt.setReplyComments(null);
            }
            commentRepository.deleteById(id);
        }else{  //medium and leaf

            //delete from father's replyComment
            for(Comment cmt: curCmt.getParentComment().getReplyComments()){
                if(cmt.getId() == id){
                    System.out.println("========Leaf========");
                    System.out.println("========CAN REMOVE========");
                    curCmt.getParentComment().getReplyComments().remove(cmt);
                    break;
                }
            }

            if(!curCmt.getReplyComments().isEmpty()){
                recursiveChildrenComments(curCmt, true);
                curCmt.setReplyComments(null);
            }

            System.out.println("=========medium=========");
            commentRepository.deleteById(id);
        }
        tempComments = new ArrayList<>();
    }

}
