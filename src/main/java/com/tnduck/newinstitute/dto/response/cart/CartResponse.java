package com.tnduck.newinstitute.dto.response.cart;

import com.tnduck.newinstitute.dto.response.course.CourseResponse;
import com.tnduck.newinstitute.dto.response.enroll.EnrollResponse;
import com.tnduck.newinstitute.dto.response.user.UserResponse;
import com.tnduck.newinstitute.entity.Cart;
import com.tnduck.newinstitute.entity.Enrollment;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class CartResponse {
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
            type = "CourseResponse")
    private CourseResponse courseResponse;
    @Schema(
            name = "userResponse",
            description = "Teacher of enrollment response",
            type = "UserResponse"  )
    private UserResponse userResponse;
    public static CartResponse convert(Cart cart){
        return CartResponse.builder()
                .id(cart.getId().toString())
                .status(cart.getStatus())
                .courseResponse(CourseResponse.convert(cart.getCourse()))
                .userResponse(UserResponse.convert(cart.getUser()))
                .build();
    }
}
