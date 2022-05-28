package com.chuchu.blog.service.impl;

import com.chuchu.blog.NotFoundException;
import com.chuchu.blog.dao.TagRepository;
import com.chuchu.blog.entity.Tag;
import com.chuchu.blog.service.TagService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: blog
 * @description:
 * @author: ChuChu
 * @create: 2022-05-15
 **/
@Service
public class TagServiceImpl implements TagService {
    @Autowired
    private TagRepository tagRepository;

    //===========Management================
    @Transactional
    @Override
    public Tag saveTag(Tag tag) {
        return tagRepository.save(tag);
    }

    @Transactional
    @Override
    public void deleteTag(Long id) {
        tagRepository.deleteById(id);
    }

    @Transactional
    @Override
    public Tag updateTag(Tag tag, Long id) {
        Tag oldTag = tagRepository.findById(id).orElse(null);
        if (oldTag == null){
            throw new NotFoundException("This tag is not exist!");
        }
        BeanUtils.copyProperties(tag, oldTag);
        return tagRepository.save(oldTag);
    }

    @Override
    public Tag getTag(Long id) {
        Tag curTag = tagRepository.findById(id).orElse(null);
        return curTag;
    }

    @Override
    public Tag getTagByName(String tagName) {
        return tagRepository.findByName(tagName);
    }

    @Override
    public Page<Tag> listTag(Pageable pageable) {
        return tagRepository.findAll(pageable);
    }

    //show in blog input page
    @Override
    public List<Tag> listTag() {
        return tagRepository.findAll();
    }

    @Override
    public List<Tag> listTag(String ids) {
        return tagRepository.findAllById(convertToList(ids));
    }

    private List<Long> convertToList(String ids) {
        List<Long> list = new ArrayList<>();
        if (!"".equals(ids) && ids != null) {
            String[] idarray = ids.split(",");
            for (int i=0; i < idarray.length;i++) {
                list.add(new Long(idarray[i]));
            }
        }
        return list;
    }

    //===========Show================
}
