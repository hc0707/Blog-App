package io.mountblue.controller;

import io.mountblue.entity.Comment;
import io.mountblue.entity.Post;
import io.mountblue.repository.CommentRepository;
import io.mountblue.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;


@Controller
public class CommentController {
    @Autowired
    private CommentService commentService;
    @Autowired
    private CommentRepository commentRepository;

    @PostMapping("/comment/save")
    public String saveComment(@RequestParam("pid")Long postId, @ModelAttribute("cmnt") Comment comment){
        commentService.saveComment(postId,comment);
        return "redirect:/post/read/"+postId;
    }

    @GetMapping("/comment/edit")
    public String editComment(@RequestParam Long id, Model model){
        Optional<Comment> optional = commentRepository.findById(id);
        if (optional.isPresent())
            model.addAttribute("comment",optional.get());
        return "editCommentForm";
    }

    @PostMapping("/comment/update")
    public String updateComment(@RequestParam Long postId, @ModelAttribute("cmnt") Comment comment){
        commentService.UpdateComment(comment);
        return "redirect:/post/read/"+postId;
    }

    @GetMapping("/comment/delete")
    public String deleteComment(@RequestParam Long id,@RequestParam Long postId){
       commentRepository.deleteById(id);
        return "redirect:/post/read/"+postId;
    }


}
