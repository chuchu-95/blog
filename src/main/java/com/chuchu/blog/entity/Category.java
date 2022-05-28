package com.chuchu.blog.entity;

import javax.validation.constraints.NotBlank;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: blog
 * @description:
 * @author: ChuChu
 * @create: 2022-01-31
 **/
@Entity
@Table(name = "t_category")
public class Category {
    @Id
    @GeneratedValue
    private Long id;
    @NotBlank(message = "The name of the category can't be null")
    private String name;

    //relationship
    @OneToMany(mappedBy = "category")
    private List<Blog> blogList = new ArrayList<>();

    public Category() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Blog> getBlogList() {
        return blogList;
    }

    public void setBlogList(List<Blog> blogList) {
        this.blogList = blogList;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
