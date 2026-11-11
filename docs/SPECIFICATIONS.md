# TÀI LIỆU ĐẶC TẢ NGHIỆP VỤ HỆ THỐNG (SPECIFICATIONS)
## DỰ ÁN: MOTO STORE - SHOWROOM MÔ TÔ PHÂN KHỐI LỚN CAO CẤP
> **Môn học:** SOF309 – Kiểm thử nâng cao cùng AI  
> **Giảng viên hướng dẫn:** ThS. Trần Quang Bình (binhtq7 - Thầy Mười Giáo)  
> **Sinh viên thực hiện:** Tạ Minh Hoàng  
> **Mã số sinh viên (MSSV):** TS02696  
> **Mã Lớp:** SD21307  
> *Tài liệu này được biên soạn dựa trên phân tích toàn bộ mã nguồn của dự án, dùng làm ngữ cảnh chuẩn (Context Knowledge Base) nạp vào AI RAG để sinh Kế hoạch kiểm thử (Test Plan), Bộ kịch bản kiểm thử (Test Cases), Unit Test (JUnit 5) và Automation UI Test (Selenium WebDriver POM).*

---

## 📌 PHẦN I. THÔNG TIN TỔNG QUAN DỰ ÁN

| Hạng mục | Chi tiết đặc tả |
| :--- | :--- |
| **Tên dự án** | **MOTO STORE - Showroom Siêu Xe Mô Tô Phân Khối Lớn Cao Cấp** |
| **Mục tiêu hệ thống** | Cung cấp nền tảng số trưng bày xe phân khối lớn (Superbike, Nakedbike, Adventure...), hỗ trợ khách hàng tìm kiếm, lọc xe, xem chi tiết thông số kỹ thuật, quản lý danh sách yêu thích, giỏ hàng, áp dụng voucher và thanh toán chuyển khoản VietQR tự động. Tích hợp Mini Game gamification giữ chân khách hàng. |
| **Kiến trúc phát triển** | • **Frontend SPA (Single Page Application):** HTML5, CSS3, Tailwind CSS (JIT Engine), Font Awesome 6, JavaScript ES6+, LocalStorage API.<br>• **Backend / Testing Target:** Java 17, Spring Boot / Embedded Web Server phục vụ tài nguyên tĩnh tại `src/main/resources/static/`.<br>• **Cổng chạy Local:** `http://localhost:8080` (hoặc mở trực tiếp file `index.html` trong trình duyệt).<br>• **Testing Frameworks:** JUnit 5 (Unit Test Controllers/Services), Selenium WebDriver 4 (UI Automation Testing with Page Object Model), TestNG (Suite Runner & Data-Driven). |
| **Tích hợp bên thứ 3** | • **VietQR API:** Cổng tạo mã QR thanh toán động `https://img.vietqr.io` qua ngân hàng VietinBank.<br>• **Unsplash Image CDN:** Nguồn dữ liệu hình ảnh siêu xe chất lượng cao. |

---

## 📌 PHẦN II. KIẾN TRÚC ĐIỀU HƯỚNG MÀN HÌNH (ROUTING ARCHITECTURE)

Hệ thống hoạt động theo mô hình Single Page Application (SPA), chuyển đổi hiển thị giữa các section thông qua hàm JavaScript `showPage(pageId)`. Tất cả các section mang class chung `.page-content`:

```mermaid
flowchart TD
    Header["HEADER / NAVIGATION BAR\n(Logo, Nav Links, Theme Toggle, Wishlist, Cart, Auth)"]
    Header --> Home["home-page\n(Hero Slider, Countdown Sale, 4 Xe Nổi Bật)"]
    Header --> Catalog["catalog-page\n(Bộ Lọc Đa Năng, Tìm Kiếm, Sắp Xếp, Lưới Xe)"]
    Header --> Wishlist["wishlist-page\n(Danh Sách Xe Yêu Thích, Xóa Tất Cả)"]
    Header --> Game["game-page\n(Mini Game Hái Táo Tránh Bom 3x3 Săn Voucher)"]
    Header --> Cart["cart-page\n(Giỏ Hàng, Thay Đổi Số Lượng, Mã Giảm Giá)"]
    Header --> Auth["auth-page\n(Tab Đăng Nhập & Đăng Ký Tài Khoản)"]
    
    Home --> Detail["detail-page\n(Ảnh Chi Tiết, Thông Số 170 Hạng Mục, Đặt Mua)"]
    Catalog --> Detail
    Wishlist --> Detail
    Detail --> Cart
    Cart --> CheckoutModal["#checkout-modal\n(VietQR Modal, Bộ Đếm 15 Phút, Xác Nhận CK)"]
```

