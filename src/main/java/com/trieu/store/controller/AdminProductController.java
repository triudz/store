package com.trieu.store.controller;

import com.trieu.store.entity.Product;
import com.trieu.store.entity.User;
import com.trieu.store.repository.ProductRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.trieu.store.repository.CategoryRepository;

@Controller
@RequestMapping("/admin/products")
public class AdminProductController {

    @Autowired private ProductRepository productRepository;
    @Autowired private CategoryRepository categoryRepository;
    // 1. Hiển thị danh sách sản phẩm
    @GetMapping
    public String listProducts(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null || !"ADMIN".equals(user.getRole())) return "redirect:/login";

        model.addAttribute("products", productRepository.findAll());
        return "admin-products";
    }

    // 2. Mở form thêm sản phẩm mới
    @GetMapping("/add")
    public String showAddForm(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null || !"ADMIN".equals(user.getRole())) return "redirect:/login";

        model.addAttribute("product", new Product());
        model.addAttribute("categories", categoryRepository.findAll()); // Gửi danh sách danh mục
        model.addAttribute("title", "Thêm Sản Phẩm Mới");
        return "admin-product-form";
    }

    // 3. Mở form sửa sản phẩm
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Integer id, HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null || !"ADMIN".equals(user.getRole())) return "redirect:/login";

        Product product = productRepository.findById(id).orElseThrow();
        model.addAttribute("product", product);
        model.addAttribute("categories", categoryRepository.findAll()); // Gửi danh sách danh mục
        model.addAttribute("title", "Chỉnh Sửa Sản Phẩm");
        return "admin-product-form";
    }

    // 4. Xử lý Lưu (dùng chung cho cả Thêm và Sửa)
    @PostMapping("/save")
    public String saveProduct(@ModelAttribute Product product, RedirectAttributes ra) {
        
        // BƯỚC KIỂM TRA: Nếu link ảnh có nhập vào và dài hơn 255 ký tự thì báo lỗi ngay
        if (product.getImageUrl() != null && product.getImageUrl().length() > 255) {
            ra.addFlashAttribute("error", "❌ Lưu thất bại! Đường link hình ảnh của bạn quá dài. Vui lòng tìm link ảnh khác ngắn gọn hơn.");
            return "redirect:/admin/products"; 
        }

        // Nếu link ảnh ngắn, hợp lệ thì cho phép lưu xuống Database
        try {
            productRepository.save(product);
            ra.addFlashAttribute("success", "✅ Lưu sản phẩm thành công!");
        } catch (Exception e) {
            ra.addFlashAttribute("error", "❌ Có lỗi hệ thống xảy ra, vui lòng thử lại!");
        }
        
        return "redirect:/admin/products";
    }

    // 5. Xóa sản phẩm
    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Integer id, RedirectAttributes ra) {
        try {
            productRepository.deleteById(id);
            ra.addFlashAttribute("success", "Đã xóa sản phẩm!");
        } catch (Exception e) {
            ra.addFlashAttribute("error", "Không thể xóa vì sản phẩm này đã từng được khách hàng mua (dính khóa ngoại)!");
        }
        return "redirect:/admin/products";
    }
}