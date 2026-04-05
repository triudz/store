package com.trieu.store.repository;
import com.trieu.store.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
public interface OrderDetailRepository extends JpaRepository<OrderDetail, OrderDetail.OrderDetailId> {}