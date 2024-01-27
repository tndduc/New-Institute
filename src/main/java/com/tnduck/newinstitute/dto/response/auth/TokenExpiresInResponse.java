package com.tnduck.newinstitute.dto.response.auth;

import com.tnduck.newinstitute.dto.response.AbstractBaseResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
/**
 * @author ductn
 * @project The new institute
 * @created 27/01/2024 - 8:46 PM
 */
@Getter
@Setter
@SuperBuilder
public class TokenExpiresInResponse extends AbstractBaseResponse {
    @Schema(
        name = "token",
        description = "Token expires In",
        type = "Long",
        example = "3600"
    )
    private Long token;

    @Schema(
        name = "refreshToken",
        description = "Refresh token expires In",
        type = "Long",
        example = "3600"
    )
    private Long refreshToken;
}
