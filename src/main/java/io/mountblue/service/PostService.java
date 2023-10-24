package io.mountblue.service;

import io.mountblue.repository.PostRepository;
import io.mountblue.entity.Post;
import io.mountblue.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private TagService tagService;

    @Autowired
    private UserRepository userRepository;

    public Page<Post> displayPosts(Map<String, String> queryString, String[] author, String[] tag) {
        int pageNumber = Integer.parseInt(queryString.getOrDefault("start", "1"));
        int pageSize = Integer.parseInt(queryString.getOrDefault("limit", "4"));
        String searchString = queryString.get("search");
        String sortField = queryString.get("sortField");
        String order = queryString.get("order");
        PageRequest page = PageRequest.of(pageNumber - 1, pageSize,
                order != null && order.equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC,
                sortField != null && !sortField.isEmpty() ? sortField : "publishedAt");


        return postRepository.findAllWithPagination(page, searchString == null || searchString.isEmpty() ? null : searchString.toLowerCase(),
                author != null ? Arrays.stream(author).map(s -> s.toLowerCase()).collect(Collectors.toList()) : null,
                tag != null ? Arrays.stream(tag).map(s -> s.toLowerCase()).collect(Collectors.toList()) : null);
    }


    public Post savePost(Post post, String tagString) {
        if (post.getId() == null) {
            if (tagString != null && !tagString.isEmpty())
                post.setTags(tagService.getPostTags(tagString));
            post.setCreatedAt(LocalDateTime.now());
            post.setExcerpt(post.getContent().substring(0,100));
            post.setUser(userRepository.findByNameAndRole(post.getAuthor(), "USER"));
            return postRepository.save(post);
        } else {
            Optional<Post> optional = postRepository.findById(post.getId());
            Post updatePost = optional.get();
            if (post.getPublished())
                updatePost.setPublishedAt(LocalDateTime.now());
            updatePost.setPublished(post.getPublished());
            updatePost.setUpdatedAt(LocalDateTime.now());
            updatePost.setContent(post.getContent());
            updatePost.setExcerpt(post.getContent().substring(0,100));
            updatePost.setTitle(post.getTitle());
            return postRepository.save(updatePost);
        }
    }

    public List<String> getAuthorList() {
        return userRepository.findUserNames();
    }

}
