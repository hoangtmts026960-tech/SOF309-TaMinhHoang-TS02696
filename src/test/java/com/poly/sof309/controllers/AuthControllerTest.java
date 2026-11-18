package com.poly.sof309.controllers;

import com.poly.sof309.exceptions.DuplicateUserException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

/**
 * =========================================================================
 * [Y3] BỘ UNIT TEST JUNIT 5 CHO AUTHCONTROLLER (MOTO STORE)
 * Kế thừa kỹ thuật: @ParameterizedTest (Lab 2), Exception & Timeout (Lab 3)
 * Sinh viên: Tạ Minh Hoàng (MSSV: TS02696 - Lớp: SD21307)
 * =========================================================================
 */
public class AuthControllerTest {

    private AuthController authController;
    private AuthController authService; // Khai báo alias authService để khớp 100% với slide Thầy

    @BeforeEach
    public void setUp() {
        authController = new AuthController();
        authService = authController;
    }

    // =========================================================================
    // BƯỚC 2.2: PARAMETERIZED TEST KIỂM TRA BIÊN FORM ĐĂNG KÝ (KHỚP 100% SLIDE)
    // =========================================================================
    @ParameterizedTest
    @DisplayName("Kiểm tra validation đăng ký tài khoản với nhiều bộ dữ liệu biên")
    @CsvSource({
        "'', 'password123', false, 'Username không được để trống'",
        "'user', 'password123', false, 'Username phải từ 5 đến 20 ký tự'",
        "'validUser', '123', false, 'Mật khẩu phải từ 8 ký tự'",
        "'validUser', 'Pass@1234', true, 'Đăng ký thành công'"
    })
    public void testRegisterValidation(String username, String password, boolean expectedSuccess, String message) {
        boolean actualResult = authService.validateRegister(username, password);
        assertEquals(expectedSuccess, actualResult, message);
    }

    // =========================================================================
    // BƯỚC 2.3: EXCEPTION TESTING (ASSERTTHROWS) (KHỚP 100% SLIDE)
    // =========================================================================
    @Test
    @DisplayName("Ném ngoại lệ DuplicateUserException khi tài khoản đã tồn tại")
    public void testRegisterDuplicateUserThrowsException() {
        assertThrows(DuplicateUserException.class, () -> {
            authService.registerExistingAccount("admin");
        });
    }

    // =========================================================================
    // BƯỚC 2.4: TIMEOUT TESTING (TIMEOUT 2 SECONDS)
    // =========================================================================
    @Test
    @Timeout(value = 2, unit = TimeUnit.SECONDS)
    @DisplayName("Kiểm tra thời gian xác thực tài khoản không bị treo vòng lặp vô tận (Timeout 2s)")
    public void testAuthenticateTimeout() {
        boolean result = authController.authenticate("admin@gmail.com", "123456");
        assertTrue(result);
    }

    // =========================================================================
    // BỔ SUNG: CÁC TEST CASES ĐẶC TẢ CHI TIẾT THEO SPECIFICATIONS (Y3)
    // =========================================================================

    @Test
    @DisplayName("TC_CTRL_AUTH_01: Đăng nhập thành công với tài khoản quản trị mặc định")
    public void testLoginSuccessWithDefaultAdmin() {
        boolean result = authController.authenticate("admin@gmail.com", "123456");
        assertTrue(result, "Đăng nhập phải thành công với tài khoản admin hợp lệ");
    }

    @Test
    @DisplayName("TC_CTRL_AUTH_02: Đăng nhập thất bại khi nhập sai mật khẩu")
    public void testLoginFailureWithWrongPassword() {
        boolean result = authController.authenticate("admin@gmail.com", "mat_khau_sai_123");
        assertFalse(result, "Đăng nhập phải thất bại khi mật khẩu không chính xác");
    }

