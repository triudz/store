package com.trieu.store.controller;

import com.trieu.store.entity.User;
import com.trieu.store.repository.ProductRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("products", productRepository.findAll());
        return "index";
    }

    @GetMapping("/admin")
    public String admin(HttpSession session) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null || !"ADMIN".equals(user.getRole())) return "redirect:/login";
        return "admin";
    }
}