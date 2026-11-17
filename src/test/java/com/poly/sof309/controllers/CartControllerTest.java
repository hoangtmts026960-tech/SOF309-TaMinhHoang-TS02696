package com.poly.sof309.controllers;

import com.poly.sof309.exceptions.InvalidCouponException;
import com.poly.sof309.exceptions.InvalidQuantityException;
import com.poly.sof309.exceptions.ProductNotFoundException;
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
 * [Y3] BỘ UNIT TEST JUNIT 5 CHO CARTCONTROLLER (GIỎ HÀNG & MÃ GIẢM GIÁ)
 * Kế thừa kỹ thuật: Parameterized Test, Exception Testing, Boundary Value Analysis
 * =========================================================================
 */
public class CartControllerTest {

    private CartController cartController;
    private ProductController productController;

    @BeforeEach
    public void setUp() {
        productController = new ProductController();
        cartController = new CartController(productController);
    }

    // ---------------------------------------------------------------------
    // 1. KIỂM THỬ THÊM VÀO GIỎ HÀNG (TC_CTRL_CART_01 -> 03)
    // ---------------------------------------------------------------------

    @Test
    @DisplayName("TC_CTRL_CART_01: Thêm sản phẩm hợp lệ vào giỏ hàng với số lượng > 0")
    public void testAddItemSuccess() {
        cartController.addItem(1L, 1);
        assertEquals(1, cartController.getItemCount(), "Giỏ hàng phải có 1 loại sản phẩm");
        assertEquals(1, cartController.getTotalQuantity(), "Tổng số lượng trong giỏ phải là 1");
        assertEquals(949000000.0, cartController.calculateSubtotal(), "Tiền tạm tính phải khớp giá BMW S1000RR");
    }

    @Test
    @DisplayName("TC_CTRL_CART_02: Ném ngoại lệ InvalidQuantityException khi thêm số lượng biên <= 0")
    public void testAddItemInvalidQuantityThrowsException() {
        // Biên 0
        assertThrows(InvalidQuantityException.class, () -> {
            cartController.addItem(1L, 0);
        }, "Thêm số lượng = 0 phải ném InvalidQuantityException");

        // Biên âm -1
        assertThrows(InvalidQuantityException.class, () -> {
            cartController.addItem(1L, -1);
        }, "Thêm số lượng âm phải ném InvalidQuantityException");
    }

    @Test
    @DisplayName("TC_CTRL_CART_03: Ném ngoại lệ ProductNotFoundException khi ID sản phẩm không tồn tại")
    public void testAddItemProductNotFoundThrowsException() {
        assertThrows(ProductNotFoundException.class, () -> {
            cartController.addItem(99999L, 1);
        }, "ID xe 99999 không tồn tại phải ném ProductNotFoundException");
    }

    // ---------------------------------------------------------------------
    // 2. KIỂM THỬ TÍNH TOÁN TIỀN TẠM TÍNH (SUBTOTAL) VÀ TỔNG TIỀN (TC_CTRL_CART_04)
    // ---------------------------------------------------------------------

    @Test
    @DisplayName("TC_CTRL_CART_04: Tính tổng tiền tạm tính (Subtotal) chính xác cho nhiều sản phẩm")
    public void testCalculateSubtotalMultipleItems() {
        // Item 1: BMW S1000RR 2024 (949.000.000 x 1)
        cartController.addItem(1L, 1);
        // Item 2: Kawasaki Ninja ZX-10R (729.000.000 x 2)
        cartController.addItem(2L, 2);

        double expectedSubtotal = (949000000.0 * 1) + (729000000.0 * 2); // 2.407.000.000 VNĐ
        assertEquals(expectedSubtotal, cartController.calculateSubtotal(), 0.001, "Tổng tiền tạm tính phải bằng 2.407.000.000 VNĐ");
    }