### Danh mục các trang và ID phần tử điều hướng:
1. **Trang Chủ (`#home-page`):** Hero Slider giới thiệu BMW S1000RR 2024, bộ đếm ngược Mega Sale hè (`#cd-days`, `#cd-hours`, `#cd-mins`, `#cd-secs`), danh mục 4 xe nổi bật (`#home-featured-grid`).
2. **Danh Mục Xe (`#catalog-page`):** Toàn bộ sản phẩm mô tô được render động tại `#catalog-grid`.
3. **Chi Tiết Xe (`#detail-page`):** Hiển thị chi tiết xe tại `#detail-content` gồm thư viện ảnh (`#detail-main-img`), giá niêm yết, thông số kỹ thuật (Dung tích xilanh, ODO, Năm sản xuất, Hộp số), địa chỉ showroom và cam kết kiểm định.
4. **Danh Sách Yêu Thích (`#wishlist-page`):** Hiển thị các xe người dùng đã "thả tim" tại `#wishlist-grid`, nút "Xóa tất cả" (`#clear-wishlist-btn`), giao diện rỗng (`#wishlist-empty-msg`).
5. **Giỏ Hàng (`#cart-page`):** Danh sách xe trong giỏ (`#cart-items-container`), hộp tóm tắt đơn hàng (`#summary-subtotal`, `#summary-discount`, `#summary-total`), ô nhập voucher (`#coupon-input`).
6. **Mini Game Hái Táo Tránh Bom (`#game-page`):** Bàn cờ 3x3 (`#game-grid`), bảng điểm (`#game-score`), trạng thái (`#game-status-text`), nút điều khiển (`#btn-start-game`).
7. **Xác Thực Người Dùng (`#auth-page`):** Chuyển tab Đăng nhập (`#tab-login`) và Đăng ký (`#tab-register`), Form đăng nhập (`#form-login`), Form đăng ký (`#form-register`).
8. **Modal Thanh Toán VietQR (`#checkout-modal`):** Ảnh QR động (`#vietqr-image`), thời gian giữ đơn (`#qr-timer`), số tiền (`#qr-amount`), nội dung chuyển khoản (`#qr-memo`).

---

## 📌 PHẦN III. QUY TẮC NGHIỆP VỤ & RÀNG BUỘC VALIDATION CHI TIẾT (BUSINESS RULES)

### 1. Phân hệ Xác thực Tài khoản (Authentication & Authorization)

#### 1.1. Chức năng Đăng Ký (Register)
* **Form ID:** `#form-register`
* **Các trường dữ liệu & Ràng buộc kiểm thử:**
  * **Họ và tên (`#reg-name`):**
    * Bắt buộc (`required`), không được chỉ chứa khoảng trắng.
    * Độ dài hợp lệ: Từ 2 đến 50 ký tự.
    * Ký tự cho phép: Chữ cái tiếng Việt, tiếng Anh và dấu cách; không chứa ký tự số hoặc ký tự đặc biệt (`!@#$%^&*`).
  * **Địa chỉ Email (`#reg-email`):**
    * Bắt buộc (`required`), kiểu `type="email"`.
    * Phải đúng định dạng chuẩn RFC (Ví dụ: `user@domain.com`, `hoangtm@fpt.edu.vn`).
    * Phải là duy nhất (Unique), không được trùng với tài khoản đã tồn tại trong LocalStorage.
  * **Số điện thoại (`#reg-phone`):**
    * Bắt buộc (`required`), kiểu `type="tel"`.
    * Định dạng chuẩn Việt Nam: Đúng 10 chữ số, bắt đầu bằng các đầu số hợp lệ: `03, 05, 07, 08, 09`.
  * **Mật khẩu (`#reg-pass`):**
    * Bắt buộc (`required`), kiểu `type="password"`.
    * Độ dài biên kiểm thử: Tối thiểu 8 ký tự, tối đa 32 ký tự.
    * Tiêu chuẩn an toàn: Khuyến nghị chứa ít nhất 1 chữ hoa, 1 chữ thường, 1 chữ số và 1 ký tự đặc biệt.
