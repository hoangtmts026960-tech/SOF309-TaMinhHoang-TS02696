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
 * =========================================================================
 */
public class AuthControllerTest {

    private AuthController authController;

    @BeforeEach
    public void setUp() {
        authController = new AuthController();
    }

    // ---------------------------------------------------------------------
    // 1. KIỂM THỬ XÁC THỰC ĐĂNG NHẬP (TC_CTRL_AUTH_01 -> 03)
    // ---------------------------------------------------------------------

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

    // ---------------------------------------------------------------------
    // 2. PARAMETERIZED TESTS KIỂM TRA GIÁ TRỊ BIÊN FORM ĐĂNG KÝ (BƯỚC 2.2)
    // ---------------------------------------------------------------------

    @ParameterizedTest
    @DisplayName("Kiểm tra validation đăng ký tài khoản với nhiều bộ dữ liệu biên")
    @CsvSource({
        "'', 'password123', false, 'Username không được để trống'",
        "'user', 'password123', false, 'Username phải từ 5 đến 20 ký tự'",
        "'validUser', '123', false, 'Mật khẩu phải từ 8 ký tự'",
        "'validUser', 'Pass@1234', true, 'Đăng ký thành công'"
    })
    public void testRegisterValidation(String username, String password, boolean expectedSuccess, String message) {
        boolean actualResult = authController.validateRegister(username, password);
        assertEquals(expectedSuccess, actualResult, message);
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
    @DisplayName("TC_CTRL_AUTH_06: Kiểm tra số điện thoại 10 số đầu số Việt Nam (03, 05, 07, 08, 09)")
    @CsvSource({
        "'090123456', false, '9 số (thiếu): Không hợp lệ'",
        "'0901234567', true, '10 số đầu 09: Hợp lệ'",
        "'0387654321', true, '10 số đầu 03: Hợp lệ'",
        "'0771122334', true, '10 số đầu 07: Hợp lệ'",
        "'09012345678', false, '11 số (thừa): Không hợp lệ'",
        "'0123456789', false, 'Đầu số lạ 01x: Không hợp lệ'",
        "'090abcdef1', false, 'Chứa ký tự chữ: Không hợp lệ'"
    })
    public void testPhoneNumberValidation(String phone, boolean expected, String message) {
        boolean actual = authController.register("Lê Văn C", "user_" + System.nanoTime() + "@fpt.edu.vn", phone, "Password@123");
        assertEquals(expected, actual, message);
    }

    // ---------------------------------------------------------------------
    // 3. EXCEPTION TESTING (BƯỚC 2.3)
    // ---------------------------------------------------------------------

    @Test
    @DisplayName("Ném ngoại lệ DuplicateUserException khi tài khoản đã tồn tại")
    public void testRegisterDuplicateUserThrowsException() {
        assertThrows(DuplicateUserException.class, () -> {
            authController.registerExistingAccount("admin");
        });
    }

    @Test
    @DisplayName("TC_CTRL_AUTH_07 (Cách 2): Đăng ký lại email đã có trong hệ thống phải ném DuplicateUserException")
    public void testRegisterDuplicateEmailThrowsException() {
        assertThrows(DuplicateUserException.class, () -> {
            authController.register("Nguyễn Văn Admin", "admin@gmail.com", "0988888888", "MatKhau@123");
        }, "Phải ném ngoại lệ khi trùng email đăng ký");
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

    @Test
    @DisplayName("TC_CTRL_AUTH_08 (Phụ): Ném IllegalArgumentException khi họ tên chứa chữ số")
    public void testRegisterNameWithDigitsThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            authController.register("Hoàng123", "test_digit@gmail.com", "0901234567", "Password@123");
        }, "Họ tên chứa chữ số phải ném IllegalArgumentException");
    }

    // ---------------------------------------------------------------------
    // 4. TIMEOUT TESTING (BƯỚC 2.4)
    // ---------------------------------------------------------------------

    @Test
    @Timeout(value = 2, unit = TimeUnit.SECONDS)
    @DisplayName("TC_CTRL_AUTH_09: Timeout testing - Xác thực người dùng phải hoàn tất dưới 2 giây")
    public void testAuthenticatePerformanceTimeout() {
        boolean result = authController.authenticate("admin@gmail.com", "123456");
        assertTrue(result);
    }
}
