# Automation Testing Framework – E-Commerce Demo

## Tổng quan

Project automation test cho website thương mại điện tử **SauceDemo** (saucedemo.com).
Áp dụng Page Object Model (POM), Data-Driven Testing, ExtentReport và CI/CD GitHub Actions.

## Công nghệ sử dụng

| Thành phần | Công nghệ | Phiên bản |
|-----------|----------|----------|
| Ngôn ngữ | Java | 17 |
| Automation | Selenium WebDriver | 4.21.0 |
| Test Runner | TestNG | 7.10.2 |
| Build Tool | Maven | 3.9+ |
| Driver Manager | WebDriverManager | 5.8.0 |
| Report | ExtentReport | 5.1.2 |
| Data-Driven | Apache POI | 5.2.5 |
| CI/CD | GitHub Actions | – |

## Cấu trúc project

```
automation-ecommerce-demo/
├── src/test/java/com/demo/
│   ├── base/
│   │   ├── BaseTest.java          # Khởi tạo WebDriver, config
│   │   └── BasePage.java          # WebDriverWait, common methods
│   ├── pages/                     # Page Object Model
│   │   ├── LoginPage.java
│   │   ├── HomePage.java
│   │   ├── ProductPage.java
│   │   └── CartPage.java
│   ├── tests/                     # Test classes
│   │   ├── LoginTest.java         # TC01 – TC04
│   │   ├── ProductTest.java       # TC05 – TC08
│   │   └── CartTest.java          # TC09 – TC12
│   ├── data/
│   │   └── TestDataProvider.java  # Data-Driven provider
│   ├── utils/
│   │   ├── ConfigReader.java      # Đọc file config.properties
│   │   ├── ScreenshotUtil.java    # Chụp ảnh khi test fail
│   │   ├── ExtentReportManager.java
│   │   └── ExcelUtil.java         # Đọc file Excel test data
│   └── listeners/
│       └── TestListener.java      # TestNG Listener
├── src/test/resources/
│   └── config.properties
├── testng.xml                     # Test suite
├── pom.xml
├── .github/workflows/maven-test.yml
└── README.md
```

## Test Case

### Login Module

| ID | Test Case | Loại | Mô tả |
|----|-----------|------|-------|
| TC01 | Login hợp lệ | Positive | standard_user + secret_sauce |
| TC02 | Locked user | Negative | locked_out_user |
| TC03 | Sai password | Negative | standard_user + sai pass |
| TC04 | Để trống | Negative | Không nhập user/pass |

### Product Module

| ID | Test Case | Loại | Mô tả |
|----|-----------|------|-------|
| TC05 | Sắp xếp giá thấp→cao | Functional | Chọn option Price (low to high) |
| TC06 | Sắp xếp tên Z→A | Functional | Chọn option Name (Z to A) |
| TC07 | Xem chi tiết sản phẩm | Functional | Click sản phẩm, kiểm tra thông tin |
| TC08 | Quay lại danh sách | Functional | Back to products |

### Cart Module

| ID | Test Case | Loại | Mô tả |
|----|-----------|------|-------|
| TC09 | Thêm 1 sản phẩm vào giỏ | Functional | Add to cart, kiểm tra badge |
| TC10 | Thêm nhiều sản phẩm | Functional | Add 3 sản phẩm, kiểm tra số lượng |
| TC11 | Xóa sản phẩm khỏi giỏ | Functional | Remove, kiểm tra giỏ trống |
| TC12 | Checkout flow | Functional | Điền form, hoàn tất đơn hàng |

## Cách chạy

### Yêu cầu
- JDK 17+
- Maven 3.9+
- Google Chrome (đã cài)

### Chạy toàn bộ suite
```bash
mvn clean test
```

### Chạy từng module
```bash
mvn clean test -DsuiteXmlFile=testng.xml
```

### Chạy headless (CI/CD)
```bash
mvn clean test -Dheadless=true
```

### Xem report
Mở file `test-output/ExtentReport.html` trong browser.

## Report mẫu

Sau khi chạy test, ExtentReport tự sinh HTML report tại `test-output/` với:
- Pie chart pass/fail/skip
- Timeline từng test
- Screenshot tự động đính kèm test fail
- Log chi tiết từng bước

## CI/CD

Project tích hợp GitHub Actions, tự động chạy test khi push lên main hoặc tạo PR.
Xem file `.github/workflows/maven-test.yml`.
**
