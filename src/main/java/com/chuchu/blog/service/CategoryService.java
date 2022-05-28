package com.chuchu.blog.service;

import com.chuchu.blog.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @program: blog
 * @description:
 * @author: ChuChu
 * @create: 2022-05-13
 **/
public interface CategoryService {
    Category saveCategory(Category category);

    void deleteCategory(Long id);

    Category updateCategory(Long id, Category category);

    //search
    Category getCategory(Long id);
    Category getCategoryByName(String name);

    //show in page
    Page<Category> listCategory(Pageable pageable);

    //list in blogs.html
    List<Category> listCategory();

}
