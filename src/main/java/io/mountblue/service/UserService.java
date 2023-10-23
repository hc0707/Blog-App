package io.mountblue.service;

import io.mountblue.entity.Post;
import io.mountblue.entity.Tag;
import io.mountblue.entity.User;
import io.mountblue.repository.TagRepository;
import io.mountblue.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public String saveUser(User user) {
        String message;
        if (userRepository.findByEmail(user.getEmail()) == null) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
            message = "User registered successfully";
        } else
            message = "User with id: " + user.getEmail() + " already exist";

        return message;
    }

    public List<Post> getUsersPosts(HttpSession session){
        List<Post> postList = new ArrayList<>();
        User user = (User) session.getAttribute("user");
        if (user.getRole().equals("USER")){
            postList=userRepository.findUserPosts(user.getId());
            System.out.println(postList);
        }
        return postList;
    }

}
