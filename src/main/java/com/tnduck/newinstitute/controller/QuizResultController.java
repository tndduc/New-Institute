package com.tnduck.newinstitute.controller;

import com.tnduck.newinstitute.dto.request.question.CreateQuestionRequest;
import com.tnduck.newinstitute.dto.request.quizResult.QuizResultRequest;
import com.tnduck.newinstitute.service.QuizResultService;
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
import org.springframework.web.bind.annotation.*;

import static com.tnduck.newinstitute.util.Constants.SECURITY_SCHEME_NAME;

/**
 * @author ductn
 * @project New-Institute
 * @created 13/05/2024
 */
@RestController
@RequestMapping("/quiz-results")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "010. Quiz Results", description = "QuizResults API")
public class QuizResultController extends AbstractBaseController{
    private final QuizResultService quizResultService;
    @PostMapping("/submit")
    @PreAuthorize("hasAuthority('USER')")
    @Operation(
            summary = "Submit Quiz Results",
            security = @SecurityRequirement(name = SECURITY_SCHEME_NAME)
    )
    public ResponseEntity<?> createLesson(
            @Parameter(description = "Request body to submit quiz results", required = true)
            @RequestBody @Valid QuizResultRequest request
    )  {
        try {
            return quizResultService.submit(request);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @PostMapping("/get")
    @PreAuthorize("hasAuthority('USER')")
    @Operation(
            summary = "Get quiz results by quiz id",
            security = @SecurityRequirement(name = SECURITY_SCHEME_NAME)
    )
    public ResponseEntity<?> getResult(@Parameter(name = "id", description = "Quiz ID", example = "00000000-0000-0000-0000-000000000001")
                                           @RequestParam(required = true) final String id)  {
        try {
            return quizResultService.getQuizResultByIDQuiz(id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
