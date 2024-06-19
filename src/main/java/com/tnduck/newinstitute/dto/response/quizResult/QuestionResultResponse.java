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
public class QuestionResultResponse{
    @Schema(
            name = "questionId",
            description = "id of question",
            type = "String")
    private String questionId;
    @Schema(
            name = "answerResults",
            description = "List of answer results",
            type = "List<AnswerResult"
            )
    List<AnswerResultResponse> answerResults;

    public static QuestionResultResponse convert(String questionId, List<AnswerResultResponse> answerResults) {
        return QuestionResultResponse.builder()
               .questionId(questionId)
               .answerResults(answerResults)
               .build();
    }
}
