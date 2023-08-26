package com.artyomhack.todo.controller;

import com.artyomhack.todo.model.user.UserRequest;
import com.artyomhack.todo.service.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String getLogin() {
        return "login";
    }

    //Post -> login

    @GetMapping("/reg")
    private String showRegisterForm() {
        return "reg";
    }

    @PostMapping("/reg")
    public String registerUser(UserRequest request) {
        if (!userService.existsByEmail(request.getEmail())) {
            System.out.println("CONTROLLER" + request);
            userService.create(request);
            return "redirect:/login";
        }
        return "errorPage";
    }
}
