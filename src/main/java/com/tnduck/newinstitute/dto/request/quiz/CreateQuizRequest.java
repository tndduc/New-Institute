package com.tnduck.newinstitute.dto.request.quiz;

import com.tnduck.newinstitute.entity.Lesson;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class CreateQuizRequest {
    @NotBlank(message = "{not_blank}")
    @Size(max = 50, message = "{max_length}")
    @Schema(
            name = "title",
            description = "Title of course",
            type = "String",
            requiredMode = Schema.RequiredMode.REQUIRED,
            example = "Final test"
    )
    private String title;
    @Size(max = 500, message = "{max_length}")
    @Schema(
            name = "description",
            description = "Description of course",
            type = "String",
            example = "Final test description : do not use docs"
    )
    private String description;

    @Schema(
            name = "idLesson",
            description = "id Lesson of course",
            type = "String",
            requiredMode = Schema.RequiredMode.REQUIRED,
            example = ""
    )
    private String idLesson;
    @NotBlank(message = "{not_blank}")
    @Schema(
            name = "isFinalExam",
            description = "isFinalExam?",
            type = "boolean",
            requiredMode = Schema.RequiredMode.REQUIRED,
            allowableValues = {"true", "false"}, defaultValue = "false"
    )
    private boolean isFinalExam;
}
