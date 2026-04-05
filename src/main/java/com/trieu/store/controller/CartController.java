package com.trieu.store.controller;

import com.trieu.store.entity.*;
import com.trieu.store.repository.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes; // Thư viện để hiện thông báo lỗi

import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired private CartRepository cartRepository;
    @Autowired private ProductRepository productRepository;

    // Xem giỏ hàng
    @GetMapping
    public String viewCart(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) return "redirect:/login";

        List<Cart> cartItems = cartRepository.findByUser(user);
        model.addAttribute("cartItems", cartItems);
        return "cart";
    }

    // 1. SỬA HÀM THÊM VÀO GIỎ: Kiểm tra số lượng tồn kho
    @PostMapping("/add")
    public String addToCart(@RequestParam Integer productId,
                            @RequestParam Integer quantity,
                            HttpSession session,
                            RedirectAttributes redirectAttributes) { // RedirectAttributes dùng để gửi thông báo sang trang khác

        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) return "redirect:/login";

        Product product = productRepository.findById(productId).orElse(null);
        Cart existingItem = cartRepository.findByUserAndProduct(user, product);

        // Tính tổng số lượng khách đang muốn mua (Số lượng đã có trong giỏ + số lượng vừa nhập thêm)
        int totalQuantityRequested = quantity;
        if (existingItem != null) {
            totalQuantityRequested += existingItem.getQuantity();
        }

        // KIỂM TRA KHO: Nếu số lượng yêu cầu lớn hơn số lượng trong kho
        if (totalQuantityRequested > product.getStockQuantity()) {
            redirectAttributes.addFlashAttribute("error", "Thất bại! Sản phẩm '" + product.getName() + "' chỉ còn " + product.getStockQuantity() + " chiếc trong kho.");
            return "redirect:/"; // Đẩy về trang chủ kèm báo lỗi
        }

        // Nếu kho đủ hàng thì tiến hành lưu vào DB
        if (existingItem != null) {
            existingItem.setQuantity(totalQuantityRequested);
            cartRepository.save(existingItem);
        } else {
            Cart newItem = new Cart();
            newItem.setUser(user);
            newItem.setProduct(product);
            newItem.setQuantity(quantity);
            cartRepository.save(newItem);
        }

        redirectAttributes.addFlashAttribute("success", "Đã thêm " + product.getName() + " vào giỏ hàng!");
        return "redirect:/";
    }

    // 2. TẠO HÀM MỚI: Cập nhật số lượng trực tiếp trong giỏ hàng
    @PostMapping("/update")
    public String updateCart(@RequestParam Integer cartId,
                             @RequestParam Integer quantity,
                             RedirectAttributes redirectAttributes) {

        Cart cartItem = cartRepository.findById(cartId).orElse(null);

        if (cartItem != null) {
            Product product = cartItem.getProduct();

            // Kiểm tra xem số lượng khách sửa có vượt quá kho không
            if (quantity > product.getStockQuantity()) {
                redirectAttributes.addFlashAttribute("error", "Sản phẩm '" + product.getName() + "' chỉ còn " + product.getStockQuantity() + " chiếc!");
            } else if (quantity > 0) {
                cartItem.setQuantity(quantity);
                cartRepository.save(cartItem);
                redirectAttributes.addFlashAttribute("success", "Đã cập nhật số lượng thành công!");
            }
        }
        return "redirect:/cart";
    }

    // Xóa sản phẩm khỏi giỏ
    @GetMapping("/remove/{id}")
    public String removeFromCart(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        cartRepository.deleteById(id);
        redirectAttributes.addFlashAttribute("success", "Đã xóa sản phẩm khỏi giỏ hàng.");
        return "redirect:/cart";
    }
}