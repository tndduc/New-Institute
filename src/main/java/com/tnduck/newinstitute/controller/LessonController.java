package com.tnduck.newinstitute.controller;

import com.tnduck.newinstitute.dto.request.lesson.LessonRequest;
import com.tnduck.newinstitute.dto.request.lesson.LessonUpdateRequest;
import com.tnduck.newinstitute.dto.request.user.UpdatePasswordRequest;
import com.tnduck.newinstitute.dto.response.DetailedErrorResponse;
import com.tnduck.newinstitute.dto.response.ErrorResponse;
import com.tnduck.newinstitute.dto.response.SuccessResponse;
import com.tnduck.newinstitute.dto.response.auth.TokenResponse;
import com.tnduck.newinstitute.dto.response.lesson.LessonResponse;
import com.tnduck.newinstitute.entity.Lesson;
import com.tnduck.newinstitute.entity.Video;
import com.tnduck.newinstitute.service.LessonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import static com.tnduck.newinstitute.util.Constants.SECURITY_SCHEME_NAME;
/**
 * @author ductn
 * @project The new institute
 * @created 03/03/2024 - 8:02 PM
 */
@RestController
@RequestMapping("/lesson")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "004. Lesson", description = "Lesson API")
public class LessonController extends AbstractBaseController{
    private final LessonService lessonService;
    @PostMapping("/create")
    @PreAuthorize("hasAuthority('TEACHER')")
    @Operation(
            summary = "Create a new Lesson",
            security = @SecurityRequirement(name = SECURITY_SCHEME_NAME)
    )
    public ResponseEntity<?> createLesson(
            @Parameter(description = "Request body to create Lesson", required = true)
            @RequestBody @Valid LessonRequest request
    )  {
        try {
            return lessonService.createLesson(request);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @PreAuthorize("hasAuthority('TEACHER')")
    @RequestMapping(
            path = "/delete",
            method = RequestMethod.DELETE)
    @Operation(
            summary = "delete a lesson",
            security = @SecurityRequirement(name = SECURITY_SCHEME_NAME)
    )
    public ResponseEntity<?> deleteLesson(@RequestBody(required = true) final String id) {
        try {

            return lessonService.deleteLesson(id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @PreAuthorize("hasAuthority('TEACHER')")
    @RequestMapping(
            path = "/update/{id}",
            method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Update a new Lesson",
            security = @SecurityRequirement(name = SECURITY_SCHEME_NAME)
    )
    public ResponseEntity<?> updateLesson (@PathVariable("id") final String id,
                                           @RequestBody(required = false) final LessonUpdateRequest lessonUpdateRequest) {
        try {
            return lessonService.updateLesson(id,lessonUpdateRequest);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
   @GetMapping("/get-lessons")
    public ResponseEntity<?> getLesson(@Parameter(name = "id", description = "Course ID", example = "00000000-0000-0000-0000-000000000001")
                                                       @RequestParam(required = true) final UUID id) {
        try {
            return lessonService.getLessonByIdCourse(id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }




}
