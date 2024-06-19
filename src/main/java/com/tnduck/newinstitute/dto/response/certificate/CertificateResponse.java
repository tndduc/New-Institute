package com.tnduck.newinstitute.dto.response.certificate;

import com.tnduck.newinstitute.dto.response.course.CourseResponse;
import com.tnduck.newinstitute.dto.response.user.UserResponse;
import com.tnduck.newinstitute.entity.Certificate;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@Setter
@SuperBuilder
public class CertificateResponse {
    @Schema(
            name = "id",
            description = "UUID",
            type = "String",
            example = "91b2999d-d327-4dc8-9956-2fadc0dc8778"
    )
    private String id;
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
    @Schema(
            name = "score",
            description = "Score",
            type = "String"
    )
    private String score;

    private LocalDateTime issueDate;

    private LocalDateTime expiryDate;

    public static CertificateResponse convert(Certificate certificate) {
        return CertificateResponse.builder()
                .id(certificate.getId().toString())
                .courseResponse(CourseResponse.convert(certificate.getCourse()))
                .userResponse(UserResponse.convert(certificate.getUser()))
                .score(certificate.getScore())
                .issueDate(certificate.getIssueDate())
                .expiryDate(certificate.getExpiryDate())
                .build();
    }
}
