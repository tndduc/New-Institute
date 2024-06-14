//package com.tnduck.newinstitute.service;
//
//import com.tnduck.newinstitute.config.VNPAYConfig;
//import com.tnduck.newinstitute.dto.response.payment.PaymentDTO;
//import com.tnduck.newinstitute.util.VNPayUtil;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.transaction.Transactional;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Service;
//
//import java.io.UnsupportedEncodingException;
//import java.net.URLEncoder;
//import java.nio.charset.StandardCharsets;
//import java.text.SimpleDateFormat;
//import java.util.*;
//
///**
// * @author ductn
// * @project New-Institute
// * @created 29/05/2024
// */
//@Service
//@RequiredArgsConstructor
//@Transactional
//@Slf4j
//public class PaymentService {
//    private final VNPAYConfig vnPayConfig;
//    public PaymentDTO.VNPayResponse createVnPayPayment(HttpServletRequest request) {
//        long amount = Integer.parseInt(request.getParameter("amount")) * 100L;
//        String bankCode = request.getParameter("bankCode");
//        Map<String, String> vnpParamsMap = vnPayConfig.getVNPayConfig();
//        vnpParamsMap.put("vnp_Amount", String.valueOf(amount));
//        if (bankCode != null && !bankCode.isEmpty()) {
//            vnpParamsMap.put("vnp_BankCode", bankCode);
//        }
//        vnpParamsMap.put("vnp_IpAddr", VNPayUtil.getIpAddress(request));
//        //build query url
//        String queryUrl = VNPayUtil.getPaymentURL(vnpParamsMap, true);
//        String hashData = VNPayUtil.getPaymentURL(vnpParamsMap, false);
//        String vnpSecureHash = VNPayUtil.hmacSHA512(vnPayConfig.getSecretKey(), hashData);
//        queryUrl += "&vnp_SecureHash=" + vnpSecureHash;
//        String paymentUrl = vnPayConfig.getVnp_PayUrl() + "?" + queryUrl;
//        return PaymentDTO.VNPayResponse.builder()
//                .code("ok")
//                .message("success")
//                .paymentUrl(paymentUrl).build();
//    }
//}
