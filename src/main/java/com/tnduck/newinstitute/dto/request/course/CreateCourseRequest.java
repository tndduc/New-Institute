package com.tnduck.newinstitute.dto.request.course;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author ductn
 * @project The new institute
 * @created 25/02/2024 - 2:44 PM
 */
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class CreateCourseRequest extends AbstractBaseCourseRequest {
        @Schema(
            name = "file",
            description = "File of course",
            type = "File"
            )
    private MultipartFile file;
    @Schema(
            name = "status",
            description = "Is the course have checked?",
            type = "String",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED,
            example = "unchecked"
    )
    private String status;
}
