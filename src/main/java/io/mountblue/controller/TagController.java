package io.mountblue.controller;

import io.mountblue.entity.Comment;
import io.mountblue.entity.Tag;
import io.mountblue.repository.CommentRepository;
import io.mountblue.repository.TagRepository;
import io.mountblue.service.CommentService;
import io.mountblue.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@Controller
public class TagController {
    @Autowired
    private TagService tagService;
    @Autowired
    private TagRepository tagRepository;

    @PostMapping("/tag/update")
    public String updateTag(@ModelAttribute Tag tag){
        tagService.updateTag(tag);
        return "";
    }

    @GetMapping("/tag/create")
    public String createTag(@RequestParam String tagName){
        tagService.createTag(tagName);
        return "";
    }

}
