package com.chuchu.blog.controller.admin;

import com.chuchu.blog.entity.Category;
import com.chuchu.blog.service.CategoryService;
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

import javax.jws.WebParam;
import javax.validation.Valid;

/**
 * @program: blog
 * @description:
 * @author: ChuChu
 * @create: 2022-05-13
 **/
@Controller
@RequestMapping("/admin")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    // show category list
    @GetMapping("/categories")
    public String categoriesPage(
            @PageableDefault(size = 15, sort = {"id"}, direction = Sort.Direction.DESC)Pageable pageable,
            Model model) {
        model.addAttribute("page", categoryService.listCategory(pageable));
        return "admin/categories";
    }

    //Add button or New link
    @GetMapping("/categories/input")
    public String categoriesInputPage(Model model){
        model.addAttribute("category", new Category());
        return "admin/categories-input";
    }

    //edit button
    @GetMapping("/categories/{id}/input")
    public String editCategory(@PathVariable("id")Long curId, Model model){
        model.addAttribute("category", categoryService.getCategory(curId));
        return "admin/categories-input";
    }

    //post new category
    @PostMapping("/categories")
    public String postNewCategory(@Valid Category category, BindingResult bindingResult,
                               RedirectAttributes redirectAttributes){
        //judge if the category has existed
        Category judgeCategory = categoryService.getCategoryByName(category.getName());
        if(judgeCategory != null){
            bindingResult.rejectValue("name", "errorName", "You can not input an existed category");
        }
        if (bindingResult.hasErrors()){
            return "admin/categories-input";
        }
        Category cur = categoryService.saveCategory(category);
        if(cur == null){
            redirectAttributes.addFlashAttribute("message", "Fail to add it!");
        }else{
            redirectAttributes.addFlashAttribute("message", "Successfully add it!");
        }
        return "redirect:/admin/categories";
    }

    //update the original category
    @PostMapping("/categories/{id}")
    public String updateCategory(@PathVariable Long id, @Valid Category category, BindingResult bindingResult,
                                 RedirectAttributes redirectAttributes){
        Category judgeCategory = categoryService.getCategoryByName(category.getName());
        if(judgeCategory != null){
            bindingResult.rejectValue("name", "errorName", "You can not input an existed category");
        }
        if(bindingResult.hasErrors()){
            return "admin/categories-input";
        }
        Category cur = categoryService.updateCategory(id, category);
        if(cur == null){
            redirectAttributes.addFlashAttribute("message", "Fail to update it!");
        }else{
            redirectAttributes.addFlashAttribute("message", "Successfully update it!");
        }
        return "redirect:/admin/categories";
    }

    //delete
    @GetMapping("/categories/{id}/delete")
    public String deleteCategory(@PathVariable Long id, RedirectAttributes redirectAttributes){
        categoryService.deleteCategory(id);
        redirectAttributes.addFlashAttribute("message", "Successfully delete!");
        return "redirect:/admin/categories";
    }
}
