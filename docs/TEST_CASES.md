# BỘ KỊCH BẢN KIỂM THỬ CHI TIẾT (TEST CASES SPECIFICATION)
## DỰ ÁN: MOTO STORE - SHOWROOM MÔ TÔ PHÂN KHỐI LỚN CAO CẤP
> **Môn học:** SOF309 – Kiểm thử nâng cao cùng AI  
> **Giảng viên hướng dẫn:** ThS. Trần Quang Bình (binhtq7 - Thầy Mười Giáo)  
> **QA Lead / Sinh viên thực hiện:** Tạ Minh Hoàng (MSSV: TS02696 | Lớp: SD21307)  
> **Phiên bản:** 2.0 (Cập nhật đồng bộ toàn diện Tuần 2 & Lab 2 Kế thừa)  
> **Kỹ thuật thiết kế cốt lõi:** Phân vùng tương đương (Equivalence Partitioning - EP) & Phân tích giá trị biên (Boundary Value Analysis - BVA)  
> **Khung kiểm thử:** JUnit 5 (Jupiter Engine) cho Controllers | JUnit 4 (Vintage Engine) cho Lab 2 | Selenium WebDriver 4 POM cho UI.

---

## 📌 PHẦN I. TEST CASES KIỂM THỬ CONTROLLER BACKEND (JUNIT 5 - Y3)

*Quy ước kỹ thuật:*
* `@ParameterizedTest` + `@CsvSource`: Nạp đa bộ dữ liệu kiểm tra phân vùng và giá trị biên form.
* `assertThrows()`: Kiểm thử ném ngoại lệ nghiệp vụ chính xác khi gặp dữ liệu vi phạm.
* `@Timeout`: Đảm bảo thời gian phản hồi không vượt ngưỡng cho phép ($\le 2$ giây).

### 1. Phân hệ Xác thực Tài khoản (`AuthControllerTest`)

