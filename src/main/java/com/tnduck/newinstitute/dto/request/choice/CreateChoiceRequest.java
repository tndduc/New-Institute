package com.tnduck.newinstitute.dto.request.choice;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class CreateChoiceRequest {
    @Schema(
            name = "isCorrect",
            requiredMode = Schema.RequiredMode.REQUIRED,
            description = "isCorrect",
            type = "boolean"
    )
    private boolean isCorrect;

    @Schema(
            name = "content",
            description = "Content of course",
            type = "String",
            requiredMode = Schema.RequiredMode.REQUIRED,
            example = "yes"
    )
    private String content;
    @Schema(
            name = "idQuestion",
            description = "id of question",
            requiredMode = Schema.RequiredMode.REQUIRED,
            type = "String",
            example = "0"
    )
    private String idQuestion;
}
