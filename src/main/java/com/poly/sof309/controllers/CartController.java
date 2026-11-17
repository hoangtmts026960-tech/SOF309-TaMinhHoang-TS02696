package com.poly.sof309.controllers;

import com.poly.sof309.exceptions.InvalidCouponException;
import com.poly.sof309.exceptions.InvalidQuantityException;
import com.poly.sof309.exceptions.ProductNotFoundException;
import com.poly.sof309.models.CartItem;
import com.poly.sof309.models.Motorcycle;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * =========================================================================
 * [Y3] CONTROLLER QUẢN LÝ GIỎ HÀNG, TÍNH TOÁN TIỀN & MÃ GIẢM GIÁ
 * =========================================================================
 */
public class CartController {

    private final List<CartItem> cart = new ArrayList<>();
    private final ProductController productController;

    public CartController(ProductController productController) {
        this.productController = productController;
    }

    public CartController() {
        this(new ProductController());
    }

    /**
     * Thêm sản phẩm vào giỏ hàng với validation số lượng và mã sản phẩm
     */
    public void addItem(Long productId, int quantity) {
        // 1. Kiểm tra số lượng hợp lệ (BVA: quantity > 0)
        if (quantity <= 0) {
            throw new InvalidQuantityException("Số lượng sản phẩm thêm vào giỏ phải lớn hơn 0 (Nhận: " + quantity + ")");
        }

        // 2. Tìm kiếm xe trong danh mục
        Optional<Motorcycle> motoOpt = productController.findById(productId);
        if (motoOpt.isEmpty()) {
            throw new ProductNotFoundException("Không tìm thấy sản phẩm có mã ID: " + productId);
        }

        Motorcycle moto = motoOpt.get();

        // 3. Nếu đã có trong giỏ thì tăng số lượng, chưa có thì thêm mới
        Optional<CartItem> existingItem = cart.stream()
                .filter(item -> item.getMotorcycle().getId().equals(productId))
                .findFirst();

        if (existingItem.isPresent()) {
            existingItem.get().setQuantity(existingItem.get().getQuantity() + quantity);
        } else {
            cart.add(new CartItem(moto, quantity));
        }
    }

    /**
     * Cập nhật số lượng bằng độ lệch delta (+1 hoặc -1)
     * Khi số lượng giảm về <= 0, tự động loại bỏ xe khỏi giỏ
     */
    public void updateQuantity(Long productId, int delta) {
        Optional<CartItem> existingItem = cart.stream()
                .filter(item -> item.getMotorcycle().getId().equals(productId))
                .findFirst();

        if (existingItem.isPresent()) {
            CartItem item = existingItem.get();
            int newQuantity = item.getQuantity() + delta;
            if (newQuantity <= 0) {
                cart.remove(item);
            } else {
                item.setQuantity(newQuantity);
            }
        }
    }

    /**
     * Tính tổng tiền tạm tính (Subtotal) của toàn bộ xe trong giỏ
     */
    public double calculateSubtotal() {
        return cart.stream().mapToDouble(CartItem::getSubtotal).sum();
    }

    /**
     * Áp dụng mã giảm giá. Mã hợp lệ duy nhất là "MOTO10" (giảm 10%)
     */
    public double applyCoupon(String couponCode, double subtotal) {
        if (couponCode == null || !couponCode.trim().equalsIgnoreCase("MOTO10")) {
            throw new InvalidCouponException("Mã giảm giá không hợp lệ: " + couponCode);
        }
        // Giảm giá 10%
        return subtotal * 0.10;
    }

    /**
     * Tính tổng thanh toán cuối cùng sau khi trừ phần trăm giảm giá
     */
    public double calculateTotal(double subtotal, int discountPercent) {
        if (discountPercent < 0 || discountPercent > 100) {
            throw new IllegalArgumentException("Phần trăm giảm giá phải từ 0% đến 100%");
        }
        double discount = subtotal * (discountPercent / 100.0);
        return subtotal - discount;
    }

    public void removeItem(Long productId) {
        cart.removeIf(item -> item.getMotorcycle().getId().equals(productId));
    }

    public void clearCart() {
        cart.clear();
    }

    public List<CartItem> getCartItems() {
        return new ArrayList<>(cart);
    }

    public int getItemCount() {
        return cart.size();
    }

    public int getTotalQuantity() {
        return cart.stream().mapToInt(CartItem::getQuantity).sum();
    }
}
