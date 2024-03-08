package com.tnduck.newinstitute.dto.request.course.category;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

/**
 * @author ductn
 * @project The new institute
 * @created 07/03/2024 - 12:45 PM
 */
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class CategoryRequest {
    @Schema(
            name = "id",
            description = "ID of course",
            type = "UUID",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED,
            example = "2546926a-a9d9-45c0-8523-6dee1dd3db4d"
    )
    private UUID id;
    @Schema(
            name = "name",
            description = "Name of category",
            type = "String",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED,
            example = "Backend"
    )
    private String name;
}
