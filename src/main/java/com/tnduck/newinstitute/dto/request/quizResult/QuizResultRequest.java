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
public class QuizResultRequest {
    @NotBlank(message = "{not_blank}")
    @Size(max = 50, message = "{max_length}")
    @Schema(
            name = "quizId",
            description = "id of quiz",
            type = "String",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private String quizId;
    @Schema(
            name = "questionResultRequestList",
            description = "List of Question Result ",
            type = "List<QuestionResultRequest",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    List<QuestionResultRequest> questionResultRequestList;

}
