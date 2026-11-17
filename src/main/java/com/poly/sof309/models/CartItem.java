package com.poly.sof309.models;

public class CartItem {
    private Motorcycle motorcycle;
    private int quantity;

    public CartItem() {}

    public CartItem(Motorcycle motorcycle, int quantity) {
        this.motorcycle = motorcycle;
        this.quantity = quantity;
    }

    public Motorcycle getMotorcycle() {
        return motorcycle;
    }

    public void setMotorcycle(Motorcycle motorcycle) {
        this.motorcycle = motorcycle;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getSubtotal() {
        return motorcycle != null ? motorcycle.getPrice() * quantity : 0.0;
    }
}
