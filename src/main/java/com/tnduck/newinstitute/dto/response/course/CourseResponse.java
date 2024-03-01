package com.tnduck.newinstitute.dto.response.course;

import com.tnduck.newinstitute.dto.response.AbstractBaseResponse;
import com.tnduck.newinstitute.dto.response.user.UserResponse;
import com.tnduck.newinstitute.entity.Course;
import com.tnduck.newinstitute.entity.File;
import com.tnduck.newinstitute.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author ductn
 * @project The new institute
 * @created 28/02/2024 - 4:22 PM
 */
@Getter
@Setter
@SuperBuilder
public class CourseResponse  extends AbstractBaseResponse {
    @Schema(
            name = "id",
            description = "UUID",
            type = "String",
            example = "91b2999d-d327-4dc8-9956-2fadc0dc8778"
    )
    private String id;

    @Schema(
            name = "description",
            description = "Description for the course",
            type = "String",
            example = "Belo welcome to my channel, this channel gonna teach u how to be a lover"
    )
    private String description;

    @Schema(
            name = "name",
            description = "name for the course",
            type = "String",
            example = "Make U happy"
    )
    private String name;

    @Schema(
            name = "price",
            description = "Price of course",
            type = "BigDecimal",
            requiredMode = Schema.RequiredMode.REQUIRED,
            example = "0"
    )
    private BigDecimal price;

    @Schema(
            name = "image",
            description = "Image title of course",
            type = "String",
            example = "DOE"
    )
    private File file;

    @Schema(
            name = "userResponse",
            description = "user",
            type = "userResponse"
    )
    private UserResponse userResponse;

    @Schema(
            name = "status",
            description = "status of course",
            type = "String",
            example = "uncheck"
    )
    private String status;

    @Schema(
            name = "createdAt",
            description = "Date time field of course creation",
            type = "LocalDateTime",
            example = "2022-09-29T22:37:31"
    )
    private LocalDateTime createdAt;

    @Schema(
            name = "updatedAt",
            type = "LocalDateTime",
            description = "Date time field of course update",
            example = "2022-09-29T22:37:31"
    )
    private LocalDateTime updatedAt;

    /**
     * Convert Course to UserResponse
     * @param course
     * @return CourseResponse
     */
    public static CourseResponse convert(Course course) {
        UserResponse userDTO = UserResponse.convert(course.getUser());
        return CourseResponse.builder()
                .id(course.getId().toString())
                .name(course.getName())
                .price(course.getPrice())
                .description(course.getDescription())
                .file(course.getFile())
                .userResponse(userDTO)
                .status(course.getStatus())
                .createdAt(course.getCreatedAt())
                .updatedAt(course.getUpdatedAt())
                .build();
    }

}
