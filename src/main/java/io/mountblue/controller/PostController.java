package io.mountblue.controller;

import io.mountblue.entity.Post;
import io.mountblue.repository.PostRepository;
import io.mountblue.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;


@Controller
public class PostController {
    @Autowired
    private PostService postService;
    @Autowired
    private PostRepository postRepository;

    @GetMapping
    public String displayPosts(Model model, @RequestParam(value = "start", required = false) Integer pageNumber, @RequestParam(value = "limit", required = false) Integer pageSize) {
        if(pageNumber==null)
            pageNumber=1;
        if (pageSize==null)
            pageSize=5;
        Page<Post> posts = postService.displayPosts(pageNumber, pageSize);
        model.addAttribute("posts", posts);
        model.addAttribute("pageNumber",pageNumber);
        model.addAttribute("pageSize",pageSize);
        model.addAttribute("pageCount",postService.getPageCount(pageSize));
        return "listPosts";
    }
}
