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

    // 1. Tính tổng doanh thu theo NĂM chỉ định
    @Query(value = "SELECT SUM(total_amount) FROM orders WHERE status = 'COMPLETED' AND YEAR(order_date) = ?1", nativeQuery = true)
    java.math.BigDecimal calculateRevenueByYear(int year);

    // 2. Tính tổng doanh thu theo THÁNG và NĂM chỉ định
    @Query(value = "SELECT SUM(total_amount) FROM orders WHERE status = 'COMPLETED' AND MONTH(order_date) = ?1 AND YEAR(order_date) = ?2", nativeQuery = true)
    java.math.BigDecimal calculateRevenueByMonth(int month, int year);

    // Đếm tổng số đơn hàng đã Hoàn thành theo NĂM
    @Query(value = "SELECT COUNT(id) FROM orders WHERE status = 'COMPLETED' AND YEAR(order_date) = ?1", nativeQuery = true)
    Integer countOrdersByYear(int year);

    // Đếm tổng số đơn hàng đã Hoàn thành theo THÁNG và NĂM
    @Query(value = "SELECT COUNT(id) FROM orders WHERE status = 'COMPLETED' AND MONTH(order_date) = ?1 AND YEAR(order_date) = ?2", nativeQuery = true)
    Integer countOrdersByMonth(int month, int year);
}