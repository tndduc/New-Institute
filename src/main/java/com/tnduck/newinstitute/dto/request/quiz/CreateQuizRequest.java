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
    @Size(max = 500, message = "{max_length}")
    @Schema(
            name = "title",
            description = "Title of course",
            type = "String",
            requiredMode = Schema.RequiredMode.REQUIRED,
            example = "Final test"
    )
    private String title;
    @NotBlank(message = "{not_blank}")
    @Size(max = 500, message = "{max_length}")
    @Schema(
            name = "time",
            description = "time of quiz",
            type = "time",
            requiredMode = Schema.RequiredMode.REQUIRED,
            example = "Final test"
    )
    private int time;
    @Schema(
            name = "description",
            description = "Description of course",
            type = "String",
            example = "Final test description : do not use docs"
    )
    private String description;
    @Schema(
            name = "isPreview",
            description = "isPreview?",
            type = "Boolean",
            allowableValues = {"true", "false"}, defaultValue = "false"
    )
    private String isPreview;
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
            type = "String",
            allowableValues = {"true", "false"}, defaultValue = "false"
    )
    private String isFinalExam;
}
