package com.tnduck.newinstitute.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Map;
/**
 * @author ductn
 * @project The new institute
 * @created 27/01/2024 - 8:56 PM
 */
@Getter
@Setter
@SuperBuilder
public class DetailedErrorResponse extends ErrorResponse {
    @Schema(
        name = "items",
        description = "Error message",
        type = "Map",
        nullable = true,
        example = "{\"foo\": \"Bar\"}"
    )
    private Map<String, String> items;
}
