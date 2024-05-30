# MEDISTOCK

Ứng dụng dùng cho quản lý kho thuốc và vật tư y tế (phiên bản 2 - đã nâng cấp)

#### Tác giả:

- Chu Thị Hồng Anh D53ATB9
- Nguyễn Hữu Nguyên D53ATB9

## Các cải tiến mới:

- Tách thuốc và vật tư y tế ra riêng
- Thời gian: Calendar
- Tìm kiếm theo đoạn thời gian
- Chuẩn hóa giá sản phẩm
- Thêm phần xuất kho
- Tách phần thống kê ra riêng và thêm 02 biểu đồ mới liên quan đến việc xuất kho

## Tóm tắt Tính năng:

- Xem thông tin thuốc, vật tư y tế
- Thêm thuốc, vật tư y tế vào kho
- Sửa thông tin thuốc, vật tư y tế
- Xuất thuốc, vật tư y tế khỏi kho
- Sắp xếp thuốc, vật tư y tế theo nhiều tiêu chí khác nhau
- Tìm kiếm thuốc trong kho:
  - Tìm kiếm chính xác các trường: ID, Tên sản phẩm, NSX, HSD, Nguồn nhập, số lô
  - Tìm kiếm trên đoạn các trường: Số lượng nhập, Giá sản phẩm, NSX, HSD
- Thống kê: cả trong kho và xuất kho
- Thống kê và trực quan hóa dưới dạng biểu đồ

## Cài đặt:

### Cài đặt môi trường:

