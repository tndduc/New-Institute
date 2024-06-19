package com.tnduck.newinstitute.controller;

import com.tnduck.newinstitute.service.StudyProgressService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

import static com.tnduck.newinstitute.util.Constants.SECURITY_SCHEME_NAME;

@RestController
@RequestMapping("/study-progress")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "014. Study Progress", description = "Study Progress API")
public class StudyProgressController {
    private final StudyProgressService studyProgressService;
    @RequestMapping("/update")
    @PreAuthorize("hasAuthority('USER')")
    @Operation(
            summary = "Update study progress",
            security = @SecurityRequirement(name = SECURITY_SCHEME_NAME)
    )
    public ResponseEntity<?> updateStudyProgress(@Parameter(name = "id", description = "Unit ID", example = "00000000-0000-0000-0000-000000000001")
                                                     @RequestParam(required = true) final String id) {
        return studyProgressService.updateStudyProgress(id);
    }

    @RequestMapping("/get")
    @PreAuthorize("hasAuthority('USER')")
    @Operation(
            summary = "get study progress by id course",
            security = @SecurityRequirement(name = SECURITY_SCHEME_NAME)
    )
    public ResponseEntity<?> getStudyProgress(@Parameter(name = "id", description = "Course ID", example = "00000000-0000-0000-0000-000000000001")
                                                  @RequestParam(required = true) final String id) {
        return studyProgressService.getStudyProgress(id);
    }
}
