package com.trieu.store.service;

import com.trieu.store.entity.User;
import com.trieu.store.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Logic Đăng ký
    public boolean registerUser(User user) {
        // Kiểm tra xem email đã tồn tại chưa
        User existingUser = userRepository.findByEmail(user.getEmail());
        if (existingUser != null) {
            return false; // Email đã bị trùng
        }

        // Cấp quyền mặc định là CUSTOMER cho người dùng mới đăng ký
        user.setRole("CUSTOMER");
        userRepository.save(user);
        return true;
    }

    // Logic Đăng nhập
    public User loginUser(String email, String password) {
        User user = userRepository.findByEmail(email);

        // Kiểm tra user có tồn tại và mật khẩu có khớp không (Tạm thời so sánh chuỗi trực tiếp)
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }
}