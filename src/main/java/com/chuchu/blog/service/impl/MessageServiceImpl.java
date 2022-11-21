package com.chuchu.blog.service.impl;

import com.chuchu.blog.dao.MessageRepository;
import com.chuchu.blog.entity.Message;
import com.chuchu.blog.service.MessageService;
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
 * @create: 2022-11-15
 **/
@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    private MessageRepository messageRepository;

    @Override
    public List<Message> listMessage() {
        Sort sort = Sort.by(Sort.Direction.DESC, "createTime");
        List<Message> messages = messageRepository.findAllByParentMessageNull(sort);
        return eachComment(messages);
    }

    @Transactional
    @Override
    public Message saveMessage(Message message) {
        Long parentMessageId = message.getParentMessage().getId();
        if (parentMessageId != -1) {
            message.setParentMessage(messageRepository.findById(parentMessageId).orElse(null));
        } else {
            message.setParentMessage(null);
        }
        message.setCreateTime(new Date());
        return messageRepository.save(message);
    }
    
    private List<Message> eachComment(List<Message> messages) {
        List<Message> commentsView = new ArrayList<>();
        for (Message message : messages) {
            Message c = new Message();
            BeanUtils.copyProperties(message,c);
            commentsView.add(c);
        }
        combineChildren(commentsView);
        return commentsView;
    }


    private void combineChildren(List<Message> messages) {
        for (Message message : messages) {
            List<Message> replies1 = message.getReplyMessages();
            for(Message reply1 : replies1) {
                recursively(reply1);
            }
            message.setReplyMessages(tempReplies);
            tempReplies = new ArrayList<>();
        }
    }


    private List<Message> tempReplies = new ArrayList<>();

    private void recursively(Message message) {
        tempReplies.add(message);//顶节点添加到临时存放集合
        if (message.getReplyMessages().size()>0) {
            List<Message> replys = message.getReplyMessages();
            for (Message reply : replys) {
                tempReplies.add(reply);
                if (reply.getReplyMessages().size()>0) {
                    recursively(reply);
                }
            }
        }
    }
}
