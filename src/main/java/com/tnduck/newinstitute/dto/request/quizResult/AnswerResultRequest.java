package com.tnduck.newinstitute.dto.request.quizResult;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * @author ductn
 * @project New-Institute
 * @created 17/05/2024
 */
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class AnswerResultRequest {
    @NotBlank(message = "{not_blank}")
    @Size(max = 50, message = "{max_length}")
    @Schema(
            name = "answerId",
            description = "id of answer",
            type = "String",
            requiredMode = Schema.RequiredMode.REQUIRED,
            example = "")
    private String answerId;
}
