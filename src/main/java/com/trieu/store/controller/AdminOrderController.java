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
@RequestMapping("/admin/orders") // Khai báo đường dẫn gốc cho khu vực quản lý đơn hàng
public class AdminOrderController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderService orderService;

    // 1. Hiển thị TOÀN BỘ đơn hàng cho Admin xem
    // 1. Hiển thị TOÀN BỘ đơn hàng và THỐNG KÊ cho Admin xem
    @GetMapping
    public String viewAllOrdersForAdmin(Model model) {
        // Lấy danh sách đơn hàng
        List<Order> allOrders = orderRepository.findAllByOrderByOrderDateDesc();

        // [UC9] Gọi hàm tính tổng doanh thu từ Repository
        java.math.BigDecimal totalRevenue = orderRepository.calculateTotalRevenue();

        // Đề phòng trường hợp chưa bán được đơn nào (database trả về null)
        if (totalRevenue == null) {
            totalRevenue = java.math.BigDecimal.ZERO;
        }

        // Đẩy dữ liệu ra view
        model.addAttribute("orders", allOrders);
        model.addAttribute("totalRevenue", totalRevenue); // Gửi tổng tiền ra HTML

        return "admin-orders";
    }

    // 2. Xử lý khi Admin bấm nút Cập nhật trạng thái
    @PostMapping("/update")
    public String updateOrderStatus(@RequestParam Integer id, @RequestParam String status) {
        // Gọi Service cập nhật trạng thái mới
        orderService.updateOrderStatus(id, status);

        // Cập nhật xong thì load lại trang danh sách
        return "redirect:/admin/orders";
    }
}