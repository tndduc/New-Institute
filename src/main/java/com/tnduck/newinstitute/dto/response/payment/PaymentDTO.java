package com.tnduck.newinstitute.dto.response.payment;

/**
 * @author ductn
 * @project New-Institute
 * @created 29/05/2024
 */
import lombok.Builder;

public abstract class PaymentDTO {
    @Builder
    public static class VNPayResponse {
        public String code;
        public String message;
        public String paymentUrl;
    }
}