* **Xử lý kết quả nghiệp vụ:**
  * **Thành công (Positive):** Lưu đối tượng `{ name, email }` vào `localStorage.setItem('moto_user', ...)` và `{ account, password, fullname }` vào danh sách tài khoản; hiển thị Toast thông báo màu xanh `Chào mừng [Tên] đến với MOTO STORE!`; chuyển hướng về `#home-page`; cập nhật thông tin người dùng trên Header (`#auth-section`).
  * **Thất bại (Negative):** Nếu vi phạm bất kỳ ràng buộc nào, hiển thị cảnh báo lỗi tương ứng, focus vào trường bị sai và ngăn chặn submit (`e.preventDefault()`).

#### 1.2. Chức năng Đăng Nhập (Login)
* **Form ID:** `#form-login`
* **Các trường dữ liệu & Ràng buộc kiểm thử:**
  * **Tài khoản đăng nhập (`#login-user`):**
    * Bắt buộc (`required`), có thể nhập Email hoặc Số điện thoại.
    * Không được để trống.
  * **Mật khẩu đăng nhập (`#login-pass`):**
    * Bắt buộc (`required`), không được để trống.
* **Logic kiểm tra xác thực (Authentication Matching):**
  * Hệ thống so khớp với thông tin đã lưu trong `localStorage` hoặc tài khoản Quản trị viên mặc định:
    * `mockUser.account`: `admin@gmail.com`
    * `mockUser.password`: `123456`
    * `mockUser.fullname`: `Nguyễn Văn Admin`
* **Xử lý kết quả nghiệp vụ:**
  * **Đúng tài khoản & mật khẩu:** Lưu phiên đăng nhập vào LocalStorage; hiển thị Toast xanh `Đăng nhập thành công!`; chuyển về `#home-page`; Header chuyển sang trạng thái hiển thị `Hi, [Tên]` kèm icon Đăng xuất (`.fa-right-from-bracket`).
  * **Sai tài khoản hoặc mật khẩu:** Giữ nguyên form, hiển thị thông báo lỗi màu đỏ/Toast cảnh báo `Tài khoản hoặc mật khẩu không chính xác!`.
  * **Bỏ trống một hoặc cả hai trường:** Trình duyệt chặn submit bằng HTML5 Validation báo `Please fill out this field`.

#### 1.3. Chức năng Đăng Xuất (Logout)
* **Thao tác:** Bấm vào nút Đăng xuất trên thanh Header (`onclick="logout()"`).
* **Xử lý:** Xóa `moto_user` khỏi `localStorage`; cập nhật lại `#auth-section` thành nút "Đăng Nhập"; hiển thị Toast màu xanh dương `Đã đăng xuất tài khoản.`; đưa người dùng về `#home-page`.

---

### 2. Phân hệ Quản lý & Lọc Sản Phẩm (Catalog & Product Filtering)

#### 2.1. Cấu trúc Thực thể Sản phẩm (Motorcycle Entity Schema)
Hệ thống quản lý danh sách mô tô cao cấp (`PRODUCTS`) với các thuộc tính kiểm thử:
* `id` *(Integer, Primary Key)*: Số nguyên dương duy nhất (`1, 2, 3...`).
* `name` *(String, Required)*: Tên dòng xe (Ví dụ: `BMW S1000RR 2024`, `Kawasaki Ninja ZX-10R`).
* `brand` *(String, Enum)*: Hãng sản xuất gồm 7 thương hiệu lớn: `BMW`, `Kawasaki`, `Ducati`, `Yamaha`, `Honda`, `Aprilia`, `KTM`.
* `category` *(String, Enum)*: Dòng xe gồm 4 phân khúc: `Sportbike`, `Nakedbike`, `Adventure`, `Classic`.
* `price` *(Long, Numeric > 0)*: Giá bán niêm yết tính bằng VNĐ (Dao động từ 345.000.000 VNĐ đến 1.050.000.000 VNĐ).
* `odo` *(String)*: Số km đã vận hành (Ví dụ: `0 km`, `1,200 km`, `3,500 km`).
* `cc` *(String)*: Dung tích xi lanh thực tế (Ví dụ: `998 cc`, `999 cc`, `1103 cc`, `1254 cc`, `1301 cc`).
* `year` *(Integer)*: Năm sản xuất hợp lệ từ 2021 đến 2024.
* `gearbox` *(String)*: Hộp số tiêu chuẩn (Ví dụ: `6 cấp`, `Hộp số thủ công`).
* `image` *(String, URL)*: Đường dẫn ảnh sản phẩm hiển thị trên Card và trang Chi tiết.

