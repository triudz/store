package com.trieu.store.controller;

import com.trieu.store.entity.User;
import com.trieu.store.entity.Voucher;
import com.trieu.store.repository.VoucherRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/vouchers")
public class AdminController {

    @Autowired private VoucherRepository voucherRepository;

    @GetMapping
    public String manageVouchers(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null || !"ADMIN".equals(user.getRole())) return "redirect:/login";

        model.addAttribute("vouchers", voucherRepository.findAll());
        model.addAttribute("newVoucher", new Voucher());
        return "admin-vouchers";
    }

    @PostMapping("/add")
    public String addVoucher(@ModelAttribute Voucher newVoucher, RedirectAttributes redirectAttributes) {
        try {
            voucherRepository.save(newVoucher);
            redirectAttributes.addFlashAttribute("success", "Đã tạo mã giảm giá thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Lỗi: Mã giảm giá đã tồn tại!");
        }
        return "redirect:/admin/vouchers";
    }
}