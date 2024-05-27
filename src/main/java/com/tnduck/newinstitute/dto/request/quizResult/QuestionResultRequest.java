package com.tnduck.newinstitute.dto.request.quizResult;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 * @author ductn
 * @project New-Institute
 * @created 19/05/2024
 */
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class QuestionResultRequest {
    @NotBlank(message = "{not_blank}")
    @Size(max = 50, message = "{max_length}")
    @Schema(
            name = "questionId",
            description = "id of question",
            type = "String",
            requiredMode = Schema.RequiredMode.REQUIRED,
            example = "")
    private String questionId;
    @Schema(
            name = "answerResults",
            description = "List of answer results",
            type = "List<AnswerResult",
            requiredMode = Schema.RequiredMode.REQUIRED
            )
    List<AnswerResultRequest> answerResults;
}
