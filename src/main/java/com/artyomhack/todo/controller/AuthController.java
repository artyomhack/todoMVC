package com.artyomhack.todo.controller;

import com.artyomhack.todo.model.user.UserRequest;
import com.artyomhack.todo.service.user.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    public AuthController(UserService userService, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
    }

    @GetMapping("/login")
    public String getSHowLogin(){
        return "login";
    }

    @PostMapping("/auth/login")
    public String login(@RequestParam("email") String email, @RequestParam("passwordHash") String passwordHash) {
        System.out.printf("EMAIL: %s\n PASSHASH: %s \n", email, passwordHash);
        try {
            var user = userService.loadByEmail(email);
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            email, passwordHash
                    )
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
            return "redirect:/user/" + user.getId();
        } catch (AuthenticationException e) {
            return "redirect:/login?error";
        }
    }

    @GetMapping("/reg")
    private String showRegisterForm() {
        return "/auth/reg";
    }

    @PostMapping("/reg")
    public String registerUser(UserRequest request) {
        if (!userService.existsByEmail(request.getEmail())) {
            userService.create(request);
            return "redirect:/login";
        }
        return "errorPage";
    }
}
