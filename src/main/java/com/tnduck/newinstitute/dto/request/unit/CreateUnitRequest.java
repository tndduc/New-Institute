package com.tnduck.newinstitute.dto.request.unit;

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
public class CreateUnitRequest {

    @Size(max = 50, message = "{max_length}")
    @Schema(
            name = "title",
            description = "Title of Unit",
            type = "String",
            example = "Final test"
    )
    private String title;
    @Schema(
            name = "ordinalNumber",
            description = "ordinalNumber",
            type = "int",
            example = "1"
    )
    private int ordinalNumber;
    @Schema(
            name = "duration",
            description = "duration of Unit",
            type = "String",
            example = "Final test"
    )
    private String duration;

    @NotBlank(message = "{not_blank}")
    @Schema(
            name = "type",
            description = "type?",
            type = "String",
            requiredMode = Schema.RequiredMode.REQUIRED,
            allowableValues = {"video", "quiz"}, defaultValue = "video"
    )
    private String type;
    @Schema(
            name = "lessonId",
            description = "id Lesson ",
            type = "String",
            example = ""
    )
    private String lessonId;
}
