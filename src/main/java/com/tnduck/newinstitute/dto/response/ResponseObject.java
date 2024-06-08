package com.tnduck.newinstitute.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

/**
 * @author ductn
 * @project New-Institute
 * @created 03/06/2024
 */
public class ResponseObject <T> extends ResponseEntity<ResponseObject.Payload<T>> {
    public ResponseObject(HttpStatusCode code, String message, T data) {
        super(new Payload<>(code.value(), message, data),code);
    }
    @Builder
    public static class Payload<T> {
        public int code;
        public String message;
        @JsonInclude(JsonInclude.Include.NON_NULL)
        public T data;
    }
}