- Cài đặt [JDK21](https://www.oracle.com/java/technologies/javase/jdk21-archive-downloads.html)
- Cài đặt [Apache Netbeans IDE 21](https://netbeans.apache.org/front/main/download/nb21/)

### Tải mã nguồn project:

- Link mã nguồn [MEDISTOCK_FINAL](https://github.com/userhanh/MEDISTOCK_FINAL.git)
- Clone project

  ```bash
      git clone https://github.com/userhanh/MEDISTOCK_FINAL.git
  ```

### Các cách chạy project:

#### Với file jar:

Click chuột vào file **App** để mở ứng dụng<br>
![Run](https://github.com/userhanh/MEDISTOCK_FINAL/blob/master/Images/1.jpg)

#### Với mã nguồn:

- Mở project trong Apache Netbeans
- Chạy ứng dụng bằng cách chạy file **MediStock.java**

## DEMO CHƯƠNG TRÌNH

#### Giao diện đăng nhập

Đăng nhập vào ứng dụng:

- username: 'a'
- password: 'a'<br>
  ![Login](https://github.com/userhanh/MEDISTOCK_FINAL/blob/master/Images/2.jpg)
- Đăng nhập thành công khi đúng tài khoản và mật khẩu trên, mở vào giao diện ứng dụng

#### Giao diện trang chủ

- Giao diện trang chủ có 03 tab: THÔNG TIN KHO, THÔNG TIN XUẤT, THỐNG KÊ
- Tab THÔNG TIN KHO GỒM:

  - 2 bảng biểu thị danh sách thuốc và vật tư y tế trong kho;
  - 1 bảng hiển thị thông tin thuốc và vật tư y tế
  - Bảng thuốc và vật tư y tế: một số trường bắt buộc, một số không
  - Các phương thức sắp xếp: hiển thị toàn bộ và sắp xếp theo nhiều tiêu chí khác nhau
  - Các phương thức tìm kiếm chính xác:
    - trả về đúng giá trị
    - có thể tìm kiếm có dấu/không dấu; không phân biệt hoa/thường
  - Các phương thức tìm kiếm theo đoạn: đã cải tiến thành trong đoạn [a,b]
  - Các phương thức: thêm thuốc, thêm vật tư y tế, sửa, xuất kho

![Home](https://github.com/userhanh/MEDISTOCK_FINAL/blob/master/Images/3.jpg)

- Click vào các hàng của bảng để trích thông tin ra bảng<br>
  ![Interact](https://github.com/userhanh/MEDISTOCK_FINAL/blob/master/Images/8.jpg)

#### Demo Tính năng tìm kiếm

- Tìm kiếm chính xác: Nhập trường tìm kiếm và nhấn nút "Tìm"<br>
  ![Search1](https://github.com/userhanh/MEDISTOCK_FINAL/blob/master/Images/4.jpg)<br>
  ![Search2](https://github.com/userhanh/MEDISTOCK_FINAL/blob/master/Images/5.jpg)<br>
- Tìm kiếm theo đoạn:<br>
  ![Search3](https://github.com/userhanh/MEDISTOCK_FINAL/blob/master/Images/6.jpg)<br>
  ![Search4](https://github.com/userhanh/MEDISTOCK_FINAL/blob/master/Images/7.jpg)<br>

#### Demo Chức năng thêm thuốc hoặc vật tư y tế

- Nhập các trường thông tin bắt buộc, các trường khác có thể nhập hoặc không<br>
  ![Add](https://github.com/userhanh/MEDISTOCK_FINAL/blob/master/Images/9.jpg)
- Yêu cầu: các trường thông tin phải hợp lệ, nếu không sẽ hiện ra thông báo, yêu cầu sửa lại
- Lựa chọn thêm thuốc hoặc vật tư y tế:<br>
  ![Choose1](https://github.com/userhanh/MEDISTOCK_FINAL/blob/master/Images/12.jpg)
- Nếu thỏa mãn tất cả các điều kiện sẽ hiện thông báo thành công, và thuốc sẽ được hiển thị ở bảng<br>
  ![Validate](https://github.com/userhanh/MEDISTOCK_FINAL/blob/master/Images/10.jpg)<br>
  ![Validate](https://github.com/userhanh/MEDISTOCK_FINAL/blob/master/Images/11.jpg)

#### Demo Tính năng sửa thông tin

- Click chọn hàng trong danh sách
- Thay đổi thông tin hiện ra ở các trường<br>
  ![Edit](https://github.com/userhanh/MEDISTOCK_FINAL/blob/master/Images/13.jpg)<br>
- Thông báo hiển thị thành công ứng với từng loại thay đổi (thuốc/vật tư y tế)<br>
  ![EditOK](https://github.com/userhanh/MEDISTOCK_FINAL/blob/master/Images/14.jpg)<br>
  ![EditOK](https://github.com/userhanh/MEDISTOCK_FINAL/blob/master/Images/15.jpg)<br>

#### Demo tính năng xuất kho

- Click chọn mặt hàng cần xuất<br>
  ![Exporting1](https://github.com/userhanh/MEDISTOCK_FINAL/blob/master/Images/17.jpg)<br>
- Click nút xuất<br>
  ![Exporting1](https://github.com/userhanh/MEDISTOCK_FINAL/blob/master/Images/18.jpg)<br>
- Thông tin về mặt hàng đã xuất sẽ hiển thị ở tab xuất kho<br>
  ![Exported](https://github.com/userhanh/MEDISTOCK_FINAL/blob/master/Images/19.jpg)<br>

### Tab xuất kho

- Khi chưa xuất kho tab hiển thị thông tin rỗng<br>
  ![NoExport](https://github.com/userhanh/MEDISTOCK_FINAL/blob/master/Images/16.jpg)<br>
- Khi đã xuất kho sẽ hiển thị thông tin về sản phẩm đã xuất với các trường: tên sản phẩm, loại sản phẩm, thời gian xuất, số lượng xuất, tổng hóa đơn khi xuất (giá trị mỗi sản phẩm \* số lượng)<br>
  ![Exported](https://github.com/userhanh/MEDISTOCK_FINAL/blob/master/Images/20.jpg)<br>
- Tính năng thống kê xuất kho:
  - Tổng số thuốc đã xuất, tổng số vật tư y tế đã xuất, tổng số sản phẩm đã xuất
  - Tổng tiền thuốc đã xuất, tổng tiền vật tư y tế đã xuất, tổng số tiền đã xuất được

### Tab thống kê: trực quan hóa thống kê bằng các biểu đồ

#### Có 06 biểu đồ về thống kê

![Chart](https://github.com/userhanh/MEDISTOCK_FINAL/blob/master/Images/27.jpg)<br>

- Biểu đồ thống kê số lượng thuốc theo tầm giá<br>
  ![Chart1](https://github.com/userhanh/MEDISTOCK_FINAL/blob/master/Images/21.jpg)<br>
- Biểu đồ thống kê số lượng vật tư y tế theo tầm giá<br>
  ![Chart2](https://github.com/userhanh/MEDISTOCK_FINAL/blob/master/Images/22.jpg)<br>
- Biểu đồ thống kê số lượng sản phẩm thuốc<br>
  ![Chart3](https://github.com/userhanh/MEDISTOCK_FINAL/blob/master/Images/24.jpg)<br>
- Biểu đồ thống kê số lượng sản phẩm vật tư y tế<br>
  ![Chart4](https://github.com/userhanh/MEDISTOCK_FINAL/blob/master/Images/23.jpg)<br>
- Biểu đồ thống kê số thuốc và vật tư y tế đã xuất<br>
  ![Chart5](https://github.com/userhanh/MEDISTOCK_FINAL/blob/master/Images/25.jpg)<br>
- Biểu đồ thống kê tiền thuốc và vật y tế đã xuất<br>
  ![Chart6](https://github.com/userhanh/MEDISTOCK_FINAL/blob/master/Images/26.jpg)<br>

Các biểu đồ đảm bảo tương ứng được với dữ liệu được cập nhất (đã kiểm tra tính cập nhật nhất thời).

##### Các mã nguồn mở được sử dụng

- jaxb-api
- jaxb ri
- jfreechart
- jcalendar
