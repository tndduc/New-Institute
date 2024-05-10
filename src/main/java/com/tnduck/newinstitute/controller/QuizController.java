package com.tnduck.newinstitute.controller;

import com.tnduck.newinstitute.dto.request.quiz.CreateQuizRequest;
import com.tnduck.newinstitute.dto.request.quiz.UpdateQuizRequest;
import com.tnduck.newinstitute.service.QuizService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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

@RestController
@RequestMapping("/quiz")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "006. Quiz", description = "Quiz API")
public class QuizController  extends AbstractBaseController {
    private final QuizService quizService;
    @PreAuthorize("hasAuthority('TEACHER')")
    @RequestMapping(
            path = "/create",
            method = RequestMethod.POST,
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(
            summary = "create a quiz",
            security = @SecurityRequirement(name = SECURITY_SCHEME_NAME)
    )
    public ResponseEntity<?> createQuiz(@ModelAttribute final CreateQuizRequest createQuizRequest) {
        try {
            return ResponseEntity.ok(quizService.createQuiz(createQuizRequest));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @PreAuthorize("hasAuthority('TEACHER')")
    @RequestMapping(
            path = "/updateQuiz",
            method = RequestMethod.PUT,
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(
            summary = "create a quiz",
            security = @SecurityRequirement(name = SECURITY_SCHEME_NAME)
    )
    public ResponseEntity<?> updateQuiz(@ModelAttribute final UpdateQuizRequest updateQuizRequest) {
        try {
            return ResponseEntity.ok(quizService.updateQuiz(updateQuizRequest));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/getListQuizByIdLesson")
    public ResponseEntity<?> getListQuizByIdLesson(@RequestParam(required = true) final String id) {
        try {

            return ResponseEntity.ok(quizService.getQuizByIdLesson(UUID.fromString(id)));
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

            return ResponseEntity.ok(quizService.deleteQuiz(UUID.fromString(id)));
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
