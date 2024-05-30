package com.tnduck.newinstitute.controller;

import com.tnduck.newinstitute.config.VNPayConfig;
import com.tnduck.newinstitute.service.PaymentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    @GetMapping("/create_payment")
    public ResponseEntity<?>  createPayment() throws UnsupportedEncodingException {
        return paymentService.createPayment();
    }
}