#### 2.2. Quy tắc Tìm kiếm, Lọc và Sắp xếp (Search, Filter, Sort)
* **Ô tìm kiếm (`#filter-search`):**
  * Sự kiện: `oninput="applyFilters()"`.
  * Logic: Tìm kiếm gần đúng không phân biệt hoa thường theo tên xe (`p.name.toLowerCase().includes(search)`).
  * Biên: Nhập chuỗi rỗng $\rightarrow$ trả về danh sách theo bộ lọc khác; Nhập từ khóa không tồn tại (ví dụ: `Xe Đạp Điện`) $\rightarrow$ hiển thị thông báo `Không tìm thấy xe phù hợp.`
* **Bộ lọc Dòng xe (`#filter-category`):**
  * Các tùy chọn: `Tất cả dòng xe ("")`, `Sportbike`, `Nakedbike`, `Adventure`, `Classic`.
* **Bộ lọc Thương hiệu (`#filter-brand`):**
  * Các tùy chọn: `Tất cả hãng ("")`, `BMW`, `Kawasaki`, `Ducati`, `Yamaha`, `Honda`, `Aprilia`, `KTM`.
* **Sắp xếp theo Giá bán (`#filter-sort`):**
  * `default`: Giữ nguyên thứ tự mặc định ban đầu.
  * `asc`: Sắp xếp tăng dần theo giá bán (`a.price - b.price`).
  * `desc`: Sắp xếp giảm dần theo giá bán (`b.price - a.price`).
* **Quy tắc phối hợp (Compound Filtering):** Kết quả hiển thị là giao (AND) của cả 3 điều kiện: Từ khóa Tên AND Dòng xe AND Hãng xe, sau đó áp dụng thuật toán Sắp xếp giá.

---

### 3. Phân hệ Giỏ Hàng & Quản lý Đơn Hàng (Cart & Order Management)

#### 3.1. Thao tác với Giỏ Hàng (Cart Operations)
* **Thêm vào giỏ (`addToCart(productId)`):**
  * Kiểm tra sản phẩm theo ID.
  * Nếu sản phẩm chưa có trong giỏ: Đưa vào giỏ với số lượng `quantity = 1`.
  * Nếu sản phẩm đã tồn tại trong giỏ: Tăng số lượng lên `quantity + 1`.
  * Cập nhật số lượng trên `#cart-badge` tại Header.
  * Lưu vào `localStorage.setItem('moto_cart', ...)`.
  * Hiển thị Toast thành công: `Đã thêm "[Tên xe]" vào giỏ hàng!`.
* **Mua ngay (`buyNow(productId)`):**
  * Tự động gọi `addToCart(productId)` và chuyển ngay sang `#cart-page`.
* **Tăng/Giảm số lượng (`updateQuantity(productId, delta)`):**
  * Bấm nút `+`: Tăng số lượng lên 1 đơn vị.
  * Bấm nút `-`: Giảm số lượng 1 đơn vị. Nếu số lượng sau khi giảm $\le 0$, tự động loại bỏ sản phẩm ra khỏi giỏ.
* **Nhập trực tiếp số lượng (`setQuantity(productId, value)`):**
  * Ràng buộc: Số nguyên dương $\ge 1$.
  * Biên kiểm thử:
    * Nhập giá trị hợp lệ (Ví dụ: `2`, `5`) $\rightarrow$ Giỏ hàng cập nhật đúng số lượng và tính lại thành tiền.
    * Nhập số $\le 0$, số âm, chữ cái hoặc để trống $\rightarrow$ Tự động xóa sản phẩm khỏi giỏ hàng.
* **Xóa sản phẩm (`removeFromCart(productId)`):**
  * Loại bỏ xe tương ứng khỏi giỏ hàng, cập nhật lại DOM và hiển thị Toast `Đã xóa sản phẩm khỏi giỏ hàng.`.
* **Xóa toàn bộ giỏ (`clearCart()`):**
  * Xóa sạch mảng `cart = []`, reset tổng tiền về 0 VNĐ, hiển thị giao diện rỗng `#cart-empty-msg`.

#### 3.2. Tính toán Hóa đơn & Mã Giảm Giá (Pricing & Coupon Engine)
* **Công thức tính toán:**
  $$\text{Tạm tính (Subtotal)} = \sum_{i=1}^{n} (\text{price}_i \times \text{quantity}_i)$$
  $$\text{Số tiền giảm (Discount)} = \text{Subtotal} \times \left(\frac{\text{discountPercent}}{100}\right)$$
  $$\text{Tổng thanh toán (Total)} = \text{Subtotal} - \text{Discount}$$
