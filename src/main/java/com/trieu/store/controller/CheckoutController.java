package com.trieu.store.controller;

import com.trieu.store.entity.*;
import com.trieu.store.repository.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/checkout")
public class CheckoutController {

    @Autowired private CartRepository cartRepository;
    @Autowired private VoucherRepository voucherRepository;
    @Autowired private OrderRepository orderRepository;
    @Autowired private OrderDetailRepository orderDetailRepository;
    @Autowired private ProductRepository productRepository;

    // Hiển thị trang thanh toán
    @GetMapping
    public String showCheckoutPage(@RequestParam(required = false) String voucherCode, HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) return "redirect:/login";

        List<Cart> cartItems = cartRepository.findByUser(user);
        if (cartItems.isEmpty()) return "redirect:/cart"; // Giỏ trống không cho thanh toán

        // 1. Tính tổng tiền hàng
        BigDecimal subTotal = BigDecimal.ZERO;
        for (Cart item : cartItems) {
            BigDecimal itemTotal = item.getProduct().getPrice().multiply(new BigDecimal(item.getQuantity()));
            subTotal = subTotal.add(itemTotal);
        }

        // 2. Kiểm tra mã giảm giá
        BigDecimal discount = BigDecimal.ZERO;
        Voucher appliedVoucher = null;
        String voucherMessage = null;

        if (voucherCode != null && !voucherCode.isEmpty()) {
            appliedVoucher = voucherRepository.findByCode(voucherCode);
            if (appliedVoucher == null) {
                voucherMessage = "Mã giảm giá không tồn tại!";
            } else if (appliedVoucher.getExpiryDate().isBefore(LocalDate.now())) {
                voucherMessage = "Mã giảm giá đã hết hạn!";
                appliedVoucher = null;
            } else if (appliedVoucher.getUsageLimit() <= 0) {
                voucherMessage = "Mã giảm giá đã hết lượt sử dụng!";
                appliedVoucher = null;
            } else {
                discount = appliedVoucher.getDiscountAmount();
                voucherMessage = "Áp dụng mã thành công! Giảm " + discount + " VNĐ";
            }
        }

        // Đảm bảo tổng tiền không bị âm
        BigDecimal finalTotal = subTotal.subtract(discount);
        if (finalTotal.compareTo(BigDecimal.ZERO) < 0) finalTotal = BigDecimal.ZERO;

        model.addAttribute("cartItems", cartItems);
        model.addAttribute("subTotal", subTotal);
        model.addAttribute("discount", discount);
        model.addAttribute("finalTotal", finalTotal);
        model.addAttribute("voucherMessage", voucherMessage);
        if (appliedVoucher != null) model.addAttribute("appliedVoucherId", appliedVoucher.getId());

        return "checkout";
    }

    // Xử lý khi khách ấn NÚT "XÁC NHẬN ĐẶT HÀNG"
    @PostMapping("/place-order")
    public String placeOrder(@RequestParam BigDecimal finalTotal,
                             @RequestParam(required = false) Integer appliedVoucherId,
                             HttpSession session, RedirectAttributes redirectAttributes) {
        User user = (User) session.getAttribute("loggedInUser");
        List<Cart> cartItems = cartRepository.findByUser(user);

        // 1. Tạo đơn hàng (Order)
        Order order = new Order();
        order.setUser(user);
        order.setTotalAmount(finalTotal);

        Voucher voucher = null;
        if (appliedVoucherId != null) {
            voucher = voucherRepository.findById(appliedVoucherId).orElse(null);
            order.setVoucher(voucher);
            // Trừ lượt sử dụng mã giảm giá
            voucher.setUsageLimit(voucher.getUsageLimit() - 1);
            voucherRepository.save(voucher);
        }
        orderRepository.save(order);

        // 2. Lưu chi tiết đơn hàng (OrderDetail) & Trừ số lượng tồn kho (Product)
        for (Cart item : cartItems) {
            OrderDetail detail = new OrderDetail();
            detail.setOrder(order);
            detail.setProduct(item.getProduct());
            detail.setQuantity(item.getQuantity());
            detail.setUnitPrice(item.getProduct().getPrice());
            orderDetailRepository.save(detail);

            Product product = item.getProduct();
            product.setStockQuantity(product.getStockQuantity() - item.getQuantity());
            productRepository.save(product);
        }

        // 3. Xóa sạch giỏ hàng sau khi mua xong
        cartRepository.deleteAll(cartItems);

        redirectAttributes.addFlashAttribute("success", "Đặt hàng thành công! Cảm ơn bạn đã mua sắm.");
        return "redirect:/"; // Trở về trang chủ
    }
}