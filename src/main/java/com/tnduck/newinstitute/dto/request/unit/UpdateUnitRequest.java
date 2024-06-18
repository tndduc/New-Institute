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
public class UpdateUnitRequest {

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
            description = "Title of Unit",
            type = "String",
            example = "Final test"
    )
    private String title;
    @Schema(
            name = "isPreview",
            description = "isPreview?",
            type = "Boolean",
            allowableValues = {"true", "false"}, defaultValue = "false"
    )
    private String isPreview;
    @Schema(
            name = "duration",
            description = "duration of Unit",
            type = "String",
            example = "Final test"
    )
    private String duration;

    @Schema(
            name = "type",
            description = "id Unit of course",
            type = "String",
            requiredMode = Schema.RequiredMode.REQUIRED,
            example = ""
    )
    private String type;

}
