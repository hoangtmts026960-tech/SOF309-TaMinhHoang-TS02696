package com.poly.sof309.controllers;

import com.poly.sof309.models.Motorcycle;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * =========================================================================
 * [Y3] CONTROLLER QUẢN LÝ DANH MỤC & LỌC SẢN PHẨM MÔ TÔ
 * =========================================================================
 */
public class ProductController {

    private final List<Motorcycle> products = new ArrayList<>();

    public ProductController() {
        initProductsData();
    }

    private void initProductsData() {
        products.add(new Motorcycle(1L, "BMW S1000RR 2024", "BMW", "Sportbike", 949000000.0, "0 km", "999 cc", 2024, "6 cấp", "bmw_s1000rr.jpg"));
        products.add(new Motorcycle(2L, "Kawasaki Ninja ZX-10R", "Kawasaki", "Sportbike", 729000000.0, "1,200 km", "998 cc", 2023, "6 cấp", "ninja_zx10r.jpg"));
        products.add(new Motorcycle(3L, "Ducati Streetfighter V4", "Ducati", "Nakedbike", 850000000.0, "500 km", "1103 cc", 2024, "6 cấp", "ducati_v4.jpg"));
        products.add(new Motorcycle(4L, "Yamaha YZF-R1M", "Yamaha", "Sportbike", 820000000.0, "0 km", "998 cc", 2024, "6 cấp", "yamaha_r1m.jpg"));
        products.add(new Motorcycle(5L, "Honda CBR1000RR-R SP", "Honda", "Sportbike", 1050000000.0, "0 km", "1000 cc", 2024, "6 cấp", "honda_cbr.jpg"));
        products.add(new Motorcycle(6L, "BMW R1250 GS Adventure", "BMW", "Adventure", 704000000.0, "3,500 km", "1254 cc", 2023, "6 cấp", "bmw_gs.jpg"));
        products.add(new Motorcycle(7L, "Aprilia RSV4 Factory", "Aprilia", "Sportbike", 885000000.0, "0 km", "1099 cc", 2024, "6 cấp", "aprilia_rsv4.jpg"));
        products.add(new Motorcycle(8L, "KTM 1290 Super Duke R", "KTM", "Nakedbike", 750000000.0, "2,000 km", "1301 cc", 2023, "6 cấp", "ktm_duke.jpg"));
    }

    public List<Motorcycle> getAllProducts() {
        return new ArrayList<>(products);
    }

    public Optional<Motorcycle> findById(Long id) {
        if (id == null) return Optional.empty();
        return products.stream().filter(p -> p.getId().equals(id)).findFirst();
    }

    public List<Motorcycle> filterByCategory(String category) {
        if (category == null || category.trim().isEmpty() || category.equalsIgnoreCase("all")) {
            return getAllProducts();
        }
        return products.stream()
                .filter(p -> p.getCategory().equalsIgnoreCase(category.trim()))
                .collect(Collectors.toList());
    }

    public List<Motorcycle> filterByBrand(String brand) {
        if (brand == null || brand.trim().isEmpty() || brand.equalsIgnoreCase("all")) {
            return getAllProducts();
        }
        return products.stream()
                .filter(p -> p.getBrand().equalsIgnoreCase(brand.trim()))
                .collect(Collectors.toList());
    }

    public List<Motorcycle> searchByName(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return getAllProducts();
        }
        String cleanKeyword = keyword.trim().toLowerCase();
        return products.stream()
                .filter(p -> p.getName().toLowerCase().contains(cleanKeyword))
                .collect(Collectors.toList());
    }

    public List<Motorcycle> sortProducts(String sortOrder) {
        List<Motorcycle> sorted = new ArrayList<>(products);
        if ("asc".equalsIgnoreCase(sortOrder)) {
            sorted.sort(Comparator.comparingDouble(Motorcycle::getPrice));
        } else if ("desc".equalsIgnoreCase(sortOrder)) {
            sorted.sort((a, b) -> Double.compare(b.getPrice(), a.getPrice()));
        }
        return sorted;
    }
}
