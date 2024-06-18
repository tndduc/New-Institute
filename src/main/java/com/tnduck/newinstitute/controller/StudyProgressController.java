package com.tnduck.newinstitute.controller;

import com.tnduck.newinstitute.service.StudyProgressService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<?> updateStudyProgress(String unitID) {
        return studyProgressService.updateStudyProgress(unitID);
    }

    @RequestMapping("/get")
    @PreAuthorize("hasAuthority('USER')")
    @Operation(
            summary = "get study progress by id course",
            security = @SecurityRequirement(name = SECURITY_SCHEME_NAME)
    )
    public ResponseEntity<?> getStudyProgress(String IdCourse) {
        return studyProgressService.getStudyProgress(IdCourse);
    }
}
