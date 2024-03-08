package com.tnduck.newinstitute.dto.response.course.category;

import com.tnduck.newinstitute.dto.response.course.tag.TagResponse;
import com.tnduck.newinstitute.entity.CategoryCourse;
import com.tnduck.newinstitute.entity.TagCourse;
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
public class CategoryResponse {
    @Schema(
            name = "id",
            description = "ID of course",
            type = "UUID",
            example = "2546926a-a9d9-45c0-8523-6dee1dd3db4d"
    )
    private UUID id;
    @Schema(
            name = "name",
            description = "Name of category",
            type = "String",
            example = "Backend"
    )
    private String name;
    @Schema(
            name = "description",
            description = "Description of category",
            type = "String",
            example = "Backend"
    )
    private String description;
    public static CategoryResponse convert(CategoryCourse categoryCourse) {
        return CategoryResponse.builder()
                .id(categoryCourse.getId())
                .name(categoryCourse.getName())
                .description(categoryCourse.getDescription())
                .build();
    }
}
