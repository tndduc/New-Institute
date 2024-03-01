package com.tnduck.newinstitute.dto.request.course;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

/**
 * @author ductn
 * @project The new institute
 * @created 25/02/2024 - 9:35 AM
 */
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public abstract class AbstractBaseCourseRequest {
    @NotBlank(message = "{not_blank}")
    @Size(max = 50, message = "{max_length}")
    @Schema(
            name = "name",
            description = "Name of course",
            type = "String",
            requiredMode = Schema.RequiredMode.REQUIRED,
            example = "Nextjs Zero to Hero"
    )
    private String name;
    @NotBlank(message = "{not_blank}")
    @Size(max = 500, message = "{max_length}")
    @Schema(
            name = "description",
            description = "Description of course",
            type = "String",
            requiredMode = Schema.RequiredMode.REQUIRED,
            example = "Become a Web Development Pro with these valuable skills. Find the right instructor for you. Choose from many topics, skill levels, and languages. Download To Your Phone. Courses in 75 Languages. Stay Updated with AI. Learn ChatGPT. ChatGPT AI Course."
    )
    private String description;
    @Schema(
            name = "file",
            description = "File of course",
            type = "File"
            )
    private MultipartFile file;
    @Schema(
            name = "price",
            description = "Price of course",
            type = "BigDecimal",
            requiredMode = Schema.RequiredMode.REQUIRED,
            example = "0"
    )
    private BigDecimal price;
}
