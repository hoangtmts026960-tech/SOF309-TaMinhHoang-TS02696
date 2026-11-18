package com.poly.sof309.controllers;

import com.poly.sof309.exceptions.DuplicateUserException;
import com.poly.sof309.models.User;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * =========================================================================
 * [Y3] CONTROLLER QUẢN LÝ XÁC THỰC VÀ NGƯỜI DÙNG (MOTO STORE)
 * =========================================================================
 */
public class AuthController {

    private final List<User> userDatabase = new ArrayList<>();

    // Regex RFC chuẩn cho Email
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"
    );

    // Regex chuẩn 10 chữ số cho đầu số Việt Nam (03, 05, 07, 08, 09)
    private static final Pattern PHONE_PATTERN = Pattern.compile(
            "^(03|05|07|08|09)\\d{8}$"
    );

    public AuthController() {
        // Tài khoản quản trị mặc định ban đầu
        userDatabase.add(new User("Nguyễn Văn Admin", "admin@gmail.com", "0901234567", "123456"));
    }

    /**
     * Xác thực thông tin đăng nhập
     */
    public boolean authenticate(String username, String password) {
        if (username == null || password == null || username.trim().isEmpty() || password.isEmpty()) {
            return false;
        }

        for (User u : userDatabase) {
            if ((u.getEmail().equalsIgnoreCase(username.trim()) || u.getPhone().equals(username.trim()))
                    && u.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Kiểm tra validation đăng ký đơn giản theo mẫu hướng dẫn của Thầy
     */
    public boolean validateRegister(String username, String password) {
        if (username == null || username.trim().isEmpty()) {
            return false;
        }
        if (username.length() < 5 || username.length() > 20) {
            return false;
        }
        if (password == null || password.length() < 8) {
            return false;
        }
        return true;
    }

    /**
     * Đăng ký tài khoản mới với đầy đủ ràng buộc validation và ngoại lệ nghiệp vụ
     */
    public boolean register(String name, String email, String phone, String password) {
        // 1. Kiểm tra họ và tên
        if (name == null || name.trim().length() < 2 || name.trim().length() > 50) {
            throw new IllegalArgumentException("Họ và tên không hợp lệ (phải từ 2 đến 50 ký tự)");
        }
        if (name.matches(".*\\d.*")) {
            throw new IllegalArgumentException("Họ và tên không được chứa ký tự số");
        }

        // 2. Kiểm tra trùng lặp Email
        if (isEmailExists(email)) {
            throw new DuplicateUserException("Email đã được đăng ký trong hệ thống: " + email);
        }

        // 3. Kiểm tra định dạng Email
        if (email == null || !EMAIL_PATTERN.matcher(email.trim()).matches()) {
            return false;
        }

        // 4. Kiểm tra số điện thoại
        if (phone == null || !PHONE_PATTERN.matcher(phone.trim()).matches()) {
            return false;
        }

        // 5. Kiểm tra độ dài biên mật khẩu [8, 32]
        if (password == null || password.length() < 8 || password.length() > 32) {
            return false;
        }

        // Đăng ký thành công -> lưu vào DB
        userDatabase.add(new User(name.trim(), email.trim(), phone.trim(), password));
        return true;
    }

    /**
     * Hàm giả lập kiểm tra tài khoản đã tồn tại ném DuplicateUserException
     */
    public void registerExistingAccount(String email) {
        if (isEmailExists(email)) {
            throw new DuplicateUserException("Tài khoản đã tồn tại trong hệ thống: " + email);
        }
    }

    public boolean isEmailExists(String identifier) {
        if (identifier == null) return false;
        String trimmed = identifier.trim();
        return userDatabase.stream().anyMatch(u -> 
            u.getEmail().equalsIgnoreCase(trimmed) || 
            u.getEmail().startsWith(trimmed + "@") ||
            (trimmed.equalsIgnoreCase("admin") && u.getEmail().startsWith("admin"))
        );
    }

    public int getUserCount() {
        return userDatabase.size();
    }
}
