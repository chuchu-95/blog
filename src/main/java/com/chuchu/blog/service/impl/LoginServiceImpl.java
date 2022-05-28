package com.chuchu.blog.service.impl;

import com.chuchu.blog.dao.UserRepository;
import com.chuchu.blog.entity.User;
import com.chuchu.blog.service.LoginService;
import com.chuchu.blog.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @program: blog
 * @description:
 * @author: ChuChu
 * @create: 2022-05-14
 **/
@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public User findUser(String userName, String password) {
        return userRepository.findByUserNameAndPassword(userName, MD5Utils.code(password));
    }
}