* **Nghiệp vụ Mã Giảm Giá (`#coupon-input`, nút `#btn-apply-coupon`):**
  * Mã voucher hợp lệ duy nhất: **`MOTO10`** (Hệ thống tự động loại bỏ khoảng trắng và chuyển thành chữ in hoa bằng `.trim().toUpperCase()`).
  * **Trường hợp nhập đúng `MOTO10`:**
    * Thiết lập `discountPercent = 10` (Giảm 10% trên tổng giá trị đơn hàng).
    * Hiển thị thông báo màu xanh tại `#coupon-msg`: `Áp dụng mã giảm 10% thành công!`.
    * Cập nhật lại số tiền tại `#summary-discount` và `#summary-total`.
  * **Trường hợp nhập sai (Ví dụ: `DISCOUNT50`, `MOTO20`):**
    * Giữ nguyên `discountPercent = 0`.
    * Hiển thị thông báo màu đỏ tại `#coupon-msg`: `Mã giảm giá không hợp lệ!`.
  * **Trường hợp xóa trắng ô mã giảm giá:**
    * Xóa nội dung thông báo tại `#coupon-msg`.

---

### 4. Phân hệ Thanh Toán VietQR Tự Động (VietQR Payment Gateway)

* **Điều kiện tiên quyết:** Giỏ hàng phải có ít nhất 1 sản phẩm ($Total > 0$). Nếu bấm thanh toán khi giỏ hàng trống $\rightarrow$ Toast cảnh báo `Giỏ hàng trống!` và chặn mở modal.
* **Kích hoạt Modal (`openCheckoutModal()`):**
  * Mở cửa sổ thanh toán `#checkout-modal`.
* **Tích hợp Cổng VietQR:**
  * Mã Ngân hàng (BIN): `970415` (Ngân hàng TMCP Công Thương Việt Nam - VietinBank).
  * Số tài khoản nhận: `10288889999`.
  * Tên chủ tài khoản: `SHOWROOM MOTO STORE VIETNAM`.
  * Số tiền thanh toán: Lấy chính xác giá trị `total` của đơn hàng sau giảm giá.
  * Mã nội dung chuyển khoản (Memo): Tự động sinh ngẫu nhiên theo cú pháp `MOTO` + số ngẫu nhiên 4 chữ số (Ví dụ: `MOTO3892`).
  * URL sinh ảnh QR động:  
    `https://img.vietqr.io/image/970415-10288889999-compact2.png?amount={total}&addInfo={memo}&accountName=SHOWROOM%20MOTO%20STORE`
* **Bộ đếm thời gian giữ đơn hàng (`#qr-timer`):**
  * Thời hạn phiên thanh toán: 15 phút (900 giây).
  * Đồng hồ đếm lùi từng giây hiển thị theo định dạng `MM:SS`.
  * **Hết giờ (Timeout - 00:00):** Tự động đóng modal `#checkout-modal`, hủy phiên QR, hiển thị Toast cảnh báo `Hết thời gian giữ đơn hàng QR. Vui lòng thử lại!`.
* **Xác nhận thanh toán (`confirmPaymentSuccess()`):**
  * Người dùng bấm nút "Đã Chuyển Khoản Thành Công" $\rightarrow$ Đóng modal, làm trống giỏ hàng, hiển thị Toast chúc mừng `🎉 Đặt hàng thành công! MOTO STORE sẽ liên hệ quý khách sớm nhất.`.

---

### 5. Phân hệ Danh Sách Yêu Thích (Wishlist Management)

* **Thêm/Bỏ yêu thích (`toggleWishlist(productId)`):**
  * Tương tác qua nút hình trái tim trên card sản phẩm hoặc trang chi tiết xe.
  * Nếu sản phẩm chưa có trong danh sách: Thêm ID vào mảng `wishlist`, đổi icon sang tim đặc màu đỏ (`fa-solid text-red-500`), hiển thị Toast `Đã thêm "[Tên xe]" vào danh sách yêu thích! ❤️`.
  * Nếu sản phẩm đã có: Gỡ ID khỏi mảng `wishlist`, đổi icon sang tim rỗng (`fa-regular`), hiển thị Toast `Đã xóa "[Tên xe]" khỏi danh sách yêu thích`.
