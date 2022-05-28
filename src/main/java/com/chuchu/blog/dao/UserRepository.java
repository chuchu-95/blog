package com.chuchu.blog.dao;

import com.chuchu.blog.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @program: blog
 * @description:
 * @author: ChuChu
 * @create: 2022-05-14
 **/
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUserNameAndPassword(String userName, String password);
}
