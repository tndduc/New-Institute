package com.tnduck.newinstitute.dto.request.auth;

import com.tnduck.newinstitute.dto.annotation.Password;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
/**
 * @author ductn
 * @project The new institute
 * @created 27/01/2024 - 9:37 PM
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {
    @NotBlank(message = "{not_blank}")
    @Email(message = "{invalid_email}")
    @Schema(
        name = "email",
        description = "E-mail of the user",
        type = "String",
        requiredMode = Schema.RequiredMode.REQUIRED,
        example = "mail@example.com"
    )
    private String email;

    @NotBlank(message = "{not_blank}")
    @Password(message = "{invalid_password}")
    @Schema(
        name = "password",
        description = "Password of the user",
        type = "String",
        requiredMode = Schema.RequiredMode.REQUIRED,
        example = "P@sswd123."
    )
    private String password;

    @Schema(
        name = "rememberMe",
        description = "Remember option for refresh token",
        type = "Boolean",
        example = "true"
    )
    private Boolean rememberMe;
}