* **Đồng bộ hóa dữ liệu:**
  * Lưu trữ vào `localStorage.setItem('moto_wishlist', ...)`.
  * Cập nhật số lượng huy hiệu trên Header tại `#wishlist-badge`.
* **Xóa tất cả yêu thích (`clearWishlist()`):**
  * Làm rỗng danh sách yêu thích, hiển thị thông báo trống `#wishlist-empty-msg`.

---

### 6. Phân hệ Mini Game "Hái Táo Tránh Bom" (Gamification Marketing)

* **Mục tiêu:** Tạo trải nghiệm tương tác vui nhộn và thưởng mã giảm giá `MOTO10` cho người dùng đạt mốc điểm quy định.
* **Cơ chế hoạt động:**
  * Bàn cờ dạng ma trận $3 \times 3$ gồm 9 ô bấm (`.game-cell`).
  * Vòng lặp thời gian (`setInterval` mỗi 1000ms = 1 giây):
    * Chọn ngẫu nhiên 1 trong 9 vị trí (Index từ 0 đến 8).
    * Xác suất xuất hiện: **65% Táo đỏ 🍎** và **35% Bom đen 💣**.
* **Quy tắc tính điểm (`clickGameCell(index)`):**
  * Nhấp trúng Táo 🍎: **Cộng +10 điểm**, hiệu ứng đổi sang icon lấp lánh ✨, hiển thị Toast `+10 điểm! 🍎`.
  * Nhấp trúng Bom 💣: **Trừ -20 điểm**, hiệu ứng đổi sang icon vụ nổ 💥, hiển thị Toast `-20 điểm! 💥`.
  * Ràng buộc biên điểm số: Điểm số không bị âm ($Score \ge 0$).
* **Mốc thưởng Voucher:**
  * Khi điểm số đạt $\ge 100$ điểm lần đầu tiên (`gameScore >= 100`): Hệ thống kích hoạt Toast chúc mừng và tặng mã: `🎉 Xuất sắc! Bạn đã đạt trên 100 điểm. Sử dụng mã MOTO10 ở Giỏ hàng để giảm 10%!`.
* **Điều khiển Game:**
  * Nút Tạm Dừng / Tiếp Tục (`toggleGameLoop()`): Chuyển đổi trạng thái `isGameRunning` và icon Play/Pause.
  * Nút Chơi Lại (`resetGameScore()`): Đặt lại điểm số về 0, xóa trạng thái bàn cờ.

---

## 📌 PHẦN IV. MA TRẬN PHÂN VÙNG TƯƠNG ĐƯƠNG & GIÁ TRỊ BIÊN CHO KIỂM THỬ (TEST DESIGN MATRIX)

Dưới đây là bảng phân vùng tương đương (Equivalence Partitioning) và phân tích giá trị biên (Boundary Value Analysis) làm ngữ cảnh nạp vào AI RAG để sinh các kịch bản kiểm thử:

### 1. Kiểm thử Đăng ký Tài khoản (Register Validation)
| Trường dữ liệu | Vùng hợp lệ (Valid Partition) | Vùng không hợp lệ (Invalid Partition) | Điểm biên cần test (Boundary Values) |
| :--- | :--- | :--- | :--- |
| **Họ và tên** | 2 - 50 ký tự chữ cái | • Trống<br>• < 2 ký tự<br>• > 50 ký tự<br>• Chứa số / ký tự đặc biệt | `""` (0 ký tự), `"A"` (1 ký tự), `"An"` (2 ký tự), 50 ký tự, 51 ký tự |
| **Email** | Đúng định dạng email RFC (`user@gmail.com`) | • Trống<br>• Sai định dạng thiếu `@`<br>• Thiếu domain (`user@`)<br>• Email đã tồn tại | `"test@"`, `"@domain.com"`, `"valid.user@gmail.com"` |
| **Số điện thoại** | 10 chữ số, bắt đầu bằng `03, 05, 07, 08, 09` | • Trống<br>• < 10 số hoặc > 10 số<br>• Chứa chữ cái<br>• Đầu số lạ (01x, 02x) | `090123456` (9 số), `0901234567` (10 số), `09012345678` (11 số), `"090abc1234"` |
| **Mật khẩu** | 8 - 32 ký tự, đủ chữ hoa, số, ký tự đặc biệt | • Trống<br>• < 8 ký tự<br>• > 32 ký tự | 7 ký tự (Fail), 8 ký tự (Pass), 32 ký tự (Pass), 33 ký tự (Fail) |

