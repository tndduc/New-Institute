package com.tnduck.newinstitute.dto.response.enroll;

import com.tnduck.newinstitute.dto.response.course.CourseResponse;
import com.tnduck.newinstitute.dto.response.lesson.LessonResponse;
import com.tnduck.newinstitute.dto.response.user.UserResponse;
import com.tnduck.newinstitute.entity.Enrollment;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * @author ductn
 * @project New-Institute
 * @created 21/05/2024
 */
@Getter
@Setter
@SuperBuilder
public class EnrollResponse {
    @Schema(
            name = "id",
            description = "UUID",
            type = "String",
            example = "91b2999d-d327-4dc8-9956-2fadc0dc8778"
    )
    private String id;
    @Schema(
            name = "status",
            description = "Status of enroll",
            type = "String"
    )
    private String status;
    @Schema(
            name = "courseResponse",
            description = "Course of enrollment response",
            type = "CourseResponse"  )
    private CourseResponse courseResponse;
    @Schema(
            name = "userResponse",
            description = "Teacher of enrollment response",
            type = "UserResponse"  )
    private UserResponse userResponse;
    public static EnrollResponse convert(Enrollment enrollment){
        return EnrollResponse.builder()
               .id(enrollment.getId().toString())
               .status(enrollment.getStatus())
               .courseResponse(CourseResponse.convert(enrollment.getCourse()))
               .userResponse(UserResponse.convert(enrollment.getUser()))
               .build();
    }
}
