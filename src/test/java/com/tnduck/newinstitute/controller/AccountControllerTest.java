package com.tnduck.newinstitute.controller;

import com.tnduck.newinstitute.dto.request.user.UpdatePasswordRequest;
import com.tnduck.newinstitute.dto.response.SuccessResponse;
import com.tnduck.newinstitute.dto.response.user.UserResponse;
import com.tnduck.newinstitute.entity.User;
import com.tnduck.newinstitute.service.MessageSourceService;
import com.tnduck.newinstitute.service.UserService;
import org.instancio.Instancio;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
/**
 * @author ductn
 * @project The new institute
 * @created 26/01/2024 - 11:25 PM
 */
@Tag("unit")
@ExtendWith(MockitoExtension.class)
@DisplayName("Unit tests for AccountController")
class AccountControllerTest {
    @InjectMocks
    private AccountController accountController;

    @Mock
    private UserService userService;

    @Mock
    private MessageSourceService messageSourceService;

    private final User user = Instancio.create(User.class);

    private final UpdatePasswordRequest updatePasswordRequest = Instancio.create(UpdatePasswordRequest.class);

    @Test
    @DisplayName("Test for get me")
    void given_whenMe_thenAssertBody() {
        // Given
        when(userService.getUser()).thenReturn(user);
        // When
        ResponseEntity<UserResponse> response = accountController.me();
        // Then
        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user.getId().toString(), response.getBody().getId());
        assertEquals(user.getEmail(), response.getBody().getEmail());
        assertEquals(user.getFirstName(), response.getBody().getFirstName());
        assertEquals(user.getLastName(), response.getBody().getLastName());
    }

    @Test
    @DisplayName("Test for password update")
    void given_whenUpdatePassword_thenAssertBody() throws BindException {
        // Given
        String message = "your_password_updated";
        when(userService.updatePassword(updatePasswordRequest)).thenReturn(user);
        when(messageSourceService.get(message)).thenReturn(message);
        // When
        ResponseEntity<SuccessResponse> response = accountController.password(updatePasswordRequest);
        // Then
        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(message, response.getBody().getMessage());
    }

    @Test
    @DisplayName("Test for resend e-mail verification")
    void given_whenResendEmailVerification_thenAssertBody() {
        // Given
        String message = "verification_email_sent";
        doNothing().when(userService).resendEmailVerificationMail();
        when(messageSourceService.get(message)).thenReturn(message);
        // When
        ResponseEntity<SuccessResponse> response = accountController.resendEmailVerificationMail();
        // Then
        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(message, response.getBody().getMessage());
    }
}