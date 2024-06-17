package com.tnduck.newinstitute.controller;


import com.tnduck.newinstitute.dto.request.OrderRequestDTO;
import com.tnduck.newinstitute.dto.response.ResponseObject;
import com.tnduck.newinstitute.dto.response.payment.PaymentDTO;
import com.tnduck.newinstitute.service.EnrollmentService;
import com.tnduck.newinstitute.service.GetPaymentStatusService;
import com.tnduck.newinstitute.service.OrderPaymentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
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
@Slf4j
@Tag(name = "013. payment", description = "Payment API")
public class PaymentController {
    @Autowired
    private GetPaymentStatusService getPaymentStatusService;
    @Autowired
    private OrderPaymentService orderPaymentService;

    @Autowired
    private EnrollmentService enrollmentService;
    @PostMapping("/create-order")
    public ResponseEntity<Map<String, Object>> createOrderPayment(HttpServletRequest request, @RequestBody OrderRequestDTO orderRequestDTO) throws IOException {

        Map<String, Object> result = this.orderPaymentService.createOrder(request, orderRequestDTO);
        return new ResponseEntity<>(result, HttpStatus.OK);

    }
    //http://localhost:8080/callback?vnp_amount=2415116&bankCode=NCB
    @GetMapping("/callback")
    public ResponseEntity<Map<String, Object>> doCallBack(@RequestParam Map<String, Object> callBackInfo,
                                                          HttpServletResponse response) throws IOException{
        System.out.println(callBackInfo);
        this.enrollmentService.confirmPayment(callBackInfo);
        response.sendRedirect("http://localhost:3000/");
        return new ResponseEntity<>(new HashMap<>(), HttpStatus.OK);
    }
}
