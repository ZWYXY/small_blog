package com.zr.blog.web.admin;

import com.zr.blog.po.Tag;
import com.zr.blog.po.Type;
import com.zr.blog.service.TagService;
import org.springframework.dao.EmptyResultDataAccessException;
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

import javax.annotation.Resource;
import javax.validation.Valid;


@Controller
@RequestMapping("/admin")
public class TagController {

    @Resource
    private TagService tagService;

    /**
     * 获取标签列表
     * @param pageable
     * @param model
     * @return
     */
    @GetMapping("/tags")
    public String list(
            @PageableDefault(size = 6, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable
            , Model model) {
        model.addAttribute("page", tagService.TagList(pageable));
        return "/admin/tags";
    }

    /**
     * 跳转到新增标签页面
     * @param model
     * @return
     */
    @GetMapping("/tag/input")
    public String input(Model model) {
        model.addAttribute("tag", new Tag());
        return "/admin/tags-input";
    }

    /**
     * 新增标签
     * @param tag
     * @param result
     * @param redirectAttributes
     * @return
     */
    @PostMapping("/tag")
    public String save(@Valid Tag tag, BindingResult result, RedirectAttributes redirectAttributes) {
        Tag tag1 = tagService.getTagByName(tag.getName());
        if(tag1 != null) {
            result.rejectValue("name", "nameError", "存在的标签，不能重复添加");
            return "/admin/tags-input";
        }
        if(result.hasErrors()) {
            return "/admin/tags-input";
        }
        Tag t = tagService.save(tag);
        if(t == null) {
            //失败提示
            redirectAttributes.addFlashAttribute("message", "新增失败");
        } else {
            redirectAttributes.addFlashAttribute("message", "新增成功");
        }
        return "redirect:/admin/tags";
    }

    /**
     * 跳转到修改标签界面
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/tag/{id}/input")
    public String editInput(@PathVariable Long id, Model model) {
        model.addAttribute("tag", tagService.getTag(id));
        return "/admin/tags-input";
    }

    /**
     * 修改标签
     * @param id
     * @param tag
     * @param result
     * @param redirectAttributes
     * @return
     */
    @PostMapping("/tag/{id}")
    public String edit(@PathVariable Long id, @Valid Tag tag
        ,BindingResult result, RedirectAttributes redirectAttributes) {
        Tag tag1 = tagService.getTagByName(tag.getName());
        if(tag1 != null) {
            result.rejectValue("name", "nameError", "存在的标签，不能修改成重复的名称");
        }
        if(result.hasErrors()) {
            return "admin/tags-input";
        }
        Tag t = tagService.updateTag(id, tag);
        if(t == null) {
            redirectAttributes.addFlashAttribute("message", "修改失败");
        } else {
            redirectAttributes.addFlashAttribute("message", "修改成功");
        }
        return "redirect:/admin/tags";
    }

    /**
     * 删除标签
     * @param id
     * @param redirectAttributes
     * @return
     */
    @GetMapping("/tag/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            tagService.deleteTag(id);
            redirectAttributes.addFlashAttribute("message", "删除成功");
        } catch (EmptyResultDataAccessException e) {
            redirectAttributes.addFlashAttribute("message", "删除失败");
        }
        return "redirect:/admin/tags";
    }


}
