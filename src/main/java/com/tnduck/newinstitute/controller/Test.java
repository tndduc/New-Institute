package com.tnduck.newinstitute.controller;

import com.tnduck.newinstitute.dto.request.course.CreateCourseRequest;
import com.tnduck.newinstitute.dto.request.quiz.CreateQuizRequest;
import com.tnduck.newinstitute.entity.File;
import com.tnduck.newinstitute.service.CloudinaryService;
import com.tnduck.newinstitute.service.FileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

import static com.tnduck.newinstitute.util.Constants.SECURITY_SCHEME_NAME;

/**
 * @author ductn
 * @project The new institute
 * @created 21/02/2024 - 10:59 PM
 */
@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
@Slf4j
public class Test {

    @PreAuthorize("hasAuthority('TEACHER')")
    @RequestMapping(
            path = "/create",
            method = RequestMethod.POST
            )
    @Operation(
            summary = "create a quiz",
            security = @SecurityRequirement(name = SECURITY_SCHEME_NAME)
    )
    public ResponseEntity<?> createQuiz(@ModelAttribute final CreateQuizRequest createQuizRequest) {
        try {
            return ResponseEntity.ok(createQuizRequest);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
