package com.tnduck.newinstitute.dto.response.quizResult;

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
public class QuizResultResponse {
    @Schema(
            name = "quizId",
            description = "id of quiz",
            type = "String")
    private String quizId;
    @Schema(
            name = "questionResultRequestList",
            description = "List of Question Result ",
            type = "List<QuizResultResponse"
    )
    List<QuizResultResponse> questionResultRequestList;

}