    @Test
    @DisplayName("TC_CTRL_AUTH_03: Đăng nhập thất bại khi tài khoản chưa từng đăng ký")
    public void testLoginFailureWithUnregisteredAccount() {
        boolean result = authController.authenticate("chuadangky@domain.com", "123456");
        assertFalse(result, "Đăng nhập phải thất bại khi tài khoản chưa tồn tại");
    }

    @ParameterizedTest
    @DisplayName("TC_CTRL_AUTH_04: Kiểm tra phân tích giá trị biên độ dài mật khẩu [8, 32] ký tự")
    @CsvSource({
        "'1234567', false, 'Biên dưới Min-1 (7 ký tự): Không hợp lệ'",
        "'12345678', true, 'Biên dưới Min (8 ký tự): Hợp lệ'",
        "'12345678901234567890123456789012', true, 'Biên trên Max (32 ký tự): Hợp lệ'",
        "'123456789012345678901234567890123', false, 'Biên trên Max+1 (33 ký tự): Không hợp lệ'"
    })
    public void testPasswordBoundaryValidation(String password, boolean expected, String message) {
        boolean actual = authController.register("Nguyễn Văn A", "unique_" + System.nanoTime() + "@gmail.com", "0901234567", password);
        assertEquals(expected, actual, message);
    }

    @ParameterizedTest
    @DisplayName("TC_CTRL_AUTH_05: Phân vùng tương đương định dạng Email chuẩn RFC")
    @CsvSource({
        "'hoangtm@fpt.edu.vn', true, 'Email hợp lệ chuẩn tên miền giáo dục'",
        "'user.name+tag@gmail.com', true, 'Email hợp lệ có dấu cộng và chấm'",
        "'hoangfpt.edu.vn', false, 'Thiếu ký tự @: Không hợp lệ'",
        "'hoang@', false, 'Thiếu tên miền: Không hợp lệ'",
        "'@domain.com', false, 'Thiếu tên người dùng: Không hợp lệ'",
        "'', false, 'Chuỗi rỗng: Không hợp lệ'"
    })
    public void testEmailEquivalencePartitioning(String email, boolean expected, String message) {
        boolean actual = authController.register("Trần Thị B", email, "0901234567", "Password@123");
        assertEquals(expected, actual, message);
    }

    @ParameterizedTest
    @DisplayName("TC_CTRL_AUTH_06: Kiểm tra số điện thoại chuẩn 10 chữ số đầu số Việt Nam")
    @CsvSource({
        "'090123456', false, 'Thiếu số: 9 chữ số'",
        "'0901234567', true, 'Hợp lệ: 10 chữ số đầu 09'",
        "'0381234567', true, 'Hợp lệ: 10 chữ số đầu 03'",
        "'0771234567', true, 'Hợp lệ: 10 chữ số đầu 07'",
        "'0851234567', true, 'Hợp lệ: 10 chữ số đầu 08'",
        "'0561234567', true, 'Hợp lệ: 10 chữ số đầu 05'",
        "'09012345678', false, 'Thừa số: 11 chữ số'",
        "'0123456789', false, 'Đầu số 01 không hợp lệ'",
        "'090abc1234', false, 'Chứa ký tự chữ'",
        "'', false, 'Để trống'"
    })
    public void testPhoneValidation(String phone, boolean expected, String message) {
        boolean actual = authController.register("Lê Văn C", "unique_phone_" + System.nanoTime() + "@gmail.com", phone, "Password@123");
        assertEquals(expected, actual, message);
    }

    @Test
    @DisplayName("TC_CTRL_AUTH_08: Ném IllegalArgumentException khi họ tên để trống hoặc dưới 2 ký tự")
    public void testRegisterInvalidNameLengthThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            authController.register("A", "valid_test@gmail.com", "0901234567", "Password@123");
        }, "Phải ném IllegalArgumentException khi tên < 2 ký tự");

        assertThrows(IllegalArgumentException.class, () -> {
            authController.register("", "valid_test@gmail.com", "0901234567", "Password@123");
        }, "Phải ném IllegalArgumentException khi tên để trống");
    }
}
