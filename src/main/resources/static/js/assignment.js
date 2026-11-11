// ==================== 1. DỮ LIỆU SẢN PHẨM MÔ TÔ ====================
const productsData = [
    { id: 1, name: "Kawasaki Ninja ZX-10R", year: 2023, odo: "3,500 KM", price: 571000000, oldPrice: 620000000, isSale: true, discount: "GIẢM 8%", category: "Sportbike", brand: "Kawasaki", cc: "998 CC", gear: "Hộp số thủ công (6 cấp)", image: "https://images.unsplash.com/photo-1568772585407-9361f9bf3a87?q=80&w=600", images: ["https://images.unsplash.com/photo-1568772585407-9361f9bf3a87?q=80&w=600", "https://images.unsplash.com/photo-1558981806-ec527fa84c39?q=80&w=600"], desc: "Kawasaki Ninja ZX-10R trang bị động cơ 998cc 4 xi-lanh." },
    { id: 2, name: "BMW S1000RR 2024", year: 2024, odo: "1,200 KM", price: 949000000, oldPrice: 990000000, isSale: true, discount: "GIẢM 5%", category: "Sportbike", brand: "BMW", cc: "999 CC", gear: "Hộp số thủ công (6 cấp)", image: "https://images.unsplash.com/photo-1558981806-ec527fa84c39?q=80&w=600", images: ["https://images.unsplash.com/photo-1558981806-ec527fa84c39?q=80&w=600"], desc: "Siêu xe thể thao đỉnh cao từ Đức với công nghệ ShiftCam 999cc." },
    { id: 3, name: "Ducati Streetfighter V4", year: 2022, odo: "5,800 KM", price: 650000000, oldPrice: 710000000, isSale: true, discount: "GIẢM 9%", category: "Nakedbike", brand: "Ducati", cc: "1103 CC", gear: "Hộp số thủ công (6 cấp)", image: "https://images.unsplash.com/photo-1609630875171-b1321377ee65?q=80&w=600", images: ["https://images.unsplash.com/photo-1609630875171-b1321377ee65?q=80&w=600"], desc: "Nakedbike siêu mạnh mẽ động cơ Desmosedici Stradale V4 1103cc." },
    { id: 4, name: "Yamaha YZF-R1M", year: 2023, odo: "2,100 KM", price: 860000000, oldPrice: 910000000, isSale: true, discount: "GIẢM 6%", category: "Sportbike", brand: "Yamaha", cc: "998 CC", gear: "Hộp số thủ công (6 cấp)", image: "https://images.unsplash.com/photo-1558981403-c5f9899a28bc?q=80&w=600", images: ["https://images.unsplash.com/photo-1558981403-c5f9899a28bc?q=80&w=600"], desc: "Phiên bản giới hạn YZF-R1M dàn áo sợi Carbon, phuộc điện Öhlins ERS." },
    { id: 5, name: "BMW R1250 GS Adventure", year: 2021, odo: "12,000 KM", price: 704000000, category: "Adventure", brand: "BMW", cc: "1254 CC", gear: "Hộp số thủ công (6 cấp)", image: "https://images.unsplash.com/photo-1591637333184-19aa84b3e01f?q=80&w=600", images: ["https://images.unsplash.com/photo-1591637333184-19aa84b3e01f?q=80&w=600"], desc: "Vua đường trường Adventure động cơ Boxer 1254cc ShiftCam." },
    { id: 6, name: "Ducati Scrambler Icon", year: 2022, odo: "6,500 KM", price: 425000000, category: "Classic", brand: "Ducati", cc: "803 CC", gear: "Hộp số thủ công (6 cấp)", image: "https://images.unsplash.com/photo-1534093607318-f025413f49cb?q=80&w=600", images: ["https://images.unsplash.com/photo-1534093607318-f025413f49cb?q=80&w=600"], desc: "Cổ điển kết hợp hiện đại, động cơ L-Twin 803cc." },
    { id: 7, name: "Honda CBR1000RR-R SP", year: 2023, odo: "1,800 KM", price: 1050000000, category: "Sportbike", brand: "Honda", cc: "1000 CC", gear: "Hộp số thủ công (6 cấp)", image: "https://images.unsplash.com/photo-1568772585407-9361f9bf3a87?q=80&w=600", images: ["https://images.unsplash.com/photo-1568772585407-9361f9bf3a87?q=80&w=600"], desc: "Siêu xe đua đường phố đỉnh cao của Honda với động cơ 4 xi-lanh." },
    { id: 8, name: "Kawasaki Z1000 ABS", year: 2022, odo: "8,900 KM", price: 412000000, category: "Nakedbike", brand: "Kawasaki", cc: "1043 CC", gear: "Hộp số thủ công (6 cấp)", image: "https://images.unsplash.com/photo-1609630875171-b1321377ee65?q=80&w=600", images: ["https://images.unsplash.com/photo-1609630875171-b1321377ee65?q=80&w=600"], desc: "Huyền thoại Nakedbike Sugomi thiết kế hầm hố." },
    { id: 9, name: "Yamaha MT-09 2024", year: 2024, odo: "800 KM", price: 345000000, category: "Nakedbike", brand: "Yamaha", cc: "890 CC", gear: "Hộp số thủ công (6 cấp)", image: "https://images.unsplash.com/photo-1558981403-c5f9899a28bc?q=80&w=600", images: ["https://images.unsplash.com/photo-1558981403-c5f9899a28bc?q=80&w=600"], desc: "Động cơ CP3 3 xi-lanh 890cc mô-men xoắn tức thì." },
    { id: 10, name: "BMW F850 GS Adventure", year: 2022, odo: "7,400 KM", price: 600000000, category: "Adventure", brand: "BMW", cc: "853 CC", gear: "Hộp số thủ công (6 cấp)", image: "https://images.unsplash.com/photo-1591637333184-19aa84b3e01f?q=80&w=600", images: ["https://images.unsplash.com/photo-1591637333184-19aa84b3e01f?q=80&w=600"], desc: "Mẫu Adventure tầm trung hoàn hảo trang bị động cơ 2 xi-lanh." },
    { id: 11, name: "Aprilia RSV4 Factory 1100", year: 2023, odo: "2,900 KM", price: 885000000, category: "Sportbike", brand: "Aprilia", cc: "1099 CC", gear: "Hộp số thủ công (6 cấp)", image: "https://images.unsplash.com/photo-1558981806-ec527fa84c39?q=80&w=600", images: ["https://images.unsplash.com/photo-1558981806-ec527fa84c39?q=80&w=600"], desc: "Tuyệt tác Superbike từ Ý trang bị động cơ V4 1099cc." },
    { id: 12, name: "KTM 1290 Super Duke R", year: 2023, odo: "4,100 KM", price: 750000000, category: "Nakedbike", brand: "KTM", cc: "1301 CC", gear: "Hộp số thủ công (6 cấp)", image: "https://images.unsplash.com/photo-1609630875171-b1321377ee65?q=80&w=600", images: ["https://images.unsplash.com/photo-1609630875171-b1321377ee65?q=80&w=600"], desc: "Mãnh thú Quái vật trang bị động cơ V-Twin LC8 1301cc." }
];

