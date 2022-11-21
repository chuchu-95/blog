package com.chuchu.blog.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @program: blog
 * @description:
 * @author: ChuChu
 * @create: 2022-11-15
 **/
@Entity
@Table(name = "t_message")
public class Message {
    @Id
    @GeneratedValue
    private Long id;

    private String nickName;
    private String email;
    private String content;
    private String avatar;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;


    @OneToMany(mappedBy = "parentMessage")
    private List<Message> replyMessages = new ArrayList<>();


    @ManyToOne
    private Message parentMessage;

    private boolean adminComment;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isAdminComment() {
        return adminComment;
    }

    public void setAdminComment(boolean adminComment) {
        this.adminComment = adminComment;
    }

    public Message() {
    }

    public List<Message> getReplyMessages() {
        return replyMessages;
    }

    public void setReplyMessages(List<Message> replyMessages) {
        this.replyMessages = replyMessages;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMessage() {
        return content;
    }

    public void setMessage(String message) {
        this.content = message;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Message getParentMessage() {
        return parentMessage;
    }

    public void setParentMessage(Message parentMessage) {
        this.parentMessage = parentMessage;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", nickName='" + nickName + '\'' +
                ", email='" + email + '\'' +
                ", content='" + content + '\'' +
                ", avatar='" + avatar + '\'' +
                ", createTime=" + createTime +
                ", replyMessages=" + replyMessages +
                ", parentMessage=" + parentMessage +
                ", adminComment=" + adminComment +
                '}';
    }
}