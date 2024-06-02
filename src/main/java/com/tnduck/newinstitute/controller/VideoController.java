package com.tnduck.newinstitute.controller;

import com.tnduck.newinstitute.dto.request.video.CreateVideoLessonRequest;
import com.tnduck.newinstitute.dto.response.DetailedErrorResponse;
import com.tnduck.newinstitute.dto.response.SuccessResponse;
import com.tnduck.newinstitute.dto.response.video.VideoResponse;
import com.tnduck.newinstitute.service.LessonService;
import com.tnduck.newinstitute.service.VideoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static com.tnduck.newinstitute.util.Constants.SECURITY_SCHEME_NAME;

/**
 * @author ductn
 * @project The new institute
 * @created 03/03/2024 - 8:02 PM
 */
@RestController
@RequestMapping("/video")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "006. Video", description = "Video API")
public class VideoController extends AbstractBaseController{
    private final LessonService lessonService;
    private final VideoService videoService;

    @PreAuthorize("hasAuthority('TEACHER')")
    @RequestMapping(
            path = "/create",
            method = RequestMethod.POST,
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(
            summary = "Create a new course",
            security = @SecurityRequirement(name = SECURITY_SCHEME_NAME)
    )
    public ResponseEntity<?> createVideo(@ModelAttribute final CreateVideoLessonRequest request) {
        try {
            return videoService.createVideo(request);
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
    public ResponseEntity<?> updateVideoTitle (@PathVariable("id") final String id,
                                           @RequestBody(required = false) final String title) {
        try {
            return videoService.updateVideo(id,title);
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
    public ResponseEntity<?> deleteVideo(@RequestParam(required = true) final String id) {
        try {

            return videoService.deleteVideo(id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @GetMapping("/get-videos-by-id-unit")
    public ResponseEntity<?> getVideo(@Parameter(name = "id", description = "Unit ID", example = "00000000-0000-0000-0000-000000000001")
                                      @RequestParam(required = true) final UUID id) {
        try {
            return videoService.getVideoFromUnit(id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


}
