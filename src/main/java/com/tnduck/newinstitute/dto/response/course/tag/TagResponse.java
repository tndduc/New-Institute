package com.tnduck.newinstitute.dto.response.course.tag;

import com.tnduck.newinstitute.dto.response.course.CourseResponse;
import com.tnduck.newinstitute.dto.response.user.UserResponse;
import com.tnduck.newinstitute.entity.Course;
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
public class TagResponse {
    @Schema(
            name = "id",
            description = "ID of tag",
            type = "UUID",
            example = "2546926a-a9d9-45c0-8523-6dee1dd3db4d"
    )
    private UUID id;
    @Schema(
            name = "name",
            description = "Name of tag",
            type = "String",
            example = "Backend"
    )
    private String name;
    @Schema(
            name = "description",
            description = "Description of tag",
            type = "String",
            example = "Backend"
    )
    private String description;
    public static TagResponse convert(TagCourse tagCourse) {
        return TagResponse.builder()
                .id(tagCourse.getId())
                .name(tagCourse.getName())
                .description(tagCourse.getDescription())
                .build();
    }
}