const mockUser = { account: "admin@gmail.com", password: "123456", fullname: "Nguyễn Văn Admin" };

// Mảng Giỏ Hàng & Yêu thích toàn cục
let cart = [];
let wishlistCount = 0;

function formatCurrency(amount) {
    return 'VND ' + amount.toLocaleString('vi-VN');
}

// ==================== 2. QUẢN LÝ GIỎ HÀNG THỰC TẾ ====================
function addToCart(productId) {
    const product = productsData.find(p => p.id === productId);
    if (!product) return;

    const existingItem = cart.find(item => item.id === productId);
    if (existingItem) {
        existingItem.quantity += 1;
    } else {
        cart.push({ ...product, quantity: 1 });
    }

    updateCartUI();
    alert(`Đã thêm "${product.name}" vào giỏ hàng!`);
}

function increaseQuantity(productId) {
    const item = cart.find(i => i.id === productId);
    if (item) {
        item.quantity += 1;
        updateCartUI();
    }
}

function decreaseQuantity(productId) {
    const item = cart.find(i => i.id === productId);
    if (item) {
        item.quantity -= 1;
        if (item.quantity <= 0) {
            removeFromCart(productId);
        } else {
            updateCartUI();
        }
    }
}

function removeFromCart(productId) {
    cart = cart.filter(item => item.id !== productId);
    updateCartUI();
}

