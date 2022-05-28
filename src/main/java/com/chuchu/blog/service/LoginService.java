package com.chuchu.blog.service;

import com.chuchu.blog.entity.User;

/**
 * @program: blog
 * @description:
 * @author: ChuChu
 * @create: 2022-05-14
 **/
public interface LoginService {
    User findUser(String userName, String password);
}