| Test Case ID | Hàm kiểm thử | Kỹ thuật (EP/BVA) | Loại (Pos/Neg) | Mô tả mục tiêu kiểm thử | Dữ liệu kiểm thử (Test Data) | Các bước kiểm thử (Test Steps) | Kết quả mong đợi (Expected) | Trạng thái |
| :---: | :--- | :---: | :---: | :--- | :--- | :--- | :--- | :---: |
| **TC_CTRL_AUTH_01** | `authenticate()` | EP | Positive | Đăng nhập thành công với tài khoản quản trị mặc định | `username = "admin@gmail.com"`<br>`password = "123456"` | 1. Gọi `authController.authenticate(username, password)`<br>2. So sánh kết quả boolean | Trả về `true`, xác thực hợp lệ | **Pass** |
| **TC_CTRL_AUTH_02** | `authenticate()` | EP | Negative | Đăng nhập thất bại khi nhập sai mật khẩu | `username = "admin@gmail.com"`<br>`password = "sai_pass_123"` | 1. Gọi `authController.authenticate(username, password)`<br>2. So sánh kết quả boolean | Trả về `false`, từ chối đăng nhập | **Pass** |
| **TC_CTRL_AUTH_03** | `authenticate()` | EP | Negative | Đăng nhập thất bại khi tài khoản chưa từng tồn tại | `username = "chuadangky@domain.com"`<br>`password = "123456"` | 1. Gọi `authController.authenticate(...)`<br>2. So sánh kết quả boolean | Trả về `false`, từ chối đăng nhập | **Pass** |
| **TC_CTRL_AUTH_04** | `authenticate()` | EP/BVA | Negative | Đăng nhập thất bại khi để trống username hoặc password | • `username = "", password = "123"`<br>• `username = "admin", password = ""` | 1. Gọi xác thực với các chuỗi rỗng<br>2. Kiểm tra kết quả | Trả về `false` trong mọi trường hợp trống | **Pass** |
| **TC_CTRL_AUTH_05** | `register()` | EP | Positive | Đăng ký tài khoản mới thành công với dữ liệu hợp lệ | • Tên: `"Tạ Minh Hoàng"`<br>• Email: `"hoangtm@fpt.edu.vn"`<br>• SĐT: `"0901234567"`<br>• Pass: `"Pass@1234"` | 1. Gọi `authController.register(name, email, phone, pass)`<br>2. Kiểm tra tài khoản trong hệ thống | Trả về `true`, lưu tài khoản thành công | **Pass** |
| **TC_CTRL_AUTH_06** | `register()` | BVA | Negative (Exception) | Ném `IllegalArgumentException` khi họ tên rỗng hoặc $< 2$ ký tự | • `name = ""` (0 ký tự)<br>• `name = "A"` (1 ký tự)<br>• `name = "   "` (khoảng trắng) | 1. Truyền họ tên vi phạm biên độ dài<br>2. Dùng `assertThrows(IllegalArgumentException.class, ...)` | Ném chính xác `IllegalArgumentException` | **Pass** |
| **TC_CTRL_AUTH_07** | `register()` | EP | Parameterized | Kiểm tra các phân vùng định dạng Email RFC bằng `@ParameterizedTest` | • `'hoang@fpt.edu.vn', true`<br>• `'hoangfpt.edu.vn', false` (thiếu @)<br>• `'hoang@', false` (thiếu domain)<br>• `'@gmail.com', false` (thiếu user)<br>• `'hoang@com', false` (TLD không hợp lệ) | 1. Chạy lặp qua `@CsvSource`<br>2. Gọi validation email trong `register`<br>3. `assertEquals(expected, actual)` | Chỉ email chuẩn RFC trả về `true`, các định dạng lỗi trả về `false` | **Pass** |
| **TC_CTRL_AUTH_08** | `register()` | EP/BVA | Parameterized | Kiểm tra số điện thoại chuẩn 10 chữ số đầu số Việt Nam bằng `@ParameterizedTest` | • `'090123456', false` (9 số - thiếu)<br>• `'0901234567', true` (10 số đầu 09 - hợp lệ)<br>• `'0381234567', true` (10 số đầu 03 - hợp lệ)<br>• `'09012345678', false` (11 số - thừa)<br>• `'0123456789', false` (đầu số 01 không hợp lệ)<br>• `'090abc1234', false` (chứa chữ) | 1. Chạy lặp qua `@CsvSource`<br>2. Kiểm tra Regex đầu số và độ dài<br>3. `assertEquals(expected, actual)` | Chỉ SĐT đủ 10 chữ số với đầu `03,05,07,08,09` trả về `true` | **Pass** |
| **TC_CTRL_AUTH_09** | `register()` | BVA | Parameterized | Kiểm tra giá trị biên độ dài mật khẩu $[8, 32]$ ký tự bằng `@ParameterizedTest` | • `'user1', '1234567', false` (7 ký tự - dưới biên)<br>• `'user2', '12345678', true` (8 ký tự - biên dưới)<br>• `'user3', 'a'.repeat(32), true` (32 ký tự - biên trên)<br>• `'user4', 'a'.repeat(33), false` (33 ký tự - vượt biên) | 1. Chạy lặp qua `@CsvSource`<br>2. Gọi `register(name, email, phone, pass)`<br>3. Kiểm tra kết quả | Điểm biên 7 và 33 ký tự báo lỗi; điểm biên 8 và 32 ký tự thành công | **Pass** |
| **TC_CTRL_AUTH_10** | `register()` | EP | Negative (Exception) | Ném `DuplicateUserException` khi đăng ký email đã tồn tại | `email = "admin@gmail.com"` (hoặc email đã đăng ký) | 1. Đăng ký email lần 1 thành công<br>2. Đăng ký lại chính email đó<br>3. Dùng `assertThrows(DuplicateUserException.class)` | Ném chính xác `DuplicateUserException` với thông điệp báo email đã tồn tại | **Pass** |
| **TC_CTRL_AUTH_11** | `authenticate()` | Non-Functional | Timeout | Đảm bảo xác thực không bị treo lặp vô tận, phản hồi trong 2s | `username = "admin@gmail.com"`<br>`password = "123456"` | 1. Gắn nhãn `@Timeout(value = 2, unit = TimeUnit.SECONDS)`<br>2. Thực hiện gọi hàm xác thực | Thực thi hoàn tất dưới 2000ms | **Pass** |

---

### 2. Phân hệ Quản lý Giỏ Hàng (`CartControllerTest`)