function clearCart() {
    if (cart.length === 0) return;
    if (confirm("Bạn có chắc chắn muốn xóa tất cả sản phẩm khỏi giỏ hàng?")) {
        cart = [];
        updateCartUI();
    }
}

function updateCartUI() {
    const totalCount = cart.reduce((sum, item) => sum + item.quantity, 0);
    document.getElementById('cart-count').innerText = totalCount;

    const cartTableBody = document.getElementById('cart-table-body');
    if (!cartTableBody) return;

    if (cart.length === 0) {
        cartTableBody.innerHTML = `<tr><td colspan="6" style="text-align:center; padding: 30px; color: #888;">Giỏ hàng của bạn đang trống!</td></tr>`;
        document.getElementById('cart-total-price').innerText = 'VND 0';
        return;
    }

    let totalPrice = 0;
    cartTableBody.innerHTML = cart.map(item => {
        const itemSubtotal = item.price * item.quantity;
        totalPrice += itemSubtotal;

        return `
            <tr>
                <td><img src="${item.image}" class="cart-item-img" alt="${item.name}"></td>
                <td><strong>${item.name}</strong></td>
                <td>${formatCurrency(item.price)}</td>
                <td>
                    <div class="quantity-control">
                        <button class="btn-qty" onclick="decreaseQuantity(${item.id})">-</button>
                        <span class="qty-number">${item.quantity}</span>
                        <button class="btn-qty" onclick="increaseQuantity(${item.id})">+</button>
                    </div>
                </td>
                <td><strong>${formatCurrency(itemSubtotal)}</strong></td>
                <td>
                    <button class="btn-delete-item" onclick="removeFromCart(${item.id})" title="Xóa"><i class="fa-solid fa-trash-can"></i></button>
                </td>
            </tr>
        `;
    }).join('');

    document.getElementById('cart-total-price').innerText = formatCurrency(totalPrice);
}

function checkoutCart() {
    if (cart.length === 0) {
        alert("Giỏ hàng đang trống! Vui lòng chọn mua sản phẩm.");
        return;
    }
    alert("Cảm ơn bạn đã đặt hàng! Nhân viên tư vấn sẽ liên hệ để xác nhận đơn hàng sớm nhất.");
    cart = [];
    updateCartUI();
}

// ==================== 3. TRÒ CHƠI HÁI TÁO TRÁNH BOM ====================
let gameScore = 0;
let gameInterval = null;
let activeItemIndex = null;
let isGameRunning = false;
let discountApplied = false;

function startGame() {
    gameScore = 0;
    discountApplied = false;
    isGameRunning = true;
    document.getElementById('game-score').innerText = gameScore;
    document.getElementById('game-notification').innerText = '';
    document.getElementById('game-start-btn').innerText = 'Đang Chơi...';

    if (gameInterval) clearInterval(gameInterval);

    gameInterval = setInterval(() => {
        const cells = document.querySelectorAll('.game-cell');
        cells.forEach(cell => cell.innerHTML = '');

        activeItemIndex = Math.floor(Math.random() * 9);
        const isApple = Math.random() < 0.7;
        cells[activeItemIndex].innerHTML = isApple ? '🍎' : '💣';
        cells[activeItemIndex].dataset.type = isApple ? 'apple' : 'bomb';
    }, 1000);
}

