package fpoly.junit.bai4;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit Test cho Class 1: Nhập điểm nguyên (0 <= diem <= 10)
 */
public class DiemHocSinhTest {

    @Test
    public void testValidScoreBoundaryMin() {
        DiemHocSinh hs = new DiemHocSinh(0);
        assertEquals(0, hs.getDiem());
    }

    @Test
    public void testValidScoreBoundaryMax() {
        DiemHocSinh hs = new DiemHocSinh(10);
        assertEquals(10, hs.getDiem());
    }

    @Test
    public void testValidScoreMiddle() {
        DiemHocSinh hs = new DiemHocSinh(5);
        assertEquals(5, hs.getDiem());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidScoreBelowZero() {
        new DiemHocSinh(-1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidScoreAboveTen() {
        new DiemHocSinh(11);
    }
}
