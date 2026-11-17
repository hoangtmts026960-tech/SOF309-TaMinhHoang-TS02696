package com.poly.sof309.controllers;

import com.poly.sof309.models.Motorcycle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * =========================================================================
 * [Y3] BỘ UNIT TEST JUNIT 5 CHO PRODUCTCONTROLLER (DANH MỤC & LỌC XE MÔ TÔ)
 * Kế thừa kỹ thuật: Parameterized Test, Equivalence Partitioning
 * =========================================================================
 */
public class ProductControllerTest {

    private ProductController productController;

    @BeforeEach
    public void setUp() {
        productController = new ProductController();
    }

    @Test
    @DisplayName("TC_CTRL_PROD_01: Lọc danh sách xe theo phân khúc Sportbike")
    public void testFilterByCategorySportbike() {
        List<Motorcycle> sportbikes = productController.filterByCategory("Sportbike");
        assertFalse(sportbikes.isEmpty(), "Danh sách Sportbike không được rỗng");
        for (Motorcycle m : sportbikes) {
            assertEquals("Sportbike", m.getCategory(), "100% xe trả về phải là dòng Sportbike");
        }
    }

    @Test
    @DisplayName("Lọc danh sách xe theo hãng sản xuất BMW")
    public void testFilterByBrandBMW() {
        List<Motorcycle> bmwBikes = productController.filterByBrand("BMW");
        assertFalse(bmwBikes.isEmpty(), "Danh sách xe BMW không được rỗng");
        for (Motorcycle m : bmwBikes) {
            assertEquals("BMW", m.getBrand(), "Xe trả về phải thuộc hãng BMW");
        }
    }

    @Test
    @DisplayName("TC_CTRL_PROD_02: Tìm kiếm xe không phân biệt hoa thường theo từ khóa con")
    public void testSearchByNameCaseInsensitive() {
        List<Motorcycle> results = productController.searchByName("bmw s1000rr");
        assertEquals(1, results.size(), "Phải tìm thấy đúng 1 xe BMW S1000RR 2024");
        assertTrue(results.get(0).getName().contains("BMW S1000RR"));
    }

    @Test
    @DisplayName("Tìm kiếm với từ khóa không tồn tại trả về danh sách rỗng")
    public void testSearchByNameNotFound() {
        List<Motorcycle> results = productController.searchByName("Xe May Dien Khong Co");
        assertTrue(results.isEmpty(), "Không được tìm thấy xe nào với từ khóa không tồn tại");
    }

    @ParameterizedTest
    @DisplayName("TC_CTRL_PROD_03: Sắp xếp giá tăng dần (asc) và giảm dần (desc)")
    @CsvSource({
        "'asc'",
        "'desc'"
    })
    public void testSortProducts(String sortOrder) {
        List<Motorcycle> sorted = productController.sortProducts(sortOrder);
        assertFalse(sorted.isEmpty());

        for (int i = 0; i < sorted.size() - 1; i++) {
            if ("asc".equals(sortOrder)) {
                assertTrue(sorted.get(i).getPrice() <= sorted.get(i + 1).getPrice(),
                        "Thứ tự tăng dần: Giá phần tử trước phải <= phần tử sau");
            } else {
                assertTrue(sorted.get(i).getPrice() >= sorted.get(i + 1).getPrice(),
                        "Thứ tự giảm dần: Giá phần tử trước phải >= phần tử sau");
            }
        }
    }

    @Test
    @DisplayName("Tìm kiếm xe theo ID thành công")
    public void testFindByIdSuccess() {
        var motoOpt = productController.findById(1L);
        assertTrue(motoOpt.isPresent());
        assertEquals("BMW S1000RR 2024", motoOpt.get().getName());
    }

    @Test
    @DisplayName("Tìm kiếm xe với ID không tồn tại trả về Optional rỗng")
    public void testFindByIdNotFound() {
        var motoOpt = productController.findById(9999L);
        assertTrue(motoOpt.isEmpty());
    }
}
