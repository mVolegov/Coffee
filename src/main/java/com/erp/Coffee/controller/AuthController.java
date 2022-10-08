package com.erp.Coffee.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {

    @GetMapping("/login")
    public String loginPage() {
        return "guest/login";
    }

    @GetMapping("/success")
    public String successPage() {
        String role = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getAuthorities()
                .toArray()[0]
                .toString();

        if (role.equals("ADMIN")) {
            return "redirect:/admin";
        } else if (role.equals("BARISTA")) {
            return "redirect:/barista";
        }

        return "guest/success";
    }

    @GetMapping("/denied")
    public String accessDeniedPage() {
        return "guest/access_denied";
    }
}
