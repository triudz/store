package com.trieu.store.repository;

import com.trieu.store.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

    // [UC7] Khách xem lịch sử: Tìm đơn theo user_id, xếp theo orderDate giảm dần
    List<Order> findByUserIdOrderByOrderDateDesc(Integer userId);

    // [UC8] Admin quản lý: Lấy tất cả đơn, xếp theo orderDate giảm dần
    List<Order> findAllByOrderByOrderDateDesc();

    // [UC9] Thống kê: Cộng tổng tiền (totalAmount) của các đơn có status = 'COMPLETED'
    @Query("SELECT SUM(o.totalAmount) FROM Order o WHERE o.status = 'COMPLETED'")
    BigDecimal calculateTotalRevenue();
}