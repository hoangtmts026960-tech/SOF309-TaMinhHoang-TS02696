package fpoly.junit.bai4;

/**
 * =========================================================================
 * BÀI 4 - CLASS 2: XÉT HỌC LỰC DỰA TRÊN ĐIỂM SỐ
 * - Yếu: diem < 5
 * - TB: 5 <= diem < 6.5
 * - Khá: 6.5 <= diem < 8
 * - Giỏi: 8 <= diem <= 10
 * =========================================================================
 */
public class XetHocLuc {

    public String xetHocLuc(double diem) {
        if (diem < 0 || diem > 10) {
            throw new IllegalArgumentException("Điểm không hợp lệ để xét học lực (0 <= diem <= 10). Nhận: " + diem);
        }

        if (diem < 5.0) {
            return "Yếu";
        } else if (diem < 6.5) {
            return "TB";
        } else if (diem < 8.0) {
            return "Khá";
        } else {
            return "Giỏi";
        }
    }

    public String xetHocLuc(DiemHocSinh hs) {
        if (hs == null) {
            throw new IllegalArgumentException("Đối tượng điểm học sinh không được null");
        }
        return xetHocLuc(hs.getDiem());
    }
}
