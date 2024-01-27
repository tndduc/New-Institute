package com.tnduck.newinstitute.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;
/**
 * @author ductn
 * @project The new institute
 * @created 27/01/2024 - 9:55 PM
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    public NotFoundException() {
        super("Not found!");
    }

    public NotFoundException(final String message) {
        super(message);
    }
}
