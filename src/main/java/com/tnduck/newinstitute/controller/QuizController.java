package com.tnduck.newinstitute.controller;

import com.tnduck.newinstitute.dto.request.quiz.CreateGroupQuizRequest;
import com.tnduck.newinstitute.dto.request.quiz.CreateQuizRequest;
import com.tnduck.newinstitute.dto.request.quiz.UpdateQuizRequest;
import com.tnduck.newinstitute.service.QuizService;
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

@RestController
@RequestMapping("/quiz")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "007. Quiz", description = "Quiz API")
public class QuizController  extends AbstractBaseController {
    private final QuizService quizService;
    @PostMapping("/create")
    @PreAuthorize("hasAuthority('TEACHER')")
    @Operation(
            summary = "Create a new Quiz",
            security = @SecurityRequirement(name = SECURITY_SCHEME_NAME)
    )
    public ResponseEntity<?> createQuiz( @Parameter(description = "Request body to create Quiz", required = true)
                                             @RequestBody @Valid CreateQuizRequest createQuizRequest) {
        try {
            return quizService.createQuiz(createQuizRequest);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @PostMapping("/create-group")
    @PreAuthorize("hasAuthority('TEACHER')")
    @Operation(
            summary = "Create a new group Quiz",
            security = @SecurityRequirement(name = SECURITY_SCHEME_NAME)
    )
    public ResponseEntity<?> createGroupQuiz( @Parameter(description = "Request body to create Quiz", required = true)
                                         @RequestBody @Valid CreateGroupQuizRequest createGroupQuizRequest) {
        try {
            return quizService.createGroupQuiz(createGroupQuizRequest);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @PostMapping("/update")
    @PreAuthorize("hasAuthority('TEACHER')")
    @Operation(
            summary = "Update a new Quiz",
            security = @SecurityRequirement(name = SECURITY_SCHEME_NAME)
    )
    public ResponseEntity<?> updateQuiz( @Parameter(description = "Request body to update Quiz", required = true)
                                             @RequestBody @Valid UpdateQuizRequest updateQuizRequest) {
        try {
            return quizService.updateQuiz(updateQuizRequest);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/getListQuizByIdUnit")
    public ResponseEntity<?> getListQuizByIdLesson(@RequestParam(required = true) final String id) {
        try {

            return quizService.getQuizByFromIDUnit(UUID.fromString(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @PreAuthorize("hasAuthority('TEACHER')")
    @RequestMapping(
            path = "/delete",
            method = RequestMethod.DELETE)
    @Operation(
            summary = "delete a quiz",
            security = @SecurityRequirement(name = SECURITY_SCHEME_NAME)
    )
    public ResponseEntity<?> deleteLesson(@RequestParam(required = true) final String id) {
        try {

            return quizService.deleteQuiz(UUID.fromString(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    //    @PreAuthorize("hasAnyAuthority('TEACHER', 'ADMIN', 'USER')")
//    @RequestMapping(
//            path = "/getListQuizByIdLesson",
//            method = RequestMethod.POST)
//    @Operation(
//            summary = "delete a lesson",
//            security = @SecurityRequirement(name = SECURITY_SCHEME_NAME)
//    )
}
