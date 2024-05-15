package com.tnduck.newinstitute.controller;

import com.tnduck.newinstitute.dto.request.choice.CreateChoiceRequest;
import com.tnduck.newinstitute.dto.request.choice.UpdateChoiceRequest;
import com.tnduck.newinstitute.dto.request.lesson.LessonRequest;
import com.tnduck.newinstitute.service.ChoiceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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
 * @project New-Institute
 * @created 13/05/2024
 */
@RestController
@RequestMapping("/choice")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "009. Choice", description = "Choice API")
public class ChoiceController extends AbstractBaseController{
    private final ChoiceService choiceService;
    @PostMapping("/create")
    @PreAuthorize("hasAuthority('TEACHER')")
    @Operation(
            summary = "Create a new Choice",
            security = @SecurityRequirement(name = SECURITY_SCHEME_NAME)
    )
    public ResponseEntity<?> createChoice( @Parameter(description = "Request body to update Choice", required = true)
                                               @RequestBody @Valid CreateChoiceRequest request) {
        try {
            return choiceService.createChoice(request);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @PostMapping("/update")
    @PreAuthorize("hasAuthority('TEACHER')")
    @Operation(
            summary = "Update a Choice",
            security = @SecurityRequirement(name = SECURITY_SCHEME_NAME)
    )
    public ResponseEntity<?> updateChoice(@Parameter(description = "Request body to update Choice", required = true)
                                              @RequestBody @Valid  UpdateChoiceRequest request) {
        try {
            return choiceService.updateChoice(request);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @PreAuthorize("hasAuthority('TEACHER')")
    @RequestMapping(
            path = "/update",
            method = RequestMethod.DELETE)
    @Operation(
            summary = "Create a new Lesson",
            security = @SecurityRequirement(name = SECURITY_SCHEME_NAME)
    )
    public ResponseEntity<?> updateChoice(@RequestBody final UUID id) {
        try {
            return choiceService.deleteChoice(id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @GetMapping("/getChoiceById")
    public ResponseEntity<?> getChoiceById(@Parameter(name = "id", description = "Choice ID", example = "00000000-0000-0000-0000-000000000001")
                                       @RequestParam(required = true) final UUID id) {
        try {
            return choiceService.getChoiceById(id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @GetMapping("/getChoiceByIdQuestion")
    public ResponseEntity<?> getChoiceByIdQuestion(@Parameter(name = "id", description = "Question ID", example = "00000000-0000-0000-0000-000000000001")
                                           @RequestParam(required = true) final UUID id) {
        try {
            return choiceService.getChoiceByIdQuestion(id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


}
