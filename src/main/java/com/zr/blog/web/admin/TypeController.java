package com.zr.blog.web.admin;

import com.zr.blog.po.Type;
import com.zr.blog.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;


@Controller
@RequestMapping("/admin")
public class TypeController {

    @Resource
    private TypeService typeService;

    @GetMapping("/types")
    public String list(
        @PageableDefault(size = 18, sort = {"id"}, direction = Sort.Direction.ASC) Pageable pageable
        , Model model
        ) {
        model.addAttribute("page", typeService.listType(pageable));
        return "admin/types";
    }

    @GetMapping("/types/input")
    public String input() {
        return "admin/types-input";
    }


    @PostMapping("/type")
    public String save(Type type, RedirectAttributes redirectAttributes) {
        Type t = typeService.save(type);
        if(t == null) {
            //失败提示
            redirectAttributes.addFlashAttribute("message", "操作失败");
        } else {
            redirectAttributes.addFlashAttribute("message", "操作成功");
        }
        return "redirect:/admin/types";
    }


}
