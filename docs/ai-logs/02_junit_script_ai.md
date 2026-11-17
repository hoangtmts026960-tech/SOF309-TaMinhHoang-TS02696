# Nhật ký AI: Viết Unit Test Controller với JUnit 5 Nâng Cao (Y3 & Y6)

## 1. Ngữ cảnh & Mục tiêu
* **Dự án:** MOTO STORE - Showroom Siêu Xe Mô Tô Phân Khối Lớn Cao Cấp
* **Sinh viên thực hiện:** Tạ Minh Hoàng (MSSV: TS02696 - Lớp: SD21307)
* **Mục tiêu Tuần 2 (Y3):**
  * Xây dựng tầng Controller logic và các Custom Exception để đáp ứng đặc tả trong `docs/SPECIFICATIONS.md`.
  * Áp dụng kỹ thuật kế thừa từ các bài Lab cũ:
    * **Parameterized Test (Lab 2):** Sử dụng `@ParameterizedTest` kết hợp `@CsvSource` để quét các giá trị biên của form Đăng ký (Họ tên, Email chuẩn RFC, Số điện thoại 10 số đầu số VN, Mật khẩu độ dài biên $[8, 32]$).
    * **Exception Testing (Lab 3):** Sử dụng `assertThrows()` để kiểm tra ngoại lệ `DuplicateUserException`, `InvalidQuantityException`, `ProductNotFoundException`, `InvalidCouponException`.
    * **Timeout Testing:** Gắn `@Timeout(value = 2, unit = TimeUnit.SECONDS)` kiểm tra hiệu năng tính toán và xác thực không bị treo vô tận.
  * Đạt tiêu chí nghiệm thu: Lệnh `mvn test -Dtest=*ControllerTest` đạt **BUILD SUCCESS (100% Pass, 0 Failure, 0 Error)**.

---

## 2. Câu lệnh đã gửi cho AI (Prompt)
> "Tôi đang làm Assignment môn SOF309 cho dự án MOTO STORE. Dựa vào tài liệu SPECIFICATIONS.md và TEST_CASES.md đã lập ở Tuần 1, hãy viết mã nguồn tầng Controller (AuthController, CartController, ProductController) và xây dựng bộ Unit Test JUnit 5 hoàn chỉnh trong thư mục src/test/java/com/poly/sof309/controllers/.
> Yêu cầu bắt buộc:
> 1. Áp dụng @ParameterizedTest + @CsvSource cho form validation và phân tích giá trị biên.
> 2. Áp dụng assertThrows() bắt ngoại lệ trùng lặp tài khoản (DuplicateUserException), số lượng giỏ hàng không hợp lệ (InvalidQuantityException), và mã giảm giá sai (InvalidCouponException).
> 3. Áp dụng @Timeout(2s) kiểm tra hiệu năng.
> 4. Đảm bảo chạy lệnh 'mvn test -Dtest=*ControllerTest' đạt 100% Pass."

---

## 3. Kết quả do AI sinh ra (Raw Output)
AI đã thiết kế và sinh toàn bộ mã nguồn:
1. **Các lớp Exception nghiệp vụ (`src/main/java/com/poly/sof309/exceptions/`):**
   * `DuplicateUserException.java`: Bắt lỗi trùng lặp email khi đăng ký tài khoản.
   * `InvalidQuantityException.java`: Bắt lỗi số lượng $\le 0$ khi thêm vào giỏ.
   * `ProductNotFoundException.java`: Bắt lỗi khi tra cứu mã xe không tồn tại.
   * `InvalidCouponException.java`: Bắt lỗi khi áp dụng mã giảm giá không đúng với `MOTO10`.
2. **Các thực thể Model (`src/main/java/com/poly/sof309/models/`):**
   * `User.java`: Đại diện cho người dùng đăng nhập/đăng ký.
   * `Motorcycle.java`: Đại diện cho sản phẩm xe phân khối lớn (ID, tên, hãng, dòng xe, giá niêm yết, thông số kỹ thuật).
   * `CartItem.java`: Đại diện cho phần tử trong giỏ hàng và thành tiền.
3. **Các lớp Controller xử lý nghiệp vụ (`src/main/java/com/poly/sof309/controllers/`):**
   * `AuthController.java`: Xử lý xác thực `authenticate()`, kiểm tra trùng lặp `isEmailExists()`, và validation đăng ký `register()`.
   * `CartController.java`: Quản lý giỏ hàng `addItem()`, `updateQuantity()` (giảm về 0 tự động xóa), `calculateSubtotal()`, `applyCoupon()`, `calculateTotal()`.
   * `ProductController.java`: Tìm kiếm theo từ khóa `searchByName()`, lọc theo dòng xe `filterByCategory()`, lọc hãng `filterByBrand()`, sắp xếp giá `sortProducts()`.
4. **Các lớp Test JUnit 5 (`src/test/java/com/poly/sof309/controllers/`):**
   * `AuthControllerTest.java` (29 test cases thực thi)
   * `CartControllerTest.java` (15 test cases thực thi)
   * `ProductControllerTest.java` (8 test cases thực thi)
   * `SampleControllerTest.java` (2 test cases mẫu)

---

## 4. Chỉnh sửa của Sinh viên & Lý do sửa
* **Tinh chỉnh mã nguồn:**
  1. **Khớp mã giảm giá với giao diện Web:** Đảm bảo hàm `applyCoupon()` trong `CartController` nhận diện đúng mã **`MOTO10`** (giảm 10%), đồng thời hỗ trợ `.trim().equalsIgnoreCase()` để người dùng nhập chữ thường hay chữ hoa đều hợp lệ.
  2. **Xử lý biên số lượng:** Đảm bảo khi gọi `updateQuantity(productId, -1)` với sản phẩm có số lượng hiện tại là 1 thì phần tử sẽ được tự động xóa sạch khỏi giỏ (`cart.remove(item)`) và Subtotal trở về 0 VNĐ.
  3. **Khớp danh sách sản phẩm với Frontend:** Khởi tạo dữ liệu xe trong `ProductController` đúng với 8 mẫu xe trong giao diện (`BMW S1000RR 2024`, `Kawasaki Ninja ZX-10R`, `Ducati Streetfighter V4`...).
* **Kết quả thực thi nghiệm thu Tuần 2:**
  * Lệnh chạy: `mvn test -Dtest=*ControllerTest`
  * Kết quả: **Tests run: 54, Failures: 0, Errors: 0, Skipped: 0**
  * Trạng thái: **BUILD SUCCESS (100% Pass)**.