| Test Case ID | Hàm kiểm thử | Kỹ thuật (EP/BVA) | Loại (Pos/Neg) | Mô tả mục tiêu kiểm thử | Dữ liệu kiểm thử (Test Data) | Các bước kiểm thử (Test Steps) | Kết quả mong đợi (Expected) | Trạng thái |
| :---: | :--- | :---: | :---: | :--- | :--- | :--- | :--- | :---: |
| **TC_CTRL_CART_01** | `addItem()` | EP | Positive | Thêm sản phẩm hợp lệ vào giỏ với số lượng $> 0$ | `productId = 1L`, `quantity = 1` | 1. Khởi tạo giỏ rỗng<br>2. Gọi `cartController.addItem(1L, 1)`<br>3. Kiểm tra danh sách giỏ | Giỏ chứa 1 sản phẩm, số lượng = 1 | **Pass** |
| **TC_CTRL_CART_02** | `addItem()` | EP | Positive | Thêm sản phẩm đã có trong giỏ sẽ tăng dồn số lượng | `productId = 1L`, thêm 2 lần: `qty1 = 1`, `qty2 = 2` | 1. Thêm sản phẩm 1 với số lượng 1<br>2. Thêm tiếp sản phẩm 1 với số lượng 2<br>3. Kiểm tra số lượng | Giỏ vẫn chỉ có 1 mặt hàng, tổng `quantity = 3` | **Pass** |
| **TC_CTRL_CART_03** | `addItem()` | BVA | Negative (Exception) | Ném `InvalidQuantityException` khi thêm số lượng biên $\le 0$ | • `quantity = 0`<br>• `quantity = -1`<br>• `quantity = -5` | 1. Gọi `addItem(1L, qty)` qua `@ParameterizedTest`<br>2. Bắt bằng `assertThrows(InvalidQuantityException.class)` | Ném `InvalidQuantityException`, từ chối thêm vào giỏ | **Pass** |
| **TC_CTRL_CART_04** | `addItem()` | EP | Negative (Exception) | Ném `ProductNotFoundException` khi mã sản phẩm không tồn tại | `productId = 99999L` | 1. Gọi `addItem(99999L, 1)`<br>2. Bắt bằng `assertThrows(ProductNotFoundException.class)` | Ném `ProductNotFoundException` | **Pass** |
| **TC_CTRL_CART_05** | `calculateSubtotal()` | EP | Positive | Tính tổng tiền tạm tính cho nhiều sản phẩm với đơn giá khác nhau | • Xe 1: Giá 949.000.000đ, Qty = 1<br>• Xe 2: Giá 729.000.000đ, Qty = 2 | 1. Thêm 2 mặt hàng vào giỏ<br>2. Gọi `calculateSubtotal()`<br>3. So sánh kết quả | Trả về chính xác $2.407.000.000$ VNĐ | **Pass** |
| **TC_CTRL_CART_06** | `applyCoupon()` | EP | Positive | Áp dụng mã `MOTO10` tính chiết khấu 10% chính xác | `code = "MOTO10"`, `subtotal = 1.000.000.000đ` | 1. Gọi `applyCoupon(code, subtotal)`<br>2. So sánh số tiền chiết khấu | Trả về 100.000.000 VNĐ (10% của 1 tỷ); Tổng thanh toán còn 900.000.000 VNĐ | **Pass** |
| **TC_CTRL_CART_07** | `applyCoupon()` | EP | Positive | Áp dụng mã `moto10` hoặc có khoảng trắng tự động trim/uppercase | • `code = "moto10"`<br>• `code = "  MOTO10  "` | 1. Truyền chuỗi chữ thường và có khoảng trắng<br>2. Kiểm tra kết quả | Hệ thống tự xử lý và áp dụng thành công mức giảm 10% | **Pass** |
| **TC_CTRL_CART_08** | `applyCoupon()` | EP | Negative (Exception) | Ném `InvalidCouponException` khi nhập mã không tồn tại | `code = "DISCOUNT50"` hoặc `"MOTO20"` | 1. Gọi `applyCoupon("DISCOUNT50", 1000000000)`<br>2. Dùng `assertThrows(InvalidCouponException.class)` | Ném `InvalidCouponException`, không giảm giá | **Pass** |
| **TC_CTRL_CART_09** | `applyCoupon()` | BVA | Boundary | Truyền mã giảm giá rỗng hoặc null không áp dụng giảm | `code = ""` hoặc `code = null` | 1. Gọi `applyCoupon("", 1000000000)`<br>2. Kiểm tra giá trị chiết khấu | Trả về `0.0` VNĐ | **Pass** |
| **TC_CTRL_CART_10** | `updateQuantity()` | BVA | Positive | Cập nhật số lượng tại điểm biên giảm về 0 tự động xóa sản phẩm | `productId = 1L`, hiện tại `qty = 1`, `delta = -1` | 1. Cho giỏ có 1 sản phẩm số lượng 1<br>2. Gọi `updateQuantity(1L, -1)`<br>3. Kiểm tra số lượng phần tử giỏ | Sản phẩm tự động bị xóa, giỏ hàng về rỗng (`getItemCount() == 0`) | **Pass** |
| **TC_CTRL_CART_11** | `setQuantity()` | EP | Positive | Đặt trực tiếp số lượng hợp lệ cho sản phẩm | `productId = 1L`, `quantity = 5` | 1. Gọi `setQuantity(1L, 5)`<br>2. Kiểm tra số lượng | Số lượng sản phẩm cập nhật thành 5 | **Pass** |
| **TC_CTRL_CART_12** | `setQuantity()` | BVA | Boundary | Đặt số lượng $\le 0$ qua `setQuantity` tự động xóa sản phẩm | `productId = 1L`, `quantity = 0` | 1. Gọi `setQuantity(1L, 0)`<br>2. Kiểm tra giỏ hàng | Sản phẩm tự động bị loại bỏ khỏi giỏ | **Pass** |
| **TC_CTRL_CART_13** | `clearCart()` | EP | Positive | Xóa toàn bộ giỏ hàng | Giỏ đang chứa nhiều sản phẩm | 1. Gọi `cartController.clearCart()`<br>2. Kiểm tra giỏ hàng | Giỏ hàng rỗng hoàn toàn (`getItemCount() == 0`), subtotal về 0 | **Pass** |

