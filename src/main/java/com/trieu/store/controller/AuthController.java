package com.trieu.store.controller;

import com.trieu.store.entity.User;
import com.trieu.store.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    // 1. Hiển thị form đăng nhập
    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    // 2. Xử lý khi người dùng submit form đăng nhập
    @PostMapping("/login")
    public String processLogin(@RequestParam String email,
                               @RequestParam String password,
                               HttpSession session,
                               Model model) {
        User user = userService.loginUser(email, password);

        if (user != null) {
            // Đăng nhập thành công: Lưu user vào session
            session.setAttribute("loggedInUser", user);

            // Phân quyền điều hướng (Admin về dashboard, Khách về trang chủ)
            if ("ADMIN".equals(user.getRole())) {
                return "redirect:/admin"; // URL quản trị viên
            }
            return "redirect:/"; // URL khách hàng
        }

        // Đăng nhập thất bại: Báo lỗi
        model.addAttribute("error", "Email hoặc mật khẩu không chính xác!");
        return "login";
    }

    // 3. Hiển thị form đăng ký
    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new User()); // Truyền object rỗng để Thymeleaf binding dữ liệu
        return "register";
    }

    // 4. Xử lý khi người dùng submit form đăng ký
    @PostMapping("/register")
    public String processRegister(@ModelAttribute("user") User user, Model model) {
        boolean isSuccess = userService.registerUser(user);

        if (isSuccess) {
            // Thành công: Chuyển về trang đăng nhập kèm thông báo
            return "redirect:/login?registered=true";
        }

        // Thất bại (trùng email): Báo lỗi
        model.addAttribute("error", "Email này đã được sử dụng. Vui lòng chọn email khác.");
        return "register";
    }

    // 5. Đăng xuất
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("loggedInUser"); // Xóa session
        return "redirect:/";
    }
}