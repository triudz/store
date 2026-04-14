package com.trieu.store.controller;

import com.trieu.store.entity.Product;
import com.trieu.store.entity.User;
import com.trieu.store.repository.ProductRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

@Controller
public class HomeController {

    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/")
    public String home(
            @RequestParam(value = "keyword", defaultValue = "") String keyword,
            @RequestParam(value = "categoryId", defaultValue = "-1") Integer categoryId,
            @RequestParam(value = "minPrice", required = false) BigDecimal minPrice,
            @RequestParam(value = "maxPrice", required = false) BigDecimal maxPrice,
            @RequestParam(value = "page", defaultValue = "0") int page, // Trang mặc định là 0
            Model model) {
        
        BigDecimal finalMinPrice = (minPrice != null) ? minPrice : BigDecimal.ZERO;
        BigDecimal finalMaxPrice = (maxPrice != null) ? maxPrice : new BigDecimal("999999999");

        // Cấu hình: Mỗi trang có 20 sản phẩm
        Pageable pageable = PageRequest.of(page, 20);

        // Lấy dữ liệu phân trang từ Database
        Page<Product> productPage = productRepository.filterProducts(keyword, finalMinPrice, finalMaxPrice, categoryId, pageable);
        
        // Gửi các thông tin cần thiết ra HTML
        model.addAttribute("products", productPage.getContent()); // Danh sách sp của trang hiện tại
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", productPage.getTotalPages());
        model.addAttribute("keyword", keyword);
        model.addAttribute("categoryId", categoryId);
        model.addAttribute("minPrice", minPrice); 
        model.addAttribute("maxPrice", maxPrice);
        
        return "index";
    }

    @GetMapping("/admin")
    public String admin(HttpSession session) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null || !"ADMIN".equals(user.getRole())) return "redirect:/login";
        return "admin";
    }
}