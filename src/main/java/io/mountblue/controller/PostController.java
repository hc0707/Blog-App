package io.mountblue.controller;

import io.mountblue.entity.Post;
import io.mountblue.repository.PostRepository;
import io.mountblue.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;


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

    @GetMapping("/post")
    public String createPost(){
        return "postForm";
    }

    @PostMapping("/post/publish")
    public String publishPost(@ModelAttribute Post post, @RequestParam String tagString){
        post.setPublished(true);
        post.setPublishedAt(LocalDateTime.now());
        postService.savePost(post,tagString);
        return "redirect:/";
    }

    @PostMapping("/post/draft")
    public String draftPost(@ModelAttribute Post post,@RequestParam String tagString,Model model){
        post.setPublished(false);
        Post savedPost = postService.savePost(post, tagString);
        model.addAttribute("post",savedPost);
        model.addAttribute("message","Post saved to draft");
        return "postForm";
    }

    @GetMapping("/post/update/{id}")
    public String updatePost(@PathVariable Long id,Model model){
        Optional<Post> post = postRepository.findById(id);
        if (post.isPresent()) {
            model.addAttribute("post", post.get());
        }
        else
            model.addAttribute("message","Id "+id+" does not exist");
        return "postForm";
    }

    @GetMapping("/post/delete/{id}")
    public String deletePost(@PathVariable Long id,Model model){
        if (id!=null)
            postRepository.deleteById(id);
        return "redirect:/";
    }

    @GetMapping("/post/read/{id}")
    public String readPost(@PathVariable Long id,Model model){
        Optional<Post> post = postRepository.findById(id);
        if (post.isPresent()) {
            model.addAttribute("post", post.get());
            return "readPost";
        }
        else
            return "postForm";

    }
}
