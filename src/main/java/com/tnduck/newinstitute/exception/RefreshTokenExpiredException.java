package com.tnduck.newinstitute.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
/**
 * @author ductn
 * @project The new institute
 * @created 27/01/2024 - 9:55 PM
 */
@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class RefreshTokenExpiredException extends TokenExpiredException {
    public RefreshTokenExpiredException() {
        super("Refresh token is expired!");
    }

    public RefreshTokenExpiredException(final String message) {
        super(message);
    }
}