---

### 3. Phân hệ Danh mục & Lọc Sản Phẩm (`ProductControllerTest`)

| Test Case ID | Hàm kiểm thử | Kỹ thuật (EP/BVA) | Loại (Pos/Neg) | Mô tả mục tiêu kiểm thử | Dữ liệu kiểm thử (Test Data) | Các bước kiểm thử (Test Steps) | Kết quả mong đợi (Expected) | Trạng thái |
| :---: | :--- | :---: | :---: | :--- | :--- | :--- | :--- | :---: |
| **TC_CTRL_PROD_01** | `getAllProducts()` | EP | Positive | Lấy danh sách toàn bộ xe mô tô trong hệ thống | Dữ liệu khởi tạo mặc định | 1. Gọi `productController.getAllProducts()`<br>2. Kiểm tra kích thước danh sách | Trả về danh sách gồm 12 mẫu xe mô tô phân khối lớn | **Pass** |
| **TC_CTRL_PROD_02** | `getProductById()` | EP | Positive | Lấy thông tin chi tiết xe theo ID hợp lệ | `id = 1L` | 1. Gọi `getProductById(1L)`<br>2. So sánh tên xe | Trả về đúng xe `"BMW S1000RR 2024"`, giá 949.000.000đ | **Pass** |
| **TC_CTRL_PROD_03** | `getProductById()` | EP | Negative | Lấy thông tin xe với ID không tồn tại | `id = 9999L` | 1. Gọi `getProductById(9999L)`<br>2. Kiểm tra kết quả | Trả về `null` | **Pass** |
| **TC_CTRL_PROD_04** | `searchByName()` | EP | Positive | Tìm kiếm xe không phân biệt chữ hoa, chữ thường và chuỗi con | • `keyword = "bmw"`<br>• `keyword = "DUCATI"`<br>• `keyword = "s1000rr"` | 1. Gọi `searchByName(keyword)`<br>2. Kiểm tra các phần tử trả về | 100% kết quả trả về chứa từ khóa tương ứng trong tên xe | **Pass** |
| **TC_CTRL_PROD_05** | `filterByCategory()` | EP | Positive | Lọc danh sách theo phân khúc Dòng xe | `category = "Sportbike"` | 1. Gọi `filterByCategory("Sportbike")`<br>2. Kiểm tra thuộc tính `category` | Tất cả xe trả về đều có dòng xe `"Sportbike"` | **Pass** |
| **TC_CTRL_PROD_06** | `filterByBrand()` | EP | Positive | Lọc danh sách theo Thương hiệu sản xuất | `brand = "Kawasaki"` | 1. Gọi `filterByBrand("Kawasaki")`<br>2. Kiểm tra thuộc tính `brand` | Tất cả xe trả về đều mang thương hiệu `"Kawasaki"` | **Pass** |
| **TC_CTRL_PROD_07** | `filterCompound()` | EP | Positive | Lọc kết hợp 3 điều kiện: Tên AND Dòng xe AND Hãng | • Keyword: `"Ninja"`<br>• Category: `"Sportbike"`<br>• Brand: `"Kawasaki"` | 1. Gọi hàm lọc kết hợp<br>2. Kiểm tra kết quả | Trả về chính xác mẫu xe `"Kawasaki Ninja ZX-10R"` | **Pass** |
| **TC_CTRL_PROD_08** | `sortProducts()` | EP | Parameterized | Sắp xếp giá tăng dần (`asc`) và giảm dần (`desc`) bằng `@ParameterizedTest` | • `'asc', true`<br>• `'desc', true` | 1. Chạy lặp qua `@CsvSource`<br>2. Gọi `sortProducts(list, order)`<br>3. Kiểm tra tính đơn điệu của mảng giá | Với `'asc'`: $Price_i \le Price_{i+1}$; Với `'desc'`: $Price_i \ge Price_{i+1}$ | **Pass** |

---

## 📌 PHẦN II. TEST CASES CHO MODULE LAB 2 THỰC HÀNH (KẾ THỪA NỀN TẢNG)

### 1. Bài 1: Kiểm thử Đơn vị MathFunc (`MathFuncTest`)

