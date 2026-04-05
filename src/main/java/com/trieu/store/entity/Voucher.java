package com.trieu.store.entity;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "vouchers")
public class Voucher {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Integer id;

    @Column(nullable = false, unique = true) private String code;
    @Column(name = "discount_amount", nullable = false) private BigDecimal discountAmount;
    @Column(name = "usage_limit", nullable = false) private Integer usageLimit;

    @Column(name = "expiry_date", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate expiryDate;

    // Getters và Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public BigDecimal getDiscountAmount() { return discountAmount; }
    public void setDiscountAmount(BigDecimal discountAmount) { this.discountAmount = discountAmount; }
    public Integer getUsageLimit() { return usageLimit; }
    public void setUsageLimit(Integer usageLimit) { this.usageLimit = usageLimit; }
    public LocalDate getExpiryDate() { return expiryDate; }
    public void setExpiryDate(LocalDate expiryDate) { this.expiryDate = expiryDate; }
}