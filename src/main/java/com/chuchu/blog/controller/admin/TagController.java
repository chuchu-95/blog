package com.chuchu.blog.controller.admin;

import com.chuchu.blog.entity.Tag;
import com.chuchu.blog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

/**
 * @program: blog
 * @description:
 * @author: ChuChu
 * @create: 2022-05-15
 **/
@Controller
@RequestMapping("/admin")
public class TagController {
    @Autowired
    private TagService tagService;

    //list all the tags
    @GetMapping("/tags")
    public String tagsPage(@PageableDefault(size = 15, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable,
                           Model model){
        model.addAttribute("page", tagService.listTag(pageable));
        return "admin/tags";
    }

    //create a new tag
    @GetMapping("/tags/input")
    public String newTagInputPage(Model model){
        model.addAttribute("tag", new Tag());
        return "admin/tags-input";
    }

    //edit a tag
    @GetMapping("/tags/{id}/input")
    public String editTagInputPage(@PathVariable Long id, Model model){
        model.addAttribute("tag", tagService.getTag(id));
        return "admin/tags-input";
    }

    //post a new tag
    @PostMapping("/tags")
    public String postNewTag(@Valid Tag tag,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes){
        Tag judgeTag = tagService.getTagByName(tag.getName());
        if(judgeTag != null){
            bindingResult.rejectValue("name","nameError", "You can not input an existed tag!");
        }
        if(bindingResult.hasErrors()){
            return "admin/tags-input";
        }
        Tag t = tagService.saveTag(tag);
        if(t == null){
            redirectAttributes.addFlashAttribute("message", "Fail to add it!");
        }else{
            redirectAttributes.addFlashAttribute("message", "Successfully add it!");
        }
        return "redirect:/admin/tags";
    }

    //post an edited tag
    @PostMapping("/tags/{id}")
    public String postEditedTag(@Valid Tag tag,
                             @PathVariable Long id,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes){
        Tag judgeTag = tagService.getTagByName(tag.getName());
        if(judgeTag != null){
            bindingResult.rejectValue("name", "nameError", "You can not input an existed tag!");
        }
        if(bindingResult.hasErrors()){
            return "admin/tags-input";
        }
        Tag curTag = tagService.updateTag(tag, id);
        if(curTag == null){
            redirectAttributes.addFlashAttribute("message", "Fail to edit it!");
        }else{
            redirectAttributes.addFlashAttribute("message", "Successfully edit it!");
        }
        return "redirect:/admin/tags";
    }

    //delete a tag
    @GetMapping("/tags/{id}/delete")
    public String deleteTag(@PathVariable Long id,
                            RedirectAttributes redirectAttributes){
        tagService.deleteTag(id);
        redirectAttributes.addFlashAttribute("message", "Successfully delete it!");
        return "redirect:/admin/tags";
    }
}
