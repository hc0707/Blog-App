package io.mountblue.service;

import io.mountblue.entity.Tag;
import io.mountblue.repository.PostRepository;
import io.mountblue.entity.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private TagService tagService;

    public Page<Post> displayPosts(Map<String, String> queryString,String[] author,String[] tag) {
        int pageNumber = Integer.parseInt(queryString.getOrDefault("start", "1"));
        int pageSize = Integer.parseInt(queryString.getOrDefault("limit", "4"));
        String searchString = queryString.get("search");
        String sortField = queryString.get("sortField");
        String order = queryString.get("order");
        PageRequest page = PageRequest.of(pageNumber - 1, pageSize,
                order != null && order.equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC,
                sortField != null && !sortField.isEmpty() ? sortField : "publishedAt");

//        if(searchString==null||searchString.isEmpty())
//            return postRepository.findByIsPublishedTrue(page);
//        else
//        {
            return postRepository.findAllWithPagination(page,searchString==null||searchString.isEmpty()?null:searchString.toLowerCase(),
                    author!=null?Arrays.stream(author).map(s->s.toLowerCase()).collect(Collectors.toList()):null,
                    tag!=null?Arrays.stream(tag).map(s->s.toLowerCase()).collect(Collectors.toList()):null);
//            return new PageImpl<>(postList,page,postList.size());
//        }
//        if (author == null && tag == null) {
//            if (searchString == null || searchString.isEmpty()) {
//                posts = postRepository.findByIsPublishedTrue(page);
//            } else {
//                posts = getPageFromSearchString(searchString,pageNumber,pageSize);
//            }
//        } else if (author != null && tag != null) {
//            posts = postRepository.findByIsPublishedTrueAndAuthorInAndTagsNameIn(author.split(","), tag.split(","), page);
//        } else if (author == null) {
//            posts = postRepository.findByIsPublishedTrueAndTagsNameIn(tag.toLowerCase().split(","), page);
//        } else
//            posts = postRepository.findByIsPublishedTrueAndAuthorIn(author.toLowerCase().split(","), page);

//        return posts;
    }

//    public Page<Post> getPageFromSearchString(String searchString, int pageNumber, int pageSize) {
//        Set<Post> postSet = new TreeSet<>((p1,p2)->p1.getPublishedAt().compareTo(p2.getPublishedAt()));
//        for (String searchQuery : searchString.split(" ")) {
//            searchQuery = searchQuery.toLowerCase();
//            postSet.addAll(postRepository.searchByKeyword(searchQuery));
//        }
//        int startIndex = (pageNumber - 1) * pageSize;
//        int endIndex = Math.min(startIndex + pageSize, postSet.size());
//        List<Post> page = new ArrayList<>(postSet).subList(startIndex, endIndex);
//        return new PageImpl<>(page, PageRequest.of(pageNumber-1, pageSize), postSet.size());
//    }

    public Post savePost(Post post, String tagString) {
        List<Tag> postTags = tagService.getPostTags(tagString);
        post.setCreatedAt(LocalDateTime.now());
        post.setUpdatedAt(LocalDateTime.now());
        post.setTags(postTags);
        Post savedPost = postRepository.save(post);
        return savedPost;
    }

    public Set<String> getAuthorList() {
        List<Post> posts = postRepository.findAll();
        Set<String> authorList = new HashSet<>();
        posts.stream().forEach(p -> authorList.add(p.getAuthor()));
        return authorList;
    }

}