function cellClicked(index) {
    if (!isGameRunning) return;
    const cells = document.querySelectorAll('.game-cell');
    const cell = cells[index];

    if (index === activeItemIndex && cell.innerHTML !== '') {
        const type = cell.dataset.type;
        if (type === 'apple') {
            gameScore += 10;
            cell.innerHTML = '✨';
        } else if (type === 'bomb') {
            gameScore -= 20;
            if (gameScore < 0) gameScore = 0;
            cell.innerHTML = '💥';
        }
        document.getElementById('game-score').innerText = gameScore;
        cell.dataset.type = '';
        activeItemIndex = null;

        if (gameScore >= 100 && !discountApplied) {
            discountApplied = true;
            isGameRunning = false;
            clearInterval(gameInterval);
            cells.forEach(c => c.innerHTML = '');
            document.getElementById('game-start-btn').innerText = 'Chơi Lại';
            document.getElementById('game-notification').innerHTML = '🎉 Chúc mừng! Bạn đạt 100 điểm và nhận mã giảm 10%: <strong style="color:#d90429">MOTO10</strong>';
        }
    }
}

// ==================== 4. RENDER SẢN PHẨM & MÃ GIẢM GIÁ ====================
function renderProductCard(product) {
    const oldPriceHTML = product.oldPrice ? `<span class="old-price">${formatCurrency(product.oldPrice)}</span>` : '';
    const discountTagHTML = product.discount ? `<div class="discount-tag">${product.discount}</div>` : '';

    return `
        <div class="product-card">
            ${discountTagHTML}
            <img class="product-img" src="${product.image}" alt="${product.name}">
            <div class="product-info">
                <span class="product-category">${product.brand} - ${product.category}</span>
                <h3 class="product-name">${product.name}</h3>
                <p class="product-price">${oldPriceHTML} ${formatCurrency(product.price)}</p>
                <div class="product-actions">
                    <button class="btn btn-detail" onclick="viewDetailPage(${product.id})">Chi Tiết</button>
                    <button class="btn btn-cart" onclick="addToCart(${product.id})">Thêm Giỏ</button>
                </div>
            </div>
        </div>
    `;
}

function renderAllPages() {
    const saleProducts = productsData.filter(p => p.isSale);
    document.getElementById('sale-product-list').innerHTML = saleProducts.map(renderProductCard).join('');
    document.getElementById('home-product-list').innerHTML = productsData.slice(0, 8).map(renderProductCard).join('');
    renderCategoryProducts(productsData);
}

function applyCoupon(originalPrice, productId) {
    const couponInput = document.getElementById('coupon-code-input').value.trim().toUpperCase();
    if (couponInput === 'MOTO10') {
        const discountedPrice = originalPrice * 0.90;
        document.getElementById('detail-display-price').innerHTML = `
            <span class="old-price" style="font-size:18px">${formatCurrency(originalPrice)}</span>
            ${formatCurrency(discountedPrice)} <span style="font-size:12px; color:green;">(-10%)</span>
        `;
        alert("Áp dụng mã giảm giá 10% thành công!");
    } else {
        alert("Mã giảm giá không hợp lệ!");
    }
}

// ==================== 5. CHUYỂN TRANG & CÁC TÍNH NĂNG KHÁC ====================
function switchPage(pageId) {
    if (gameInterval && pageId !== 'game-page') {
        clearInterval(gameInterval);
        isGameRunning = false;
    }
    document.querySelectorAll('.page-section').forEach(section => section.classList.remove('active-page'));
    document.getElementById(pageId).classList.add('active-page');

    document.getElementById('nav-home').classList.toggle('active', pageId === 'home-page');
    document.getElementById('nav-category').classList.toggle('active', pageId === 'category-page');
    document.getElementById('nav-register').classList.toggle('active', pageId === 'register-page');
    document.getElementById('nav-login').classList.toggle('active', pageId === 'login-page');

    if (pageId === 'cart-page') updateCartUI();

    window.scrollTo({ top: 0, behavior: 'smooth' });
}

