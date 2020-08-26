package com.zr.blog.web.admin;

import com.zr.blog.po.Type;
import com.zr.blog.service.TypeService;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.validation.Valid;


@Controller
@RequestMapping("/admin")
public class TypeController {

    @Resource
    private TypeService typeService;

    @GetMapping("/types")
    public String list(
        @PageableDefault(size = 6, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable
        , Model model
        ) {
        model.addAttribute("page", typeService.listType(pageable));
        return "/admin/types";
    }

    @GetMapping("/types/input")
    public String input(Model model) {
        model.addAttribute("type", new Type());
        return "/admin/types-input";
    }


    @GetMapping("/types/{id}/input")
    public String editInput(@PathVariable Long id, Model model) {
        model.addAttribute("type", typeService.getType(id));
        return "/admin/types-input";
    }

    @PostMapping("/type")
    public String save(@Valid Type type, BindingResult result, RedirectAttributes redirectAttributes) {
        Type tName = typeService.getTypeByName(type.getName());
        if(tName != null) {
            result.rejectValue("name", "nameError", "存在的分类，不能重复添加");
            return "/admin/types-input";
        }
        if(result.hasErrors()) {
            return "/admin/types-input";
        }
        Type t = typeService.save(type);
        if(t == null) {
            //失败提示
            redirectAttributes.addFlashAttribute("message", "新增失败");
        } else {
            redirectAttributes.addFlashAttribute("message", "新增成功");
        }
        return "redirect:/admin/types";
    }

    @PostMapping("/types/{id}")
    public String edit(@PathVariable Long id, @Valid Type type
        ,BindingResult result, RedirectAttributes redirectAttributes) {
        Type tName = typeService.getTypeByName(type.getName());
        if(tName != null) {
            result.rejectValue("name", "nameError", "存在的分类，不能重复添加");
        }
        if(result.hasErrors()) {
            return "admin/types-input";
        }
        Type t = typeService.updateType(id, type);
        if(t == null) {
            redirectAttributes.addFlashAttribute("message", "修改失败");
        } else {
            redirectAttributes.addFlashAttribute("message", "修改成功");
        }
        return "redirect:/admin/types";
    }

    @GetMapping("/types/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            typeService.deleteType(id);
            redirectAttributes.addFlashAttribute("message", "删除成功");
        } catch (EmptyResultDataAccessException e) {
            redirectAttributes.addFlashAttribute("message", "删除失败");
        }
        return "redirect:/admin/types";
    }



}
