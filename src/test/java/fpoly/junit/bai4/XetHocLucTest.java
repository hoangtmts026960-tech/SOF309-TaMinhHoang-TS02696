package fpoly.junit.bai4;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit Test cho Class 2: Xét học lực (Yếu, TB, Khá, Giỏi)
 * Áp dụng phân vùng tương đương và phân tích giá trị biên
 */
public class XetHocLucTest {

    private XetHocLuc xetHocLuc;

    @Before
    public void setUp() {
        xetHocLuc = new XetHocLuc();
    }

    // ---------------------------------------------------------------------
    // 1. KIỂM THỬ XẾP LOẠI "YẾU" (diem < 5)
    // ---------------------------------------------------------------------

    @Test
    public void testHocLucYeuBoundaryMin() {
        assertEquals("Yếu", xetHocLuc.xetHocLuc(0));
    }

    @Test
    public void testHocLucYeuBoundaryMax() {
        assertEquals("Yếu", xetHocLuc.xetHocLuc(4.9));
    }

    @Test
    public void testHocLucYeuMiddle() {
        assertEquals("Yếu", xetHocLuc.xetHocLuc(3.0));
    }

    // ---------------------------------------------------------------------
    // 2. KIỂM THỬ XẾP LOẠI "TB" (5 <= diem < 6.5)
    // ---------------------------------------------------------------------

    @Test
    public void testHocLucTBBoundaryMin() {
        assertEquals("TB", xetHocLuc.xetHocLuc(5.0));
    }

    @Test
    public void testHocLucTBBoundaryMax() {
        assertEquals("TB", xetHocLuc.xetHocLuc(6.4));
    }

    @Test
    public void testHocLucTBMiddle() {
        assertEquals("TB", xetHocLuc.xetHocLuc(5.8));
    }

    // ---------------------------------------------------------------------
    // 3. KIỂM THỬ XẾP LOẠI "KHÁ" (6.5 <= diem < 8)
    // ---------------------------------------------------------------------

    @Test
    public void testHocLucKhaBoundaryMin() {
        assertEquals("Khá", xetHocLuc.xetHocLuc(6.5));
    }

    @Test
    public void testHocLucKhaBoundaryMax() {
        assertEquals("Khá", xetHocLuc.xetHocLuc(7.9));
    }

    @Test
    public void testHocLucKhaMiddle() {
        assertEquals("Khá", xetHocLuc.xetHocLuc(7.2));
    }

    // ---------------------------------------------------------------------
    // 4. KIỂM THỬ XẾP LOẠI "GIỎI" (8 <= diem <= 10)
    // ---------------------------------------------------------------------

    @Test
    public void testHocLucGioiBoundaryMin() {
        assertEquals("Giỏi", xetHocLuc.xetHocLuc(8.0));
    }

    @Test
    public void testHocLucGioiBoundaryMax() {
        assertEquals("Giỏi", xetHocLuc.xetHocLuc(10.0));
    }

    @Test
    public void testHocLucGioiMiddle() {
        assertEquals("Giỏi", xetHocLuc.xetHocLuc(9.0));
    }

    // ---------------------------------------------------------------------
    // 5. KIỂM THỬ NGOẠI LỆ ĐIỂM NGOÀI PHẠM VI [0, 10]
    // ---------------------------------------------------------------------

    @Test(expected = IllegalArgumentException.class)
    public void testDiemAmThrowsException() {
        xetHocLuc.xetHocLuc(-1.0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDiemVuotQua10ThrowsException() {
        xetHocLuc.xetHocLuc(10.5);
    }

    // ---------------------------------------------------------------------
    // 6. KIỂM THỬ KẾT HỢP VỚI CLASS 1 (DiemHocSinh)
    // ---------------------------------------------------------------------

    @Test
    public void testXetHocLucWithDiemHocSinhObject() {
        DiemHocSinh hs = new DiemHocSinh(9);
        assertEquals("Giỏi", xetHocLuc.xetHocLuc(hs));
    }
}
