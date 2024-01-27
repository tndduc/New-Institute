package com.tnduck.newinstitute.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;
/**
 * @author ductn
 * @project The new institute
 * @created 27/01/2024 - 9:42 PM
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    public BadRequestException() {
        super("Bad request!");
    }

    public BadRequestException(final String message) {
        super(message);
    }
}
