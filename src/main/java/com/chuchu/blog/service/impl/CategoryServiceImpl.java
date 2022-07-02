package com.chuchu.blog.service.impl;

import com.chuchu.blog.NotFoundException;
import com.chuchu.blog.dao.CategoryRepository;
import com.chuchu.blog.entity.Category;
import com.chuchu.blog.entity.Tag;
import com.chuchu.blog.service.CategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * @program: blog
 * @description:
 * @author: ChuChu
 * @create: 2022-05-13
 **/
@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    CategoryRepository categoryRepository;

    //===========Management================
    @Transactional
    @Override
    public Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Transactional
    @Override
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }

    @Transactional
    @Override
    public Category updateCategory(Long id, Category newCategory) {
        Category oldCategory = categoryRepository.findById(id).orElse(null);
        if(oldCategory == null){
            throw new NotFoundException("This category is not exist!");
        }
        BeanUtils.copyProperties(newCategory, oldCategory);
        return categoryRepository.save(oldCategory);
    }

    @Override
    public Category getCategory(Long id) {
        return categoryRepository.findById(id).orElse(null);
    }

    @Override
    public Category getCategoryByName(String name) {
        return categoryRepository.findByName(name);
    }

    @Override
    public Page<Category> listCategory(Pageable pageable) {
        return categoryRepository.findAll(pageable);
    }

    @Override
    public List<Category> listCategory() {
        return categoryRepository.findAll();
    }

    //===========Show in blog================
    @Override
    public List<Category> listCategoryTop(Integer categoryNum) {
        Sort sort = Sort.by(Sort.Direction.DESC, "blogList.size");
        Pageable pageable = PageRequest.of(0, categoryNum, sort);
        return categoryRepository.findTop(pageable);
    }
}
