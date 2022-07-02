package com.chuchu.blog.service;

import com.chuchu.blog.entity.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @program: blog
 * @description:
 * @author: ChuChu
 * @create: 2022-05-15
 **/
public interface TagService {
    Tag saveTag(Tag tag);

    void deleteTag(Long id);

    Tag updateTag(Tag tag, Long id);

    Tag getTag(Long id);
    Tag getTagByName(String tagName);

    Page<Tag> listTag(Pageable pageable);

    List<Tag> listTag();

    List<Tag> listTag(String ids);

    //===========Show in blog page================
    List<Tag> listTagTop(Integer tagNum);
}
