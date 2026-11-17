package fpoly.junit.bai4;

/**
 * =========================================================================
 * BÀI 4 - CLASS 1: NHẬP VÀO ĐIỂM NGUYÊN (0 <= diem <= 10)
 * =========================================================================
 */
public class DiemHocSinh {

    private int diem;

    public DiemHocSinh() {}

    public DiemHocSinh(int diem) {
        setDiem(diem);
    }

    public int getDiem() {
        return diem;
    }

    public void setDiem(int diem) {
        if (!isValidDiem(diem)) {
            throw new IllegalArgumentException("Điểm không hợp lệ! Điểm nguyên phải nằm trong khoảng: 0 <= diem <= 10 (Nhận: " + diem + ")");
        }
        this.diem = diem;
    }

    public static boolean isValidDiem(int diem) {
        return diem >= 0 && diem <= 10;
    }
}