function scrollToFeatured() {
    const featuredSection = document.getElementById('featured-products');
    if (featuredSection) featuredSection.scrollIntoView({ behavior: 'smooth', block: 'start' });
}

function viewDetailPage(id) {
    const product = productsData.find(p => p.id === id);
    if (!product) return;

    const detailContainer = document.getElementById('detail-content');
    const imagesList = product.images || [product.image];

    const thumbnailsHTML = imagesList.map((img, idx) => `
        <div class="thumb-item ${idx === 0 ? 'active' : ''}" onclick="changeMainImage('${img}', this)">
            <img src="${img}" alt="${product.name}">
        </div>
    `).join('');

    detailContainer.innerHTML = `
        <div class="pro-detail-main">
            <div class="gallery-box">
                <div class="main-img-wrap">
                    <img id="main-detail-img" src="${imagesList[0]}" alt="${product.name}">
                </div>
                <div class="thumb-list">${thumbnailsHTML}</div>
            </div>

            <div class="info-box">
                <h1 class="pro-title">${product.year || '2023'} ${product.name}</h1>
                
                <div class="spec-badges">
                    <span class="badge-chip">${product.odo || '3,000 KM'}</span>
                    <span class="badge-chip">${product.category}</span>
                    <span class="badge-chip">${product.gear || 'Hộp số thủ công'}</span>
                    <span class="badge-chip">${product.cc || '1000 CC'}</span>
                </div>

                <div class="price-box" id="detail-display-price">
                    ${product.oldPrice ? `<span class="old-price" style="font-size:18px">${formatCurrency(product.oldPrice)}</span> ` : ''}
                    ${formatCurrency(product.price)}
                </div>

                <div class="coupon-action-row">
                    <input type="text" id="coupon-code-input" class="coupon-input" placeholder="Nhập mã giảm giá...">
                    <button class="btn-apply-coupon" onclick="applyCoupon(${product.price}, ${product.id})">Áp dụng mã</button>
                </div>

                <div class="action-group">
                    <button class="btn-icon-square" title="Yêu thích" onclick="addToWishlist('${product.name}')"><i class="fa-regular fa-heart"></i></button>
                    <button class="btn-buy-now" onclick="addToCart(${product.id}); switchPage('cart-page');">Đặt bây giờ</button>
                </div>
            </div>
        </div>
    `;

    switchPage('detail-page');
}

function changeMainImage(imgUrl, element) {
    document.getElementById('main-detail-img').src = imgUrl;
    document.querySelectorAll('.thumb-item').forEach(item => item.classList.remove('active'));
    element.classList.add('active');
}

