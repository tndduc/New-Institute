package com.tnduck.newinstitute.dto.request.choice;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * @author ductn
 * @project New-Institute
 * @created 13/05/2024
 */
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class UpdateChoiceRequest {
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
            name = "id Choice",
            description = "id of choice",
            requiredMode = Schema.RequiredMode.REQUIRED,
            type = "String",
            example = "0"
    )
    private String idChoice;
}