    // ---------------------------------------------------------------------
    // 3. KIỂM THỬ MÃ GIẢM GIÁ VÀ CHIẾT KHẤU (TC_CTRL_CART_05 & 06)
    // ---------------------------------------------------------------------

    @Test
    @DisplayName("TC_CTRL_CART_05: Áp dụng mã MOTO10 tính chiết khấu 10% chính xác")
    public void testApplyCouponMoto10Success() {
        double subtotal = 1000000000.0; // 1 tỷ VNĐ
        double discount = cartController.applyCoupon("MOTO10", subtotal);
        assertEquals(100000000.0, discount, 0.001, "Giảm 10% của 1 tỷ phải là 100 triệu VNĐ");

        double total = cartController.calculateTotal(subtotal, 10);
        assertEquals(900000000.0, total, 0.001, "Tổng thanh toán sau giảm phải là 900 triệu VNĐ");
    }

    @ParameterizedTest
    @DisplayName("TC_CTRL_CART_05 (Mở rộng): Áp dụng mã giảm giá không phân biệt hoa thường và khoảng trắng")
    @CsvSource({
        "'MOTO10', 1000000000.0, 100000000.0",
        "'moto10', 1000000000.0, 100000000.0",
        "'  MOTO10  ', 500000000.0, 50000000.0"
    })
    public void testApplyCouponCaseInsensitive(String coupon, double subtotal, double expectedDiscount) {
        double discount = cartController.applyCoupon(coupon, subtotal);
        assertEquals(expectedDiscount, discount, 0.001);
    }

    @ParameterizedTest
    @DisplayName("TC_CTRL_CART_06: Ném ngoại lệ InvalidCouponException khi nhập mã giảm giá không hợp lệ")
    @CsvSource({
        "'GIAM50PHANTRAM'",
        "'MOTO20'",
        "'123456'",
        "''"
    })
    public void testApplyInvalidCouponThrowsException(String invalidCode) {
        assertThrows(InvalidCouponException.class, () -> {
            cartController.applyCoupon(invalidCode, 1000000000.0);
        }, "Mã không hợp lệ phải ném InvalidCouponException");
    }

    // ---------------------------------------------------------------------
    // 4. KIỂM THỬ GIÁ TRỊ BIÊN CẬP NHẬT SỐ LƯỢNG (TC_CTRL_CART_07)
    // ---------------------------------------------------------------------

    @Test
    @DisplayName("TC_CTRL_CART_07: Giảm số lượng từ 1 về 0 (delta = -1) tự động xóa sản phẩm khỏi giỏ")
    public void testUpdateQuantityBoundaryRemove() {
        cartController.addItem(1L, 1);
        assertEquals(1, cartController.getItemCount());

        // Giảm 1 đơn vị -> số lượng = 0 -> tự động xóa
        cartController.updateQuantity(1L, -1);
        assertEquals(0, cartController.getItemCount(), "Sản phẩm phải tự động bị xóa khỏi giỏ khi số lượng <= 0");
        assertEquals(0.0, cartController.calculateSubtotal(), "Tiền tạm tính phải trở về 0 VNĐ");
    }

    @Test
    @DisplayName("Tăng số lượng sản phẩm trong giỏ hàng thành công")
    public void testUpdateQuantityIncrease() {
        cartController.addItem(1L, 1);
        cartController.updateQuantity(1L, 2);
        assertEquals(3, cartController.getTotalQuantity(), "Số lượng phải tăng lên 3");
    }

    @Test
    @Timeout(value = 2, unit = TimeUnit.SECONDS)
    @DisplayName("Timeout testing - Thao tác tính toán giỏ hàng phải hoàn tất dưới 2 giây")
    public void testCartPerformanceTimeout() {
        for (long i = 1; i <= 8; i++) {
            cartController.addItem(i, 1);
        }
        double subtotal = cartController.calculateSubtotal();
        assertTrue(subtotal > 0);
        double total = cartController.calculateTotal(subtotal, 10);
        assertTrue(total < subtotal);
    }
}
