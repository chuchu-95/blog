package com.chuchu.blog.dao;


import com.chuchu.blog.entity.Message;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
/**
 * @program: blog
 * @description:
 * @author: ChuChu
 * @create: 2022-11-15
 **/
public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findAllByParentMessageNull(Sort Sort);
}
