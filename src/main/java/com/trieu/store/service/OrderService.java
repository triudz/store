package com.trieu.store.service;

import com.trieu.store.entity.Order;
import com.trieu.store.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    // [UC7] Khách hàng bấm nút "Đã nhận được hàng"
    public void confirmReceivedByUser(Integer orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy đơn hàng"));

        // Chỉ cho phép xác nhận nếu đơn đang ở trạng thái SHIPPING
        if ("SHIPPING".equals(order.getStatus())) {
            order.setStatus("RECEIVED");
            orderRepository.save(order);
        } else {
            throw new RuntimeException("Trạng thái không hợp lệ!");
        }
    }

    // [UC8] Admin đổi trạng thái đơn hàng (Duyệt đơn, Báo hoàn thành...)
    public void updateOrderStatus(Integer orderId, String newStatus) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy đơn hàng"));
        order.setStatus(newStatus);
        orderRepository.save(order);
    }
}
