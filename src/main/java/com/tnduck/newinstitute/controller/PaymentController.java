package com.tnduck.newinstitute.controller;


import com.tnduck.newinstitute.dto.response.ResponseObject;
import com.tnduck.newinstitute.dto.response.payment.PaymentDTO;
import com.tnduck.newinstitute.service.PaymentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author ductn
 * @project New-Institute
 * @created 29/05/2024
 */
@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "012. payment", description = "Payment API")
public class PaymentController {
    private final PaymentService paymentService;
    @GetMapping("/vn-pay")
    public ResponseObject<PaymentDTO.VNPayResponse> pay(HttpServletRequest request) {
        return new ResponseObject<>(HttpStatus.OK, "Success", paymentService.createVnPayPayment(request));
    }
    //http://localhost:8080/payment/vn-pay?amount=2415116&bankCode=NCB
    @GetMapping("/vn-pay-callback")
    public ResponseEntity<?> payCallbackHandler(HttpServletRequest request) {
        String status = request.getParameter("vnp_ResponseCode");
        if (status.equals("00")) {
            return ResponseEntity.status(HttpStatus.OK).body("Success");
        } else {
            return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY).body("Failure");
        }
    }
}
