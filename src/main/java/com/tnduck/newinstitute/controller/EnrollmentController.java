package com.tnduck.newinstitute.controller;

import com.tnduck.newinstitute.dto.request.question.CreateQuestionRequest;
import com.tnduck.newinstitute.service.EnrollmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.tnduck.newinstitute.util.Constants.SECURITY_SCHEME_NAME;

/**
 * @author ductn
 * @project New-Institute
 * @created 13/05/2024
 */
@RestController
@RequestMapping("/enrollment")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "011. Enrollment", description = "Enrollment API")
public class EnrollmentController   extends AbstractBaseController{
    private final EnrollmentService enrollmentService;
    @PostMapping("/enroll")
    @PreAuthorize("hasAuthority('USER')")
    @Operation(
            summary = "Create a new Lesson",
            security = @SecurityRequirement(name = SECURITY_SCHEME_NAME)
    )
    public ResponseEntity<?> createLesson(
            @Parameter(description = "Request body to create Lesson", required = true)
            @RequestBody @Valid String idCourse
    )  {
        try {
            return enrollmentService.createEnroll(idCourse);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}