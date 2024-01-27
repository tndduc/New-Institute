package com.tnduck.newinstitute.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
/**
 * @author ductn
 * @project The new institute
 * @created 27/01/2024 - 8:56 PM
 */
@Getter
@Setter
@SuperBuilder
public class SuccessResponse extends AbstractBaseResponse {
    @Schema(
        name = "message",
        type = "Integer",
        description = "Response message field",
        example = "Success!"
    )
    private String message;
}
