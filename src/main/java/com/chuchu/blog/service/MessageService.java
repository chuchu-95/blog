package com.chuchu.blog.service;

import com.chuchu.blog.entity.Message;
import java.util.List;

/**
 * @program: blog
 * @description:
 * @author: ChuChu
 * @create: 2022-11-15
 **/

public interface MessageService {
    List<Message> listMessage();
    Message saveMessage(Message message);
}
