package com.tnduck.newinstitute.dto.request.course;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

/**
 * @author ductn
 * @project The new institute
 * @created 27/02/2024 - 7:52 AM
 */
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class UpdateCourseRequest extends AbstractBaseCourseRequest {
    @Schema(
            name = "id",
            description = "ID of course",
            type = "UUID",
            requiredMode = Schema.RequiredMode.REQUIRED,
            example = "2546926a-a9d9-45c0-8523-6dee1dd3db4d"
    )
    private UUID id;
    @Schema(
            name = "status",
            description = "Is the course have checked?",
            type = "String",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED,
            example = "unchecked"
    )
    private String status;
}
