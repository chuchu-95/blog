package com.chuchu.blog.dao;

import com.chuchu.blog.entity.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @program: blog
 * @description:
 * @author: ChuChu
 * @create: 2022-05-15
 **/
@Repository
public interface BlogRepository extends JpaRepository<Blog, Long>, JpaSpecificationExecutor<Blog> {
    //===========show page============
    //list recommend blogs
    @Query("select b from Blog b where b.recommend = true")
    List<Blog> findTop(Pageable pageable);

    //update views
    @Transactional
    @Modifying
    @Query("update Blog b set b.views = b.views+1 where b.id = ?1")
    int updateView(Long id);

    @Query("select b from Blog b where b.title like ?1 and b.content like ?1")
    Page<Blog> findByQuery(String query, Pageable pageable);

}
