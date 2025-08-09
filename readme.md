user story:

Màn đăng nhập tài khoản: duy nhất chức năng đăng nhập, lỗi nếu nhập sai tài khoản

Màn tổng quan, hiển thị ngay sau đăng nhập: yêu cầu hiển thị 3 card tổng quan tổng quan
- Số lượng bệnh nhân hiện tại đang nằm viện ( thông tin thêm: số lượng bệnh nhân nội trú/ngoại trú )
- chart hiển thị số lượng bệnh nhân nhập viện theo tháng trong năm 
- Doanh thu của bệnh viện tính từ đầu năm đến ngày hiện tại


Các màn nhập dữ liệu:

1. Tạo mới thông tin bệnh nhân
Luồng:
Người dùng chọn Tạo mới bệnh nhân từ menu.
Hệ thống hiển thị form nhập thông tin.
Người dùng nhập đầy đủ thông tin → Submit → gửi dữ liệu lên BE → lưu vào DB.
Form fields:
I. Thông tin cá nhân
Họ và tên bệnh nhân (text)
Tuổi (number, auto-calculate từ ngày sinh)
Giới tính (select: Nam / Nữ / Khác)
Ngày sinh (date)
Nhóm máu (select: A / B / AB / O / Không xác định)
Số CCCD / Hộ chiếu / CMND (text, unique)

II. Thông tin liên hệ
Số điện thoại (tel)
Email (email)
Địa chỉ (textarea)
Họ tên người giám hộ (text)
Số điện thoại người giám hộ (tel)
III. Thông tin khác
Ghi chú của bác sĩ (textarea)
Bảo hiểm y tế (checkbox Có/Không)
  → Nếu Có: nhập thông tin BHYT (mã số, ngày hết hạn, nơi đăng ký)
Tiền sử bệnh án (checkbox Có/Không)
  → Nếu Có: nhập danh sách bệnh từng mắc
Dịch vụ (radio: Nội trú / Ngoại trú)
  → Nếu chọn Nội trú: trạng thái xuất viện (radio: Đã xuất viện / Chưa xuất viện)
2. Truy vấn / Cập nhật thông tin bệnh nhân
Luồng:
Người dùng chọn Tra cứu/Cập nhật bệnh nhân.
Hệ thống hiển thị màn tra cứu:
Tra cứu theo CCCD (text input)
Sau khi nhập thông tin tra cứu → Search → BE trả về thông tin bệnh nhân.
FE hiển thị form thông tin bệnh nhân giống form tạo mới nhưng dữ liệu đã được điền sẵn.
Người dùng chỉnh sửa thông tin → Submit → BE cập nhật DB.
3. Nhập/Xuất dữ liệu
Xuất file: Chọn định dạng (xlsx, csv) → hệ thống xuất file theo format cố định.
Nhập file:
 1. Người dùng tải template file (xlsx) từ hệ thống.
 2. Điền dữ liệu theo format → Upload lại lên hệ thống.
 3. Hệ thống validate dữ liệu → Lưu vào DB nếu hợp lệ.
4. In Preview
Cho phép in thông tin bệnh nhân trực tiếp từ form hoặc màn chi tiết.


Tạo mới kết quả xét nghiệm lâm sàng và cận lâm sàng: Các yêu cầu về form như trên

| Tên trường          | Loại input | Ghi chú                         |
| ------------------- | ---------- | ------------------------------- |
| Lý do khám          | textarea   | Nhập mô tả ngắn                 |
| Triệu chứng chính   | textarea   |                                 |
| Tiền sử bệnh        | textarea   | Bệnh lý nền, dị ứng, phẫu thuật |
| Chiều cao (cm)      | number     | Min 30, max 250                 |
| Cân nặng (kg)       | number     | Min 1, max 250                  |
| Huyết áp (mmHg)     | text       | Ví dụ: "120/80"                 |
| Mạch (lần/phút)     | number     |                                 |
| Nhiệt độ (°C)       | number     | Step 0.1                        |
| Nhịp thở (lần/phút) | number     |                                 |
| Khám tổng quát      | textarea   | Mô tả toàn thân                 |
| Tim mạch            | textarea   |                                 |
| Hô hấp              | textarea   |                                 |
| Tiêu hóa            | textarea   |                                 |
| Thần kinh           | textarea   |                                 |
| Cơ xương khớp       | textarea   |                                 |
| Da liễu             | textarea   |                                 |
| Tai mũi họng        | textarea   |                                 |
| Mắt                 | textarea   |                                 |
| Chẩn đoán sơ bộ     | textarea   |                                 |
| Ghi chú bổ sung     | textarea   |                                 |

| Tên trường                         | Loại input  | Ghi chú                                                       |
| ---------------------------------- | ----------- | ------------------------------------------------------------- |
| Loại xét nghiệm/chẩn đoán hình ảnh | select      | Option: Xét nghiệm máu, Nước tiểu, X-quang, Siêu âm, MRI, CT… |
| Ngày thực hiện                     | date picker |                                                               |
| Chỉ định của bác sĩ                | textarea    |                                                               |
| Kết quả xét nghiệm/hình ảnh        | textarea    |                                                               |
| Kết luận cận lâm sàng              | textarea    |                                                               |




out of range:
quét qr bảo hiểm y tế/ căn cước -> nhận thông tin
tích hợp thanh toán trực tuyến và xuất file hóa đơn
hiển thị preview file sau khi nhập file xlsx, csv