function startCountdownTimer() {
    const targetDate = new Date().getTime() + (2 * 24 * 60 * 60 * 1000);
    setInterval(() => {
        const distance = targetDate - new Date().getTime();
        if (distance < 0) return;
        document.getElementById('days').innerText = Math.floor(distance / (1000 * 60 * 60 * 24));
        document.getElementById('hours').innerText = Math.floor((distance % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
        document.getElementById('minutes').innerText = Math.floor((distance % (1000 * 60 * 60)) / (1000 * 60));
        document.getElementById('seconds').innerText = Math.floor((distance % (1000 * 60)) / 1000);
    }, 1000);
}

function validateRegisterForm(e) {
    e.preventDefault();
    const newUser = { account: document.getElementById('email').value.trim(), password: document.getElementById('password').value, fullname: document.getElementById('fullname').value.trim() };
    localStorage.setItem('registeredUser', JSON.stringify(newUser));
    document.getElementById('register-alert').style.display = 'block';
    setTimeout(() => switchPage('login-page'), 1500);
}

function validateLoginForm(e) {
    e.preventDefault();
    const accountInput = document.getElementById('login-account').value.trim();
    const passwordInput = document.getElementById('login-password').value;
    const storedUser = JSON.parse(localStorage.getItem('registeredUser')) || mockUser;

    if ((accountInput === storedUser.account || accountInput === mockUser.account) && (passwordInput === storedUser.password || passwordInput === mockUser.password)) {
        document.getElementById('login-alert-success').style.display = 'block';
        localStorage.setItem('loggedInUser', JSON.stringify({ fullname: storedUser.fullname, account: accountInput }));
        setTimeout(() => { checkLoginState(); switchPage('home-page'); }, 1200);
    } else {
        document.getElementById('login-alert-error').style.display = 'block';
    }
}

function checkLoginState() {
    const loggedInUser = JSON.parse(localStorage.getItem('loggedInUser'));
    if (loggedInUser) {
        document.getElementById('nav-register').style.display = 'none';
        document.getElementById('nav-login').style.display = 'none';
        document.getElementById('user-info').style.display = 'flex';
        document.getElementById('user-display-name').innerText = loggedInUser.fullname;
    } else {
        document.getElementById('nav-register').style.display = 'inline-block';
        document.getElementById('nav-login').style.display = 'inline-block';
        document.getElementById('user-info').style.display = 'none';
    }
}

function handleLogout() {
    localStorage.removeItem('loggedInUser');
    checkLoginState();
    switchPage('home-page');
}

function renderCategoryProducts(products) {
    const categoryContainer = document.getElementById('category-product-list');
    categoryContainer.innerHTML = products.length === 0 ? `<div class="no-result">Không tìm thấy xe phù hợp!</div>` : products.map(renderProductCard).join('');
}

function filterAndSortProducts() {
    const searchValue = document.getElementById('search-input').value.toLowerCase().trim();
    const categoryValue = document.getElementById('filter-category').value;
    const brandValue = document.getElementById('filter-brand').value;
    const sortValue = document.getElementById('sort-price').value;

    let filtered = productsData.filter(p => (p.name.toLowerCase().includes(searchValue)) && (categoryValue === 'all' || p.category === categoryValue) && (brandValue === 'all' || p.brand === brandValue));
    if (sortValue === 'asc') filtered.sort((a, b) => a.price - b.price);
    else if (sortValue === 'desc') filtered.sort((a, b) => b.price - a.price);

    renderCategoryProducts(filtered);
}

function addToWishlist(productName) {
    wishlistCount++;
    document.getElementById('wishlist-count').innerText = wishlistCount;
    alert(`Đã thêm "${productName}" vào danh sách yêu thích!`);
}

let currentSlide = 0;
const slides = document.querySelectorAll('.slide');
function showSlide(index) {
    slides.forEach(s => s.classList.remove('active'));
    if (index >= slides.length) currentSlide = 0;
    if (index < 0) currentSlide = slides.length - 1;
    slides[currentSlide].classList.add('active');
}

document.addEventListener('DOMContentLoaded', () => {
    renderAllPages();
    checkLoginState();
    startCountdownTimer();

    document.getElementById('register-form').addEventListener('submit', validateRegisterForm);
    document.getElementById('login-form').addEventListener('submit', validateLoginForm);

    document.getElementById('search-input').addEventListener('input', filterAndSortProducts);
    document.getElementById('filter-category').addEventListener('change', filterAndSortProducts);
    document.getElementById('filter-brand').addEventListener('change', filterAndSortProducts);
    document.getElementById('sort-price').addEventListener('change', filterAndSortProducts);

    document.getElementById('prev-slide-btn').addEventListener('click', () => { currentSlide--; showSlide(currentSlide); });
    document.getElementById('next-slide-btn').addEventListener('click', () => { currentSlide++; showSlide(currentSlide); });
    setInterval(() => { currentSlide++; showSlide(currentSlide); }, 4000);

    document.getElementById('dark-mode-toggle').addEventListener('click', () => {
        document.body.classList.toggle('dark-mode');
    });

    const backToTopBtn = document.getElementById('back-to-top');
    window.onscroll = () => { backToTopBtn.style.display = document.documentElement.scrollTop > 300 ? 'block' : 'none'; };
    backToTopBtn.addEventListener('click', () => window.scrollTo({ top: 0, behavior: 'smooth' }));
});