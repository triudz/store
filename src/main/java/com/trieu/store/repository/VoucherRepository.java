package com.trieu.store.repository;
import com.trieu.store.entity.Voucher;
import org.springframework.data.jpa.repository.JpaRepository;
public interface VoucherRepository extends JpaRepository<Voucher, Integer> {
    Voucher findByCode(String code);
}