| Test Case ID | Hàm kiểm thử | Kỹ thuật (EP/BVA) | Loại (Pos/Neg) | Mô tả mục tiêu kiểm thử | Dữ liệu kiểm thử | Kết quả mong đợi | Trạng thái |
| :---: | :--- | :---: | :---: | :--- | :--- | :--- | :---: |
| **TC_LAB2_B1_01** | `factorial()` | BVA | Negative (Exception) | Ném `IllegalArgumentException` khi tính giai thừa số âm ($N < 0$) | `number = -1` | Ném `IllegalArgumentException("Number must be greater than zero")` | **Pass** |
| **TC_LAB2_B1_02** | `factorial()` | BVA | Positive | Tính giai thừa tại điểm biên $N = 0$ | `number = 0` | Trả về `1` | **Pass** |
| **TC_LAB2_B1_03** | `factorial()` | EP | Positive | Tính giai thừa với số nguyên dương hợp lệ | `number = 5` | Trả về `120` ($5! = 1 \times 2 \times 3 \times 4 \times 5$) | **Pass** |
| **TC_LAB2_B1_04** | `plus()` | EP | Positive | Thực hiện phép cộng hai số nguyên | `num1 = 1, num2 = 1` | Trả về `2` | **Pass** |

---

### 2. Bài 2: Test Suite & Suite Runner (`fpoly.junit`)

| Test Case ID | Lớp kiểm thử | Mô tả kịch bản | Dữ liệu kiểm thử | Kết quả mong đợi | Trạng thái |
| :---: | :--- | :--- | :--- | :--- | :---: |
| **TC_LAB2_B2_01** | `SuiteTest1` | Kiểm thử in thông điệp chuỗi cơ bản từ `JUnitMessage` | `message = "Fpoly"` | Message in ra console `"Fpoly"`, assert chuỗi thành công | **Pass** |
| **TC_LAB2_B2_02** | `SuiteTest1` | Kiểm thử in thông điệp chào mừng có tiền tố "Hi!" | `message = "Fpoly"` | Message in ra console `"Hi!Fpoly"`, assert thành công | **Pass** |
| **TC_LAB2_B2_03** | `SuiteTest2` | Kiểm thử in thông điệp chuỗi đặc tả format | `message = "Y"` | In ra `"Suite Test 1 is successful Y"`, assert thành công | **Pass** |
| **TC_LAB2_B2_04** | `JunitTest` / `SuiteRunner` | Thực thi tập trung toàn bộ Test Suite gom nhóm bằng JUnitCore | `JunitTest.class` | SuiteRunner chạy thành công cả 3 bài test, báo `Result == true` | **Pass** |

---

### 3. Bài 3: Vòng đời Annotations (`JunitAnnotationsExample`)

| Test Case ID | Annotation | Mô tả kiểm thử | Hành vi mong đợi | Trạng thái |
| :---: | :--- | :--- | :--- | :---: |
| **TC_LAB2_B3_01** | `@BeforeClass` & `@AfterClass` | Kiểm thử phương thức chạy duy nhất 1 lần đầu và cuối class | In `"in before class"` trước mọi test và `"in after class"` sau khi kết thúc | **Pass** |
| **TC_LAB2_B3_02** | `@Before` & `@After` | Kiểm thử phương thức chạy lặp trước và sau mỗi test method | In `"in before"` trước test và `"in after"` sau test | **Pass** |
| **TC_LAB2_B3_03** | `@Test` | Thực thi test method chính | In `"in test"` | **Pass** |
| **TC_LAB2_B3_04** | `@Ignore` | Kiểm thử tính năng bỏ qua test method | Phương thức mang nhãn `@Ignore` bị bỏ qua, không được thực thi | **Pass** |

---

### 4. Bài 4: Hệ thống Điểm số & Xét Học lực FPT Polytechnic (`fpoly.junit.bai4`)

#### 4.1. Lớp `DiemHocSinhTest` (Kiểm thử tính Điểm Trung Bình)
*Công thức:* $\text{Điểm TB} = \frac{\text{Toán} \times 2 + \text{Văn} \times 2 + \text{Anh}}{5}$ với thang điểm $[0.0, 10.0]$.

