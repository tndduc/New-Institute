package com.tnduck.newinstitute.dto.request.quiz;

import com.tnduck.newinstitute.dto.request.question.CreateGroupQuestionRequest;
import com.tnduck.newinstitute.dto.request.question.CreateQuestionRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class CreateGroupQuizRequest {
    @NotBlank(message = "{not_blank}")
    @Schema(
            name = "title",
            description = "Title of course",
            type = "String",
            requiredMode = Schema.RequiredMode.REQUIRED,
            example = "Final test"
    )
    private String title;
    @Schema(
            name = "description",
            description = "Description of course",
            type = "String",
            example = "Final test description : do not use docs"
    )
    private String description;

    @Schema(
            minimum = "0",
            name = "time",
            description = "time of quiz, set time = 0 if you want to do quiz without time limit",
            type = "int",
            requiredMode = Schema.RequiredMode.REQUIRED,
            example = "60"
    )
    private int time;
    @Schema(
            name = "idLesson",
            description = "id Unit of course",
            type = "String",
            requiredMode = Schema.RequiredMode.REQUIRED,
            example = ""
    )
    private String idLesson;
    @Schema(
            name = "isFinalExam",
            description = "isFinalExam?",
            type = "Boolean",
            allowableValues = {"true", "false"}, defaultValue = "false"
    )
    private String isFinalExam;
    @Schema(
            name = "questions",
            description = "List of questions",
            type = "List<CreateGroupQuestionRequest>"
    )
    private List<CreateGroupQuestionRequest> questions;
}