### 2. Kiểm thử Số lượng trong Giỏ Hàng (Cart Quantity)
| Tham số | Vùng hợp lệ | Vùng không hợp lệ | Điểm biên | Kết quả mong đợi |
| :--- | :--- | :--- | :--- | :--- |
| **Số lượng (qty)** | Số nguyên từ 1 đến 99 | • Số 0<br>• Số âm<br>• Không phải số (NaN)<br>• Bỏ trống | `qty = 0` $\rightarrow$ Tự động xóa khỏi giỏ<br>`qty = 1` $\rightarrow$ Hợp lệ, giữ sản phẩm<br>`qty = -1` $\rightarrow$ Tự động xóa khỏi giỏ |

### 3. Kiểm thử Áp dụng Mã Giảm Giá (Coupon Validation)
| Dữ liệu nhập | Phân vùng | Kết quả mong đợi |
| :--- | :--- | :--- |
| `"MOTO10"` | Hợp lệ chuẩn | Giảm giá 10%, hiển thị thông báo thành công xanh |
| `"moto10"`, `"  MOTO10  "` | Hợp lệ (Cần trim và uppercase) | Giảm giá 10% thành công |
| `"MOTO"`, `"MOTO20"`, `"123456"` | Mã không tồn tại | Báo lỗi đỏ "Mã giảm giá không hợp lệ!", giảm giá = 0 VNĐ |
| `""` (Trống) | Trống | Không áp dụng, thông báo rỗng |

### 4. Kiểm thử Tìm Kiếm & Lọc Xe
| Tiêu chí | Dữ liệu kiểm thử | Kết quả mong đợi |
| :--- | :--- | :--- |
| Tìm theo tên | `"BMW"` | Trả về các mẫu xe của BMW (S1000RR, R1250 GS) |
| Tìm theo tên không phân biệt hoa thường | `"bmw s1000rr"` | Trả về chính xác xe BMW S1000RR 2024 |
| Tìm từ khóa không tồn tại | `"Xe Đua F1"` | Hiển thị thông báo "Không tìm thấy xe phù hợp." |
| Lọc Dòng xe | `"Adventure"` | Chỉ hiển thị các dòng xe Adventure (BMW R1250 GS...) |
| Sắp xếp giá tăng dần | `"asc"` | Xe có giá thấp nhất xếp đầu, giá cao nhất xếp cuối |
| Sắp xếp giá giảm dần | `"desc"` | Xe có giá cao nhất xếp đầu, giá thấp nhất xếp cuối |

---

## 📌 PHẦN V. DANH SÁCH BỘ ĐỊNH DANH GIAO DIỆN PHỤC VỤ AUTOMATION (SELENIUM POM LOCATORS)

Để xây dựng các lớp **Page Object Model (POM)** trong `src/test/java/com/poly/sof309/ui/pages/`, bảng sau tổng hợp đầy đủ các Selector (ID / CSS / XPath) chuẩn xác nhất của hệ thống:

### 1. Navigation & Header Locators
* Logo về trang chủ: `//a[contains(., 'MOTOSTORE')]`
* Nút chuyển Trang Chủ: `//nav//button[text()='Trang Chủ']`
* Nút chuyển Danh Mục: `//nav//button[text()='Danh Mục Xe']`
* Nút chuyển Yêu Thích: `//nav//button[contains(., 'Yêu Thích')]`
* Nút chuyển Game: `//nav//button[contains(., 'Game Săn Mã')]`
* Huy hiệu số lượng yêu thích: `By.id("wishlist-badge")`
* Nút mở Giỏ hàng: `//button[@title='Giỏ hàng']`
* Huy hiệu số lượng giỏ hàng: `By.id("cart-badge")`
* Nút Đổi Chế Độ Dark/Light: `By.id("theme-toggle")`
* Khung hiển thị tài khoản Header: `By.id("auth-section")`

### 2. Login & Register Locators (`AuthPage.java`)
* Tab Đăng Nhập: `By.id("tab-login")`
* Tab Đăng Ký: `By.id("tab-register")`
* Form Đăng Nhập: `By.id("form-login")`
* Input Tài khoản Đăng nhập: `By.id("login-user")`
* Input Mật khẩu Đăng nhập: `By.id("login-pass")`
* Nút Submit Đăng Nhập: `//form[@id='form-login']//button[@type='submit']`
* Form Đăng Ký: `By.id("form-register")`
* Input Họ và Tên: `By.id("reg-name")`
* Input Email: `By.id("reg-email")`
* Input Số điện thoại: `By.id("reg-phone")`
* Input Mật khẩu: `By.id("reg-pass")`
* Nút Submit Đăng Ký: `//form[@id='form-register']//button[@type='submit']`

