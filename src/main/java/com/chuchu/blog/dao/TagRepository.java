package com.chuchu.blog.dao;

import com.chuchu.blog.entity.Tag;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.function.Function;

/**
 * @program: blog
 * @description:
 * @author: ChuChu
 * @create: 2022-05-15
 **/
@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
    Tag findByName(String tagName);

}
