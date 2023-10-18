package io.mountblue.service;

import io.mountblue.entity.Comment;
import io.mountblue.entity.Post;
import io.mountblue.repository.CommentRepository;
import io.mountblue.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private PostRepository postRepository;

    public void saveComment(Long postId, Comment comment){
        comment.setCreatedAt(LocalDateTime.now());
        Optional<Post> optional = postRepository.findById(postId);
        if (optional.isPresent()){
            comment.setPost(optional.get());
        }
        commentRepository.save(comment);
    }

    public void UpdateComment(Comment comment){
        Optional<Comment> optional = commentRepository.findById(comment.getId());
        if (optional.isPresent()){
            Comment updatedComment = optional.get();
            updatedComment.setUpdatedAt(LocalDateTime.now());
            updatedComment.setComment(comment.getComment());
            commentRepository.save(updatedComment);
        }
    }
}
