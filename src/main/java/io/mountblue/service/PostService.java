package io.mountblue.service;

import io.mountblue.entity.Tag;
import io.mountblue.repository.PostRepository;
import io.mountblue.entity.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private TagService tagService;

    public Page<Post> displayPosts(Map<String,String> queryString,int pageNumber,int pageSize){
        String searchString = queryString.get("search");
        String sortField = queryString.get("sortField");
        String order = queryString.get("order");
        PageRequest page = PageRequest.of(pageNumber-1,pageSize,
                order!=null&&order.equals("asc")? Sort.Direction.ASC: Sort.Direction.DESC,
                sortField!=null?sortField:"publishedAt");
        Page<Post> posts=null;
        HashSet<Post> postSet = new HashSet<>();
        if (searchString==null||searchString.isEmpty()) {
            posts = postRepository.findByIsPublishedTrue(page);
        }
        else {
            for (String searchQuery : searchString.split(" ")) {
                searchQuery=searchQuery.toLowerCase();
                List<Post> searchQueryPost = postRepository.searchByKeyword(searchQuery," "+searchQuery+" ");
                postSet.addAll(searchQueryPost);
            }
            posts=new PageImpl<>(new ArrayList<>(postSet),page,postSet.size());
        }
        return posts;
    }

    public Post savePost(Post post, String tagString){
        List<Tag> postTags = tagService.getPostTags(tagString);
        post.setCreatedAt(LocalDateTime.now());
        post.setUpdatedAt(LocalDateTime.now());
        post.setTags(postTags);
        Post savedPost = postRepository.save(post);
        return savedPost;
    }

}