### 3. Catalog & Filter Locators (`CatalogPage.java`)
* Input Tìm kiếm tên xe: `By.id("filter-search")`
* Select Dòng xe: `By.id("filter-category")`
* Select Hãng xe: `By.id("filter-brand")`
* Select Sắp xếp giá: `By.id("filter-sort")`
* Container lưới sản phẩm: `By.id("catalog-grid")`
* Card sản phẩm: `By.cssSelector("#catalog-grid > div")`

### 4. Cart & Checkout Locators (`CartPage.java`)
* Container danh sách giỏ: `By.id("cart-items-container")`
* Thông báo giỏ hàng trống: `By.id("cart-empty-msg")`
* Input Mã giảm giá: `By.id("coupon-input")`
* Nút Áp dụng Mã: `//button[contains(text(), 'Áp Dụng')]`
* Thông báo kết quả mã giảm giá: `By.id("coupon-msg")`
* Tiền tạm tính: `By.id("summary-subtotal")`
* Tiền giảm giá: `By.id("summary-discount")`
* Tổng thanh toán: `By.id("summary-total")`
* Nút Thanh toán VietQR: `//button[contains(., 'Đặt Hàng & Thanh Toán QR')]`
* Nút Xóa toàn bộ giỏ: `//button[contains(., 'Xóa Tất Cả Giỏ Hàng')]`

### 5. VietQR Modal Locators (`CheckoutModal.java`)
* Khung Modal: `By.id("checkout-modal")`
* Ảnh QR: `By.id("vietqr-image")`
* Đồng hồ đếm lùi giữ đơn: `By.id("qr-timer")`
* Số tiền trên QR: `By.id("qr-amount")`
* Mã nội dung chuyển khoản: `By.id("qr-memo")`
* Nút Đã chuyển khoản thành công: `//button[contains(., 'Đã Chuyển Khoản Thành Công')]`
* Nút Đóng modal: `//div[@id='checkout-modal']//button[contains(@class, 'fa-xmark') or i[contains(@class, 'fa-xmark')]]`

### 6. Mini Game Locators (`GamePage.java`)
* Bảng điểm: `By.id("game-score")`
* Dòng trạng thái: `By.id("game-status-text")`
* Nút Tạm Dừng/Tiếp Tục: `By.id("btn-start-game")`
* Nút Chơi Lại: `//button[contains(., 'Chơi Lại')]`
* 9 Ô bấm ma trận: `By.cssSelector("#game-grid button")`

---

## 📌 PHẦN VI. ĐẶC TẢ LỚP KIỂM THỬ CONTROLLER PHỤC VỤ JUNIT 5 (Y3 SPECIFICATION)

Để hoàn thành mục tiêu **Y3 (Unit Test cho Controller)** trong tuần 2 theo chuẩn mẫu giảng dạy của Thầy, sinh viên ánh xạ các nghiệp vụ trên thành các hàm Controller độc lập để viết `@ParameterizedTest` và `assertThrows()`:

```java
package com.poly.sof309.controllers;

public class AuthController {
    // Xác thực đăng nhập
    public boolean authenticate(String username, String password);
    
    // Đăng ký tài khoản mới với validation nghiêm ngặt
    public boolean register(String name, String email, String phone, String password) 
        throws IllegalArgumentException, DuplicateUserException;
}

public class CartController {
    // Thêm vào giỏ & kiểm tra số lượng
    public void addItem(Long productId, int quantity) 
        throws InvalidQuantityException, ProductNotFoundException;
        
    // Cập nhật số lượng
    public void updateQuantity(Long productId, int delta);
    
    // Áp dụng mã coupon
    public double applyCoupon(String couponCode, double subtotal) 
        throws InvalidCouponException;
        
    // Tính tổng tiền sau chiết khấu
    public double calculateTotal(double subtotal, int discountPercent);
}
```

---
> 💡 **Hướng dẫn tiếp theo dành cho AI RAG:**  
> Dựa trên tài liệu đặc tả chi tiết này, hãy sử dụng nội dung trên làm đầu vào (Prompt Context) để tự động sinh file `docs/TEST_PLAN.md` và `docs/TEST_CASES.md` cho Y2, sau đó triển khai toàn bộ mã nguồn Unit Test (Y3) và Selenium Page Object Model (Y4).
