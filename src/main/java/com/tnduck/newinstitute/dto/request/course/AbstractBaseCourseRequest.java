package com.tnduck.newinstitute.dto.request.course;

import com.tnduck.newinstitute.dto.request.course.category.CategoryRequest;
import com.tnduck.newinstitute.dto.request.course.tag.TagRequest;
import com.tnduck.newinstitute.dto.validator.Level;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

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
    //    @Schema(
//            name = "file",
//            description = "File of course",
//            type = "File"
//            )
//    private MultipartFile file;
    @NotBlank(message = "{not_blank}")
    @Size(max = 50, message = "{max_length}")
    @Schema(
            name = "level",
            description = "Level of course",
            type = "String",
            requiredMode = Schema.RequiredMode.REQUIRED,
            example = "Nextjs Zero to Hero"
    )
    private String level;
    @Schema(
            name = "discount",
            description = "Discount of course",
            type = "BigDecimal",
            requiredMode = Schema.RequiredMode.REQUIRED,
            example = "0"
    )
    private BigDecimal discount;
    @Schema(
            name = "price",
            description = "Price of course",
            type = "BigDecimal",
            requiredMode = Schema.RequiredMode.REQUIRED,
            example = "0"
    )
    private BigDecimal price;
    //    @Schema(
//            name = "categories",
//            description = "List of categories",
//            type = "array",
//            requiredMode = Schema.RequiredMode.REQUIRED,
//            example = "[{uuid: '00000000-0000-0000-0000-000000000001', name: 'Backend'}, {uuid: '00000000-0000-0000-0000-000000000002', name: 'Web'}]"
//    )
//    private List<CategoryRequest> categories;
//    @Schema(
//            name = "tags",
//            description = "List of tags",
//            type = "array",
//            requiredMode = Schema.RequiredMode.REQUIRED,
//            example = "[{uuid: '00000000-0000-0000-0000-000000000001', name: 'Nodejs'}, {uuid: '00000000-0000-0000-0000-000000000002', name: 'BlockChain'}]"
//    )
//    private List<TagRequest> tags;
    @ArraySchema(
            schema = @Schema(
                    description = "List of tags",
                    requiredMode = Schema.RequiredMode.NOT_REQUIRED
            )
    )
    private List<String> tags;
    @ArraySchema(
            schema = @Schema(
                    description = "List of categories",
                    requiredMode = Schema.RequiredMode.NOT_REQUIRED
            )
    )
    private List<String> categories;
}