| Test Case ID | Kỹ thuật (EP/BVA) | Loại | Dữ liệu đầu vào (Toán, Văn, Anh) | Điểm TB kỳ vọng | Kết quả mong đợi | Trạng thái |
| :---: | :---: | :---: | :--- | :---: | :--- | :---: |
| **TC_LAB2_B4_DHS_01** | EP | Positive | `toan = 8.0, van = 7.0, anh = 9.0` | `7.8` | Trả về `7.8` (sai số $\pm 0.001$) | **Pass** |
| **TC_LAB2_B4_DHS_02** | BVA | Positive | `toan = 0.0, van = 0.0, anh = 0.0` (Biên dưới) | `0.0` | Trả về `0.0` | **Pass** |
| **TC_LAB2_B4_DHS_03** | BVA | Positive | `toan = 10.0, van = 10.0, anh = 10.0` (Biên trên) | `10.0` | Trả về `10.0` | **Pass** |
| **TC_LAB2_B4_DHS_04** | BVA | Negative (Exception) | `toan = -1.0, van = 8.0, anh = 7.0` (Điểm âm) | Ngoại lệ | Ném `IllegalArgumentException("Diem phai nam trong khoang tu 0.0 den 10.0")` | **Pass** |
| **TC_LAB2_B4_DHS_05** | BVA | Negative (Exception) | `toan = 8.0, van = 11.0, anh = 7.0` (Vượt 10) | Ngoại lệ | Ném `IllegalArgumentException("Diem phai nam trong khoang tu 0.0 den 10.0")` | **Pass** |

#### 4.2. Lớp `XetHocLucTest` (Kiểm thử Xếp loại Học lực)

| Test Case ID | Kỹ thuật (EP/BVA) | Điểm TB đầu vào | Xếp loại kỳ vọng | Ý nghĩa giá trị kiểm thử | Trạng thái |
| :---: | :---: | :---: | :---: | :--- | :---: |
| **TC_LAB2_B4_XHL_01** | EP | `9.5` | `"Xuất sắc"` | Đại diện phân vùng $\ge 9.0$ | **Pass** |
| **TC_LAB2_B4_XHL_02** | BVA | `9.0` | `"Xuất sắc"` | Điểm biên dưới của loại Xuất sắc | **Pass** |
| **TC_LAB2_B4_XHL_03** | BVA | `10.0` | `"Xuất sắc"` | Điểm biên trên tối đa của thang điểm | **Pass** |
| **TC_LAB2_B4_XHL_04** | BVA | `8.9` | `"Giỏi"` | Điểm biên trên của loại Giỏi (sát ngưỡng 9.0) | **Pass** |
| **TC_LAB2_B4_XHL_05** | BVA | `8.0` | `"Giỏi"` | Điểm biên dưới của loại Giỏi | **Pass** |
| **TC_LAB2_B4_XHL_06** | EP | `8.5` | `"Giỏi"` | Đại diện phân vùng $[8.0, 9.0)$ | **Pass** |
| **TC_LAB2_B4_XHL_07** | BVA | `7.9` | `"Khá"` | Điểm biên trên của loại Khá (sát ngưỡng 8.0) | **Pass** |
| **TC_LAB2_B4_XHL_08** | BVA | `6.5` | `"Khá"` | Điểm biên dưới của loại Khá | **Pass** |
| **TC_LAB2_B4_XHL_09** | EP | `7.0` | `"Khá"` | Đại diện phân vùng $[6.5, 8.0)$ | **Pass** |
| **TC_LAB2_B4_XHL_10** | BVA | `6.4` | `"Trung bình"` | Điểm biên trên của loại Trung bình (sát 6.5) | **Pass** |
| **TC_LAB2_B4_XHL_11** | BVA | `5.0` | `"Trung bình"` | Điểm biên dưới của loại Trung bình | **Pass** |
| **TC_LAB2_B4_XHL_12** | EP | `5.5` | `"Trung bình"` | Đại diện phân vùng $[5.0, 6.5)$ | **Pass** |
| **TC_LAB2_B4_XHL_13** | BVA | `4.9` | `"Yếu"` | Điểm biên trên của loại Yếu (sát ngưỡng 5.0) | **Pass** |
| **TC_LAB2_B4_XHL_14** | BVA | `0.0` | `"Yếu"` | Điểm biên dưới tối thiểu của loại Yếu | **Pass** |
| **TC_LAB2_B4_XHL_15** | BVA (Exception) | `-0.1` hoặc `10.1` | Ngoại lệ | Ném `IllegalArgumentException("Diem trung binh khong hop le")` | **Pass** |

---

## 📌 PHẦN III. TEST CASES KIỂM THỬ TỰ ĐỘNG GIAO DIỆN (SELENIUM POM - Y4)

*Áp dụng kiến trúc Page Object Model (POM), đồng bộ DOM hoàn toàn bằng `WebDriverWait` (Explicit Wait).*

