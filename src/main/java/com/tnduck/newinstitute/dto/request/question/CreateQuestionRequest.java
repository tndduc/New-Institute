package com.tnduck.newinstitute.dto.request.question;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class CreateQuestionRequest {

    @Schema(
            name = "content",
            description = "Content of question",
            type = "String",
            requiredMode = Schema.RequiredMode.REQUIRED,
            example = "Is this course good?"
    )
    private String content;
    @Schema(
            name = "description",
            description = "description of question",
            type = "String",
            example = "Is this course good?"
    )
    private String description;
    @Schema(
            name = "level",
            description = "level of question",
            type = "int",
            example = "1"
    )
    private int level;
    @Schema(
            name = "idQuiz",
            description = "id of quiz",
            requiredMode = Schema.RequiredMode.REQUIRED,
            type = "String",
            example = "0"
    )
    private String idQuiz;
}
