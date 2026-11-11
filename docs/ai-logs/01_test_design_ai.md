# Nhật ký AI: Ứng dụng AI RAG Thiết kế Test Plan và Bộ Test Case (Y2 & Y6)

## 1. Ngữ cảnh & Mục tiêu
* **Dự án:** MOTO STORE - Showroom Siêu Xe Mô Tô Phân Khối Lớn Cao Cấp
* **Sinh viên:** Tạ Minh Hoàng (MSSV: TS02696 - Lớp: SD21307)
* **Mục tiêu:**
  * Đóng vai trò QA Lead, nạp toàn bộ tài liệu đặc tả nghiệp vụ `docs/SPECIFICATIONS.md` vào mô hình AI.
  * Sử dụng 2 kỹ thuật thiết kế kiểm thử hộp đen nâng cao: **Phân vùng tương đương (Equivalence Partitioning)** và **Phân tích giá trị biên (Boundary Value Analysis)**.
  * Thiết kế tài liệu Kế hoạch kiểm thử tổng thể (`docs/TEST_PLAN.md`) và Bộ kịch bản kiểm thử chi tiết (`docs/TEST_CASES.md`) bao phủ cả 2 tầng:
    1. **Tầng Backend Controller:** Phục vụ viết Unit Test với JUnit 5 (`@ParameterizedTest`, `assertThrows()`, `@Timeout`).
    2. **Tầng Frontend UI:** Phục vụ kiểm thử tự động với Selenium WebDriver 4 theo mô hình Page Object Model (POM) và TestNG Framework.

---

## 2. Câu lệnh đã gửi cho AI (Prompt)
> "Bạn là QA Lead, dựa trên file đặc tả SPECIFICATIONS.md Hãy áp dụng kỹ thuật Phân vùng tương đương và Phân tích giá trị biên để thiết kế Test Plan và Bộ Test Case chi tiết cho Controller và UI."

---

## 3. Kết quả do AI sinh ra (Raw Output)
AI đã phân tích kiến trúc hệ thống và sinh ra:
1. **Bản Kế hoạch Kiểm thử Tổng thể (Master Test Plan):**
   * Xác định rõ mục tiêu kiểm thử: Đảm bảo độ phủ logic cho Controller và tính ổn định tương tác DOM cho UI.
   * Xác định phạm vi In-Scope (Auth, Catalog, Cart, Pricing, VietQR Gateway, Wishlist, Mini Game) và Out-of-Scope (Load testing > 10.000 CCU, Security pentest).
   * Thiết lập chiến lược kiểm thử đa tầng: JUnit 5 Parameterized, Selenium 4 POM, TestNG điều phối Suite, GitHub Actions CI/CD Headless.
   * Tiêu chuẩn nghiệm thu: 100% Test Case Pass, GitHub Actions báo xanh, đủ 6 file nhật ký minh chứng AI.

2. **Bộ Kịch bản Kiểm thử Toàn diện (37 Test Cases chi tiết):**
   * **16 Test Cases cho Backend Controller (JUnit 5):**
     * `TC_CTRL_AUTH_01` $\rightarrow$ `TC_CTRL_AUTH_09`: Xác thực đăng nhập, kiểm tra độ dài biên mật khẩu $[8, 32]$ với `@ParameterizedTest` + `@CsvSource`, regex email RFC, đầu số điện thoại Việt Nam (10 số), bắt ngoại lệ `DuplicateUserException` và `IllegalArgumentException`, kiểm tra `@Timeout(2s)`.
     * `TC_CTRL_CART_01` $\rightarrow$ `TC_CTRL_CART_07`: Thêm giỏ hàng, ném ngoại lệ `InvalidQuantityException` khi $quantity \le 0$, ném `ProductNotFoundException`, tính Subtotal, áp dụng chiết khấu voucher `MOTO10` (10%), ném `InvalidCouponException`, cập nhật số lượng biên giảm về 0 tự động xóa khỏi giỏ.
     * `TC_CTRL_PROD_01` $\rightarrow$ `TC_CTRL_PROD_03`: Lọc theo phân khúc Sportbike, tìm kiếm gần đúng không phân biệt hoa thường, sắp xếp giá tăng dần / giảm dần.
   * **21 Test Cases cho Frontend UI (Selenium WebDriver POM):**
     * Phân nhóm rõ ràng theo `smoke` (kiểm thử nhanh luồng chính) và `regression` (kiểm thử chi tiết lỗi và biên).
     * Bao phủ toàn bộ các trang và modal: `LoginPage`, `RegisterPage`, `CatalogPage`, `WishlistPage`, `CartPage`, `CheckoutModal` (VietQR thanh toán tự động với đồng hồ đếm 15 phút), `GamePage` (Hái Táo Tránh Bom) và tiện ích Dark/Light Mode.

---

## 4. Chỉnh sửa của Sinh viên & Lý do sửa
* **Điểm bổ sung và tối ưu:**
  1. **Khớp chính xác Selector DOM:** Đối chiếu kỹ lưỡng lại từng ID trong file `src/main/resources/static/index.html` (như `#login-user`, `#login-pass`, `#coupon-input`, `#vietqr-image`, `#qr-timer`, `#game-score`) để đảm bảo khi viết mã Selenium WebDriver POM ở Tuần 3 và Tuần 4 không bị lỗi `NoSuchElementException`.
  2. **Chuẩn hóa giá trị biên:** Bổ sung trường hợp biên số lượng giỏ hàng giảm từ 1 về 0 sẽ tự động kích hoạt hàm xóa sản phẩm khỏi giỏ hàng (`removeFromCart`) thay vì để số lượng âm.
  3. **Tách biệt rõ ràng 2 nhóm Test:** Phân định rõ cột mã Test Case (`TC_CTRL_` dành cho Controller Unit Test và `TC_UI_` dành cho Selenium UI Test) để dễ dàng theo dõi và nghiệm thu tương ứng cho Tuần 2 (Y3) và Tuần 3-4 (Y4).
* **Kết luận nghiệm thu Tuần 1 (Y1 & Y2):**
  * File đặc tả nghiệp vụ `docs/SPECIFICATIONS.md` đã hoàn thành chi tiết.
  * File `docs/TEST_PLAN.md` đã thiết lập chiến lược kiểm thử toàn diện theo vai trò QA Lead.
  * File `docs/TEST_CASES.md` đã có đầy đủ 37 ca kiểm thử chất lượng cao, sẵn sàng phục vụ sinh mã Unit Test và Automation Test.
