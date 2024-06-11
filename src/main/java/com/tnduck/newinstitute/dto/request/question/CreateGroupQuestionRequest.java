package com.tnduck.newinstitute.dto.request.question;

import com.tnduck.newinstitute.dto.request.choice.CreateGroupChoiceRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class CreateGroupQuestionRequest {
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
            name = "choices",
            description = "List of answer",
            type = "List<CreateGroupChoiceRequest>"
    )
    private List<CreateGroupChoiceRequest> choices;
}
