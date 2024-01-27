package com.tnduck.newinstitute.exception;

import com.tnduck.newinstitute.dto.response.ErrorResponse;
import org.instancio.Instancio;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
/**
 * @author ductn
 * @project The new institute
 * @created 27/01/2024 - 00:23 AM
 */
@Tag("unit")
@DisplayName("Unit test for ExpectationException")
class TokenExpiredExceptionTest {
    @Test
    @DisplayName("Test ExpectationException")
    void testHandleExpectationException() {
        // Given
        TokenExpiredException exception = Instancio.create(TokenExpiredException.class);
        AppExceptionHandler exceptionHandler = Instancio.create(AppExceptionHandler.class);
        // When
        ResponseEntity<ErrorResponse> response = exceptionHandler.handleTokenExpiredRequestException(exception);
        // Then
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }
}
