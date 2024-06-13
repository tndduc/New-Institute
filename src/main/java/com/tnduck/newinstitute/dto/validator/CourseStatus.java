package com.tnduck.newinstitute.dto.validator;

/**
 * @author ductn
 * @project New-Institute
 * @created 20/05/2024
 */
public enum CourseStatus {
    PAYMENT_PENDING,
    APPROVED,
    REJECTED,
    CANCELLED,
    COMPLETED,
    IN_PROGRESS,
    ON_HOLD,
    OVERDUE,
    CERTIFIED,
    WAITLIST,
    ON_CART
}
/*
*
* trạng thái chung:

PENDING: Đăng ký đang chờ xử lý (chưa được phê duyệt).
APPROVED: Đăng ký đã được phê duyệt.
REJECTED: Đăng ký đã bị từ chối.
CANCELLED: Đăng ký đã bị hủy bỏ.
COMPLETED: Đăng ký đã hoàn thành (khóa học đã kết thúc).
Trạng thái chi tiết hơn (tùy chọn):

IN_PROGRESS: Học viên đang học khóa học.
ON_HOLD: Khóa học tạm dừng (ví dụ: do học viên nghỉ học).
OVERDUE: Học viên chưa hoàn thành khóa học đúng hạn.
CERTIFIED: Học viên đã hoàn thành khóa học và nhận được chứng chỉ.
Lựa chọn trạng thái phù hợp:

Việc lựa chọn các trạng thái cụ thể phụ thuộc vào quy trình và yêu cầu của hệ thống của bạn. Bạn có thể thêm các trạng thái khác phù hợp với nhu cầu riêng, chẳng hạn như:

WAITLIST: Học viên đang trong danh sách chờ (nếu lớp học có hạn quota).
PAYMENT_PENDING: Đăng ký đang chờ thanh toán.

*
*
*
*
*
* */