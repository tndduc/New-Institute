package com.tnduck.newinstitute.dto.request.quiz;

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
public class UpdateQuizRequest{
    @NotBlank(message = "{not_blank}")
    @Size(max = 50, message = "{max_length}")
    @Schema(
            name = "id",
            description = "id of update quiz request",
            type = "String",
            requiredMode = Schema.RequiredMode.REQUIRED,
            example = "")
    private String id;
    @Size(max = 50, message = "{max_length}")
    @Schema(
            name = "title",
            description = "Title of course",
            type = "String",
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

    @NotBlank(message = "{not_blank}")
    @Schema(
            name = "isFinalExam",
            description = "isFinalExam?",
            type = "String",
            allowableValues = {"true", "false"}, defaultValue = "false"
    )
    private String isFinalExam;
}
