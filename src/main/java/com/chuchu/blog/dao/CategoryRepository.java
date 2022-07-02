package com.chuchu.blog.dao;

import com.chuchu.blog.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * @program: blog
 * @description:
 * @author: ChuChu
 * @create: 2022-05-12
 **/
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findByName(String name);

    //==========show in blog page=========
//    @Query(value = "select * from t_category", nativeQuery = true)
    @Query("select t from Category t")
    List<Category> findTop(Pageable pageable);

}
