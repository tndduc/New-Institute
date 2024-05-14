package com.tnduck.newinstitute.dto.request.question;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * @author ductn
 * @project New-Institute
 * @created 12/05/2024}
 */
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class UpdateQuestionRequest {
    @NotBlank(message = "{not_blank}")
    @Size(max = 50, message = "{max_length}")
    @Schema(
            name = "id",
            description = "id of update question request",
            type = "String",
            requiredMode = Schema.RequiredMode.REQUIRED,
            example = "")
    private String id;
    @Size(max = 500, message = "{max_length}")
    @Schema(
            name = "content",
            description = "content of question",
            type = "String",
            example = "Is out web good ? "
    )
    private String content;
    @Size(max = 500, message = "{max_length}")
    @Schema(
            name = "description",
            description = "Description of question",
            type = "String",
            example = "Final test description : do not use docs"
    )
    private String description;
    @Size(max = 500, message = "{max_length}")
    @Schema(
            name = "level",
            description = "level of question",
            type = "String",
            example = "1"
    )
    private int level;
}
