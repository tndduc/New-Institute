package com.tnduck.newinstitute.service;

import com.tnduck.newinstitute.config.VnPayConstant;
import com.tnduck.newinstitute.dto.request.OrderRequestDTO;
import com.tnduck.newinstitute.util.VnPayHelper;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
public class OrderPaymentService {
    public Map<String, Object> createOrder(HttpServletRequest request, OrderRequestDTO orderRequestDTO) throws UnsupportedEncodingException {

        Map<String, Object> payload = new HashMap<>(){{
            put("vnp_Version", VnPayConstant.VNP_VERSION);
            put("vnp_Command", VnPayConstant.VNP_COMMAND_ORDER);
            put("vnp_TmnCode", VnPayConstant.VNP_TMN_CODE);
            put("vnp_Amount", String.valueOf(orderRequestDTO.getAmount() * 100));
            put("vnp_CurrCode", VnPayConstant.VNP_CURRENCY_CODE);
            put("vnp_TxnRef",  VnPayHelper.getRandomNumber(8));
            put("vnp_OrderInfo", orderRequestDTO.getOrderInfo());
            put("vnp_OrderType", VnPayConstant.ORDER_TYPE);
            put("vnp_Locale", VnPayConstant.VNP_LOCALE);
            put("vnp_ReturnUrl", VnPayConstant.VNP_RETURN_URL);
            put("vnp_IpAddr", VnPayHelper.getIpAddress(request));
            put("vnp_CreateDate", VnPayHelper.generateDate(false));
            put("vnp_ExpireDate", VnPayHelper.generateDate(true));
        }};

        String queryUrl = getQueryUrl(payload).get("queryUrl")
                + "&vnp_SecureHash="
                + VnPayHelper.hmacSHA512(VnPayConstant.SECRET_KEY, getQueryUrl(payload).get("hashData"));

        String paymentUrl = VnPayConstant.VNP_PAY_URL + "?" + queryUrl;
        payload.put("redirect_url", paymentUrl);

        return payload;
    }

    private Map<String, String> getQueryUrl(Map<String, Object> payload) throws UnsupportedEncodingException {

        List<String> fieldNames = new ArrayList(payload.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator itr = fieldNames.iterator();
        while (itr.hasNext()) {

            String fieldName = (String) itr.next();
            String fieldValue = (String) payload.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {

                //Build hash data
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));

                //Build query
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                if (itr.hasNext()) {

                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        return new HashMap<>(){{
            put("queryUrl", query.toString());
            put("hashData", hashData.toString());
        }};
    }
}
