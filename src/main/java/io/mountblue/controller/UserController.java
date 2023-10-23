package io.mountblue.controller;

import io.mountblue.entity.Tag;
import io.mountblue.entity.User;
import io.mountblue.repository.TagRepository;
import io.mountblue.repository.UserRepository;
import io.mountblue.service.TagService;
import io.mountblue.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String userLoginForm(){
        return "login";
    }

    @GetMapping("/user/posts")
    public String getUsersPosts(Model model, HttpSession session){
        model.addAttribute("posts",userService.getUsersPosts(session));
        return "listUsersPosts";
    }

    @GetMapping("/register")
    public String userRegistrationForm(){
        return "register";
    }

    @PostMapping("/register")
    public String saveUser(@ModelAttribute User user, Model model){
        model.addAttribute("message",userService.saveUser(user));
        return "register";
    }

}
