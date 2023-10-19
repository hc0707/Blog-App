package io.mountblue.service;

import io.mountblue.entity.Comment;
import io.mountblue.entity.Post;
import io.mountblue.entity.Tag;
import io.mountblue.repository.CommentRepository;
import io.mountblue.repository.PostRepository;
import io.mountblue.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TagService {
    @Autowired
    private TagRepository tagRepository;

    public Tag createTag(String tagName){
        Tag tag = new Tag();
        tag.setName(tagName);
        tag.setCreatedAt(LocalDateTime.now());
        return tagRepository.save(tag);
    }

    public void updateTag(Tag tag){
        tag.setUpdatedAt(LocalDateTime.now());
        tagRepository.save(tag);
    }

    public List<Tag> getPostTags(String tagString){
        String[] tagNameList = tagString.split(",");
        ArrayList<Tag> tags = new ArrayList<>();
        for(String tagName:tagNameList){
            Tag tag = tagRepository.findByName(tagName);
            if(tag==null){
                tag=createTag(tagName);
            }
            tags.add(tag);
        }
        return tags;
    }
}
