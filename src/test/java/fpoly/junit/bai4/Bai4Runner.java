package fpoly.junit.bai4;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class Bai4Runner {
    public static void main(String[] args) {
        System.out.println("========== THỰC THI KIỂM THỬ BÀI 4 ==========");
        Result result = JUnitCore.runClasses(DiemHocSinhTest.class, XetHocLucTest.class);

        for (Failure failure : result.getFailures()) {
            System.out.println("Lỗi: " + failure.toString());
        }

        System.out.println("Tổng số test cases đã chạy: " + result.getRunCount());
        System.out.println("Số test case thất bại: " + result.getFailureCount());
        System.out.println("Số test case bị bỏ qua: " + result.getIgnoreCount());
        System.out.println("Kết quả tổng quát (wasSuccessful): " + result.wasSuccessful());
        System.out.println("================================================");
    }
}
