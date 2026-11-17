package com.poly.sof309.models;

public class Motorcycle {
    private Long id;
    private String name;
    private String brand;
    private String category;
    private double price;
    private String odo;
    private String cc;
    private int year;
    private String gearbox;
    private String image;

    public Motorcycle() {}

    public Motorcycle(Long id, String name, String brand, String category, double price, String odo, String cc, int year, String gearbox, String image) {
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.category = category;
        this.price = price;
        this.odo = odo;
        this.cc = cc;
        this.year = year;
        this.gearbox = gearbox;
        this.image = image;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getOdo() {
        return odo;
    }

    public void setOdo(String odo) {
        this.odo = odo;
    }

    public String getCc() {
        return cc;
    }

    public void setCc(String cc) {
        this.cc = cc;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getGearbox() {
        return gearbox;
    }

    public void setGearbox(String gearbox) {
        this.gearbox = gearbox;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
