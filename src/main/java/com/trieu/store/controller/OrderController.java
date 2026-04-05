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
    public String viewOrderHistory(Model model) {
        // TẠM THỜI: Giả sử User ID đang đăng nhập là 1 để test.
        // (Sau này làm xong Use case Đăng nhập, bạn sẽ lấy ID từ Session thực tế)
        Integer loggedInUserId = 11;

        // Gọi Repository lấy danh sách đơn của user này
        List<Order> orders = orderRepository.findByUserIdOrderByOrderDateDesc(loggedInUserId);

        // Đẩy dữ liệu sang HTML
        model.addAttribute("orders", orders);

        return "history"; // Tương ứng với file history.html trong thư mục templates
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
