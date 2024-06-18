package com.tnduck.newinstitute.controller;

import com.tnduck.newinstitute.dto.request.lesson.LessonRequest;
import com.tnduck.newinstitute.dto.request.unit.CreateUnitRequest;
import com.tnduck.newinstitute.dto.request.unit.UpdateUnitRequest;
import com.tnduck.newinstitute.dto.response.auth.TokenResponse;
import com.tnduck.newinstitute.service.LessonService;
import com.tnduck.newinstitute.service.UnitService;
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
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static com.tnduck.newinstitute.util.Constants.SECURITY_SCHEME_NAME;

@RestController
@RequestMapping("/unit")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "005. Unit", description = "Unit API")
public class UnitController  extends AbstractBaseController{
    private final UnitService unitService;

//    @PostMapping("/create")
//    @PreAuthorize("hasAuthority('TEACHER')")
//    @Operation(
//            summary = "Create a new Unit",
//            security = @SecurityRequirement(name = SECURITY_SCHEME_NAME)
//    )
//    public ResponseEntity<?> createUnit ( @Parameter(description = "Request body to create Unit", required = true)
//                                              @RequestBody @Valid CreateUnitRequest request) {
//        try {
//            return unitService.createUnit(request);
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//    }

    @GetMapping("/get-units")
    public ResponseEntity<?> getUnit(@Parameter(name = "id", description = "Lesson ID", example = "00000000-0000-0000-0000-000000000001")
                                       @RequestParam(required = true) final UUID id) {
        try {
            return unitService.getUnitByIdLesson(id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @PostMapping("/update")
    @PreAuthorize("hasAuthority('TEACHER')")
    @Operation(
            summary = "Update a new Unit",
            security = @SecurityRequirement(name = SECURITY_SCHEME_NAME)
    )
    public ResponseEntity<?> updateUnit ( @Parameter(description = "Request body to update Unit", required = true)
                                              @RequestBody @Valid  UpdateUnitRequest request) {
        try {
            return unitService.updateUnit(request);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @PreAuthorize("hasAuthority('TEACHER')")
    @RequestMapping(
            path = "/delete",
            method = RequestMethod.DELETE)
    @Operation(
            summary = "delete a unit",
            security = @SecurityRequirement(name = SECURITY_SCHEME_NAME)
    )
    public ResponseEntity<?> deleteUnit(@RequestParam(required = true) final String id) {
        try {

            return unitService.deleteUnit(UUID.fromString(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
