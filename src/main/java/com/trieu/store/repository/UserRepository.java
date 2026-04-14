package com.trieu.store.repository;

import com.trieu.store.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    // Spring Data JPA sẽ tự động tạo câu lệnh SQL tìm kiếm theo email
    User findByEmail(String email);
}