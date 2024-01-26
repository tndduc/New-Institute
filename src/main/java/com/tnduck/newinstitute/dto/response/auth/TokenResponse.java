package com.tnduck.newinstitute.dto.response.auth;

import com.tnduck.newinstitute.dto.response.AbstractBaseResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class TokenResponse extends AbstractBaseResponse {
    @Schema(
        name = "token",
        description = "Token",
        type = "String",
        example = "eyJhbGciOiJIUzUxMiJ9..."
    )
    private String token;

    @Schema(
        name = "refreshToken",
        description = "Refresh Token",
        type = "String",
        example = "eyJhbGciOiJIUzUxMiJ9..."
    )
    private String refreshToken;

    @Schema(
        name = "expiresIn",
        description = "Expires In",
        type = "TokenExpiresInResponse"
    )
    private TokenExpiresInResponse expiresIn;
}
