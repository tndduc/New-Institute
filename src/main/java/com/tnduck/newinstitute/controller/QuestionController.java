package com.tnduck.newinstitute.controller;

import com.tnduck.newinstitute.dto.request.lesson.LessonRequest;
import com.tnduck.newinstitute.dto.request.question.CreateQuestionRequest;
import com.tnduck.newinstitute.dto.request.question.UpdateQuestionRequest;
import com.tnduck.newinstitute.dto.request.quiz.CreateQuizRequest;
import com.tnduck.newinstitute.dto.request.quiz.UpdateQuizRequest;
import com.tnduck.newinstitute.service.QuestionService;
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
@RequestMapping("/question")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "008. Question", description = "Question API")
public class QuestionController  extends AbstractBaseController {
    private final QuestionService questionService;
    @PostMapping("/create")
    @PreAuthorize("hasAuthority('TEACHER')")
    @Operation(
            summary = "Create a new Lesson",
            security = @SecurityRequirement(name = SECURITY_SCHEME_NAME)
    )
    public ResponseEntity<?> createLesson(
            @Parameter(description = "Request body to create Lesson", required = true)
            @RequestBody @Valid CreateQuestionRequest request
    )  {
        try {
            return questionService.createQuestion(request);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/update")
    @PreAuthorize("hasAuthority('TEACHER')")
    @Operation(
            summary = "Create a new Question",
            security = @SecurityRequirement(name = SECURITY_SCHEME_NAME)
    )
    public ResponseEntity<?> updateQuestion(
            @Parameter(description = "Request body to create Question", required = true)
            @RequestBody @Valid UpdateQuestionRequest request
    )  {
        try {
            return questionService.updateQuestion(request);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/get-by-id")
    public ResponseEntity<?> getQuestionById(@Parameter(name = "id", description = "Course ID", example = "00000000-0000-0000-0000-000000000001")
                                          @RequestParam(required = true) final UUID id){
        try {
            return questionService.getQuestionById(id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/get-by-quiz-id")
    public ResponseEntity<?> getQuestionByIdQuiz(@Parameter(name = "id", description = "Course ID", example = "00000000-0000-0000-0000-000000000001")
                                             @RequestParam(required = true) final UUID id){
        try {
            return questionService.getListQuestionByIdQuiz(id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
