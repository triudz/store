package com.trieu.store.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "orderdetails")
@IdClass(OrderDetail.OrderDetailId.class)
public class OrderDetail {

    @Id @ManyToOne @JoinColumn(name = "order_id") private Order order;
    @Id @ManyToOne @JoinColumn(name = "product_id") private Product product;

    private Integer quantity;
    @Column(name = "unit_price") private BigDecimal unitPrice;

    // Getters và Setters
    public Order getOrder() { return order; }
    public void setOrder(Order order) { this.order = order; }
    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }
    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
    public BigDecimal getUnitPrice() { return unitPrice; }
    public void setUnitPrice(BigDecimal unitPrice) { this.unitPrice = unitPrice; }

    // Lớp nội bộ để xử lý 2 khóa chính
    public static class OrderDetailId implements Serializable {
        private Integer order;
        private Integer product;
        public OrderDetailId() {}
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            OrderDetailId that = (OrderDetailId) o;
            return Objects.equals(order, that.order) && Objects.equals(product, that.product);
        }
        @Override
        public int hashCode() { return Objects.hash(order, product); }
    }
}