| Test Case ID | Page Object | Group | Loại | Kịch bản kiểm thử | Input Data | Thao tác Selenium | Assert kiểm tra | Trạng thái |
| :---: | :--- | :---: | :---: | :--- | :--- | :--- | :--- | :---: |
| **TC_UI_AUTH_01** | `RegisterPage` | `regression` | Pos | Đăng ký tài khoản thành công từ giao diện form | • Tên: `"Hoàng Minh"`<br>• Email: `"hoang.test@gmail.com"`<br>• SĐT: `"0987654321"`<br>• Pass: `"Pass@1234"` | 1. Mở `#auth-page`<br>2. Bấm `#tab-register`<br>3. Điền form đăng ký<br>4. Bấm Submit | Toast `#toast-container` báo `"Chào mừng Hoàng Minh đến với MOTO STORE!"`; chuyển về `#home-page` | **Pass** |
| **TC_UI_AUTH_02** | `RegisterPage` | `regression` | Neg | Kiểm tra HTML5 validation khi bỏ trống trường Email | Bỏ trống `#reg-email` | 1. Mở form đăng ký<br>2. Nhập họ tên, bỏ trống email<br>3. Bấm Submit | Trình duyệt kích hoạt `required`, form không submit | **Pass** |
| **TC_UI_AUTH_03** | `LoginPage` | `smoke` | Pos | Đăng nhập thành công với tài khoản Admin mặc định | `user: "admin@gmail.com"`<br>`pass: "123456"` | 1. Mở `#auth-page`<br>2. Điền `#login-user` và `#login-pass`<br>3. Bấm Submit | Toast xanh `"Đăng nhập thành công!"`; Header hiển thị `"Hi, admin"` | **Pass** |
| **TC_UI_AUTH_04** | `LoginPage` | `regression` | Neg | Đăng nhập thất bại khi nhập sai mật khẩu | `user: "admin@gmail.com"`<br>`pass: "sai_pass_999"` | 1. Nhập tài khoản và mật khẩu sai<br>2. Bấm Đăng Nhập | Giữ nguyên form, Header không đổi trạng thái đăng nhập | **Pass** |
| **TC_UI_AUTH_05** | `LoginPage` | `smoke` | Pos | Đăng xuất tài khoản người dùng thành công | Đang đăng nhập | 1. Bấm icon Đăng xuất trên Header | Toast báo `"Đã đăng xuất tài khoản."`; Header về trạng thái nút `"Đăng Nhập"` | **Pass** |
| **TC_UI_CATALOG_01** | `CatalogPage` | `smoke` | Pos | Tìm kiếm xe mô tô theo từ khóa có kết quả | Từ khóa: `"BMW"` | 1. Mở `#catalog-page`<br>2. Nhập `"BMW"` vào `#filter-search` | Toàn bộ card sản phẩm tại `#catalog-grid` chứa chữ `"BMW"` | **Pass** |
| **TC_UI_CATALOG_02** | `CatalogPage` | `regression` | Neg | Tìm kiếm với từ khóa không tồn tại | Từ khóa: `"Xe Dap Dien Martin"` | 1. Nhập từ khóa lạ vào ô tìm kiếm | Hiển thị thông báo `"Không tìm thấy xe phù hợp."` | **Pass** |
| **TC_UI_CATALOG_03** | `CatalogPage` | `regression` | Pos | Lọc danh sách theo Dòng xe Sportbike | Select `#filter-category` chọn `"Sportbike"` | 1. Chọn value `"Sportbike"` từ thẻ select | Các xe hiển thị đều có nhãn dòng xe `"SPORTBIKE"` | **Pass** |
| **TC_UI_CATALOG_04** | `CatalogPage` | `regression` | Pos | Lọc kết hợp: Hãng Kawasaki VÀ Dòng Sportbike | • Category: `"Sportbike"`<br>• Brand: `"Kawasaki"` | 1. Chọn Dòng xe `"Sportbike"`<br>2. Chọn Hãng xe `"Kawasaki"` | Grid hiển thị chính xác mẫu xe `"Kawasaki Ninja ZX-10R"` | **Pass** |
| **TC_UI_CATALOG_05** | `CatalogPage` | `regression` | Pos | Sắp xếp giá từ Thấp đến Cao (Ascending) | Select `#filter-sort` chọn `"asc"` | 1. Chọn sắp xếp `"Giá: Thấp đến Cao"`<br>2. Đọc giá sản phẩm 1 và 2 | Giá sản phẩm 1 $\le$ Giá sản phẩm 2 | **Pass** |
| **TC_UI_CATALOG_06** | `CatalogPage` | `smoke` | Pos | Nhấp card xe mở trang Chi tiết xe | Bấm card xe ID = 1 | 1. Bấm vào card xe BMW S1000RR | Trang `#detail-page` mở ra, hiển thị ảnh lớn, giá và bảng thông số | **Pass** |
| **TC_UI_WISH_01** | `WishlistPage` | `smoke` | Pos | Bấm nút Trái tim thêm xe vào danh sách Yêu thích | Sản phẩm ID = 1 | 1. Bấm nút Trái tim trên card BMW S1000RR | Icon tim đổi sang đỏ đặc; Toast báo `"Đã thêm... ❤️"`; Badge `#wishlist-badge` tăng lên 1 | **Pass** |
| **TC_UI_WISH_02** | `WishlistPage` | `regression` | Pos | Xóa tất cả danh sách yêu thích | Đang ở `#wishlist-page` | 1. Bấm nút `#clear-wishlist-btn` | Danh sách rỗng, hiển thị `#wishlist-empty-msg`, badge về 0 | **Pass** |
| **TC_UI_CART_01** | `CartPage` | `smoke` | Pos | Thêm xe vào giỏ hàng từ nút icon Cart trên card | Bấm nút Cart tại card ID = 1 | 1. Bấm nút icon Giỏ hàng màu đỏ tại card xe | Toast báo `"Đã thêm... vào giỏ hàng!"`; Badge `#cart-badge` hiển thị số 1 | **Pass** |
| **TC_UI_CART_02** | `CartPage` | `smoke` | Pos | Mở giỏ hàng và bấm tăng số lượng (+) | Đang ở `#cart-page` | 1. Nhấn nút `+` tại dòng sản phẩm | Số lượng ô input tăng từ 1 lên 2; tổng tiền tạm tính tăng gấp đôi | **Pass** |
| **TC_UI_CART_03** | `CartPage` | `regression` | Boundary (BVA) | Giảm số lượng về 0 (-) tự động loại bỏ xe khỏi giỏ | Sản phẩm có số lượng = 1 | 1. Nhấn nút `-` tại dòng sản phẩm | Xe bị xóa khỏi giỏ, hiển thị giao diện rỗng `#cart-empty-msg` | **Pass** |
| **TC_UI_CART_04** | `CartPage` | `smoke` | Pos | Áp dụng mã giảm giá hợp lệ MOTO10 | Nhập mã `"MOTO10"` | 1. Nhập `"MOTO10"` vào `#coupon-input`<br>2. Bấm `"Áp Dụng"` | Dòng `#coupon-msg` báo xanh `"Áp dụng mã giảm 10% thành công!"`; tổng thanh toán giảm 10% | **Pass** |
| **TC_UI_CART_05** | `CartPage` | `regression` | Neg | Áp dụng mã giảm giá sai | Nhập mã `"SAIMA123"` | 1. Nhập mã sai<br>2. Bấm `"Áp Dụng"` | Dòng `#coupon-msg` báo đỏ `"Mã giảm giá không hợp lệ!"`; giảm giá giữ 0 VNĐ | **Pass** |
| **TC_UI_CHECKOUT_01** | `CheckoutModal` | `smoke` | Pos | Bấm Đặt hàng mở VietQR Modal thanh toán | Giỏ hàng có sản phẩm | 1. Tại `#cart-page`, bấm `"Đặt Hàng & Thanh Toán QR"` | Modal `#checkout-modal` hiển thị nổi trên màn hình | **Pass** |
| **TC_UI_CHECKOUT_02** | `CheckoutModal` | `smoke` | Pos | Kiểm tra thông tin thanh toán VietQR VietinBank | Đang mở `#checkout-modal` | 1. Đọc thuộc tính `src` của `#vietqr-image`<br>2. Đọc text `#qr-amount` và `#qr-memo` | URL ảnh chứa BIN `970415`, STK `10288889999`; Memo bắt đầu bằng `"MOTO"` + 4 số | **Pass** |
| **TC_UI_CHECKOUT_03** | `CheckoutModal` | `smoke` | Pos | Xác nhận hoàn tất chuyển khoản thành công | Đang mở `#checkout-modal` | 1. Bấm nút `"Đã Chuyển Khoản Thành Công"` | Modal đóng, giỏ hàng được làm sạch, Toast chúc mừng xuất hiện | **Pass** |
| **TC_UI_GAME_01** | `GamePage` | `regression` | Pos | Nhấp trúng Táo 🍎 trong Mini Game được cộng 10 điểm | Đang ở `#game-page` | 1. Mở `#game-page`<br>2. Nhấp vào ô chứa Táo 🍎 | Điểm số `#game-score` tăng +10 điểm; Toast báo `"+10 điểm! 🍎"` | **Pass** |
| **TC_UI_GAME_02** | `GamePage` | `regression` | Neg/BVA | Nhấp trúng Bom 💣 bị trừ 20 điểm và không bị âm | Đang ở `#game-page` | 1. Nhấp vào ô chứa Bom 💣 | Điểm bị trừ 20 nhưng bị chặn tối thiểu bằng 0; Toast báo `"-20 điểm! 💥"` | **Pass** |
| **TC_UI_THEME_01** | Header Utility | `regression` | Pos | Chuyển đổi giao diện Dark Mode / Light Mode | Mọi trang | 1. Bấm nút `#theme-toggle` trên Header | Thẻ `<html>` được toggle class `dark`; icon chuyển giữa mặt trăng và mặt trời | **Pass** |
