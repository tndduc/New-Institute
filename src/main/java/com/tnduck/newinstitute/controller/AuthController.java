package com.tnduck.newinstitute.controller;

import com.tnduck.newinstitute.dto.request.auth.LoginRequest;
import com.tnduck.newinstitute.dto.request.auth.PasswordRequest;
import com.tnduck.newinstitute.dto.request.auth.RegisterRequest;
import com.tnduck.newinstitute.dto.request.auth.ResetPasswordRequest;
import com.tnduck.newinstitute.dto.response.DetailedErrorResponse;
import com.tnduck.newinstitute.dto.response.ErrorResponse;
import com.tnduck.newinstitute.dto.response.SuccessResponse;
import com.tnduck.newinstitute.dto.response.auth.PasswordResetResponse;
import com.tnduck.newinstitute.dto.response.auth.TokenResponse;
import com.tnduck.newinstitute.service.AuthService;
import com.tnduck.newinstitute.service.MessageSourceService;
import com.tnduck.newinstitute.service.PasswordResetTokenService;
import com.tnduck.newinstitute.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.tnduck.newinstitute.util.Constants.SECURITY_SCHEME_NAME;
/**
 * @author ductn
 * @project The new institute
 * @created 27/01/2024 - 9:20 PM
 */
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "001. Auth", description = "Auth API")
public class AuthController extends AbstractBaseController {
    private final AuthService authService;

    private final UserService userService;

    private final PasswordResetTokenService passwordResetTokenService;

    private final MessageSourceService messageSourceService;

    @PostMapping("/login")
    @Operation(
        summary = "Login endpoint",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Successful operation",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = TokenResponse.class)
                )
            ),
            @ApiResponse(
                responseCode = "401",
                description = "Bad credentials",
                content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ErrorResponse.class)
                )
            ),
            @ApiResponse(
                responseCode = "422",
                description = "Validation failed",
                content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = DetailedErrorResponse.class)
                )
            )
        }
    )
    public ResponseEntity<TokenResponse> login(
        @Parameter(description = "Request body to login", required = true)
        @RequestBody @Validated final LoginRequest request
    ) {
        return ResponseEntity.ok(authService.login(request.getEmail(), request.getPassword(), request.getRememberMe()));
    }

    @PostMapping("/register")
    @Operation(
        summary = "Register endpoint",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Successful operation",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = SuccessResponse.class)
                )
            ),
            @ApiResponse(
                responseCode = "422",
                description = "Validation failed",
                content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = DetailedErrorResponse.class)
                )
            )
        }
    )
    public ResponseEntity<SuccessResponse> register(
        @Parameter(description = "Request body to register", required = true)
        @RequestBody @Valid RegisterRequest request
    ) throws BindException {
        userService.register(request);

        return ResponseEntity.ok(SuccessResponse.builder().message(messageSourceService.get("registered_successfully"))
            .build());
    }
    @PostMapping("/register-teacher")
    @Operation(
            summary = "Register Teacher endpoint",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successful operation",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = SuccessResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "422",
                            description = "Validation failed",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = DetailedErrorResponse.class)
                            )
                    )
            }
    )
    public ResponseEntity<SuccessResponse> registerTeacher(
            @Parameter(description = "Request body to register", required = true)
            @RequestBody @Valid RegisterRequest request
    ) throws BindException {
        userService.registerTeacher(request);

        return ResponseEntity.ok(SuccessResponse.builder().message(messageSourceService.get("registered_successfully"))
                .build());
    }

    @GetMapping("/email-verification/{token}")
    @Operation(
        summary = "E-mail verification endpoint",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Successful operation",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = SuccessResponse.class)
                )
            ),
            @ApiResponse(
                responseCode = "404",
                description = "Not found verification token",
                content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ErrorResponse.class)
                )
            )
        }
    )
    public ResponseEntity<SuccessResponse> emailVerification(
        @Parameter(name = "token", description = "E-mail verification token", required = true)
        @PathVariable("token") final String token
    ) {
        userService.verifyEmail(token);

        return ResponseEntity.ok(SuccessResponse.builder()
            .message(messageSourceService.get("your_email_verified"))
            .build());
    }

    @PostMapping("/refresh")

    public ResponseEntity<TokenResponse> refresh( @Parameter(name = "refreshToken", description = "", example = "")
                                                      @RequestParam(required = true) final String refreshToken
    ) {
        return ResponseEntity.ok(authService.refreshFromBearerString(refreshToken));
    }

    @PostMapping("/reset-password")
    @Operation(
        summary = "Reset password endpoint",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Successful operation",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = SuccessResponse.class)
                )
            ),
            @ApiResponse(
                responseCode = "400",
                description = "Bad request",
                content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ErrorResponse.class)
                )
            ),
            @ApiResponse(
                responseCode = "401",
                description = "Bad credentials",
                content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ErrorResponse.class)
                )
            )
        }
    )
    public ResponseEntity<SuccessResponse> resetPassword(
        @Parameter(description = "Request body to password", required = true)
        @RequestBody @Valid PasswordRequest request
    ) {
        authService.resetPassword(request.getEmail());

        return ResponseEntity.ok(SuccessResponse.builder()
            .message(messageSourceService.get("password_reset_link_sent"))
            .build());
    }

    @GetMapping("/reset-password/{token}")
    @Operation(
        summary = "Reset password check token endpoint",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Successful operation",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = PasswordResetResponse.class)
                )
            ),
            @ApiResponse(
                responseCode = "400",
                description = "Bad request",
                content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ErrorResponse.class)
                )
            ),
            @ApiResponse(
                responseCode = "401",
                description = "Bad credentials",
                content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ErrorResponse.class)
                )
            )
        }
    )
    public ResponseEntity<PasswordResetResponse> resetPassword(
        @Parameter(name = "token", description = "Password reset token", required = true)
        @PathVariable("token") final String token
    ) {
        return ResponseEntity.ok(PasswordResetResponse.convert(passwordResetTokenService.findByToken(token)));
    }

    @PostMapping("/reset-password/{token}")
    @Operation(
        summary = "Reset password with token endpoint",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Successful operation",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = PasswordResetResponse.class)
                )
            ),
            @ApiResponse(
                responseCode = "400",
                description = "Bad request",
                content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ErrorResponse.class)
                )
            ),
            @ApiResponse(
                responseCode = "401",
                description = "Bad credentials",
                content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ErrorResponse.class)
                )
            )
        }
    )
    public ResponseEntity<SuccessResponse> resetPassword(
        @Parameter(name = "token", description = "Password reset token", required = true)
        @PathVariable("token") final String token,
        @Parameter(description = "Request body to update password", required = true)
        @RequestBody @Valid ResetPasswordRequest request
    ) {
        userService.resetPassword(token, request);

        return ResponseEntity.ok(SuccessResponse.builder()
            .message(messageSourceService.get("password_reset_success_successfully"))
            .build());
    }

    @GetMapping("/logout")
    @Operation(
        summary = "Logout endpoint",
        security = @SecurityRequirement(name = SECURITY_SCHEME_NAME),
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Successful operation",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = SuccessResponse.class)
                )
            ),
            @ApiResponse(
                responseCode = "400",
                description = "Bad request",
                content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ErrorResponse.class)
                )
            ),
            @ApiResponse(
                responseCode = "401",
                description = "Bad request",
                content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ErrorResponse.class)
                )
            )
        }
    )
    public ResponseEntity<SuccessResponse> logout() {
        authService.logout(userService.getUser());

        return ResponseEntity.ok(SuccessResponse.builder()
            .message(messageSourceService.get("logout_successfully"))
            .build());
    }
}
