package io.mountblue.service;

import io.mountblue.repository.PostRepository;
import io.mountblue.entity.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;

    public Page<Post> displayPosts(Integer pageNumber,Integer pageSize){
        PageRequest page = PageRequest.of(pageNumber-1,pageSize, Sort.Direction.DESC,"publishedAt");
        Page<Post> posts = postRepository.findByIsPublishedTrue(page);
        return posts;
    }

    public Long getPageCount(Integer pageSize){
        long postCount = postRepository.countByIsPublishedTrue();
        if (postCount<=pageSize)
            return null;
        long pageCount=postCount%pageSize==0?postCount/pageSize:(postCount/pageSize)+1;
        return pageCount;
    }

    public Post savePost(Post post){
        post.setCreatedAt(LocalDateTime.now());
        post.setUpdatedAt(LocalDateTime.now());
        Post savedPost = postRepository.save(post);
        return savedPost;
    }
}
