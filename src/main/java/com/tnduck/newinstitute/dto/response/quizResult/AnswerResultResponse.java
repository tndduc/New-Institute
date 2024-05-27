package com.tnduck.newinstitute.dto.response.quizResult;

import com.tnduck.newinstitute.entity.Choice;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author ductn
 * @project New-Institute
 * @created 17/05/2024
 */
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class AnswerResultResponse {
    @Schema(
            name = "answerId",
            description = "id of answer",
            type = "String",
            example = "")
    private String answerId;
    @Schema(
            name = "isTrue",
            description = "isTrue",
            type = "Boolean",
            example = "")
    private Boolean isTrue;

    public static AnswerResultResponse convert(Choice choice){
        return AnswerResultResponse.builder()
               .answerId(choice.getId().toString())
               .build();
    }
    public static AnswerResultResponse convert(Choice choice,Boolean isTrue){
        return AnswerResultResponse.builder()
                .answerId(choice.getId().toString())
                .isTrue(isTrue)
                .build();
    }

    public static List<AnswerResultResponse> convert(List<Choice> choiceList, Boolean isGetAnswer) {
        return choiceList.stream()
                .map(choice -> isGetAnswer
                        ? AnswerResultResponse.convert(choice, choice.isCorrect())
                        : AnswerResultResponse.convert(choice))
                .collect(Collectors.toList());
    }
}
