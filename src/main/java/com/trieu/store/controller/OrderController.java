package com.trieu.store.controller;

import com.trieu.store.entity.Order;
import com.trieu.store.repository.OrderRepository;
import com.trieu.store.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderService orderService;

    // 1. Hiển thị danh sách đơn hàng của khách
    @GetMapping("/history")
    public String viewOrderHistory(jakarta.servlet.http.HttpSession session, Model model) {

        // Bước A: Lấy thông tin người dùng đang đăng nhập từ Session
        // (Lưu ý: Đảm bảo import đúng class User của entity bạn nhé)
        com.trieu.store.entity.User loggedInUser = (com.trieu.store.entity.User) session.getAttribute("loggedInUser");

        // Bước B: Kiểm tra bảo mật - Nếu chưa đăng nhập (hoặc hết hạn session) thì đẩy về trang Login
        if (loggedInUser == null) {
            return "redirect:/login";
        }

        // Bước C: Lấy ID THỰC TẾ của chính người đang đăng nhập đó
        Integer realUserId = loggedInUser.getId();

        // Bước D: Gọi Repository tìm đúng đơn hàng của ID này
        List<Order> orders = orderRepository.findByUserIdOrderByOrderDateDesc(realUserId);

        // Đẩy dữ liệu sang HTML
        model.addAttribute("orders", orders);

        return "history";
    }

    // 2. Xử lý khi khách bấm nút "Đã nhận được hàng"
    @PostMapping("/history/confirm/{id}")
    public String confirmOrder(@PathVariable Integer id) {
        // Gọi Service để đổi trạng thái thành "RECEIVED"
        orderService.confirmReceivedByUser(id);

        // Xong việc thì load lại trang lịch sử
        return "redirect:/history";
    }
}
