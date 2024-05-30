package com.tnduck.newinstitute.dto.response.payment;

import com.tnduck.newinstitute.dto.response.AbstractBaseResponse;
import com.tnduck.newinstitute.dto.response.question.QuestionResponse;
import com.tnduck.newinstitute.entity.Question;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

/**
 * @author ductn
 * @project New-Institute
 * @created 29/05/2024
 */
@Getter
@Setter
//@SuperBuilder
public class PaymentResponse implements Serializable{
    private String status;
    private String message;
    private String URL;
//    public static PaymentResponse convert(PaymentResponse paymentResponse){
//        return PaymentResponse.builder()
//                .message(paymentResponse.message)
//                .status(paymentResponse.status)
//                .URL(paymentResponse.URL)
//                .build();
//    }
}
