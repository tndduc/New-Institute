package com.tnduck.newinstitute.controller;

import com.tnduck.newinstitute.dto.request.quiz.CreateQuizRequest;
import com.tnduck.newinstitute.dto.request.quiz.UpdateQuizRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import static com.tnduck.newinstitute.util.Constants.SECURITY_SCHEME_NAME;

@RestController
@RequestMapping("/question")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "007. Question", description = "Question API")
public class QuestionController  extends AbstractBaseController {
//    @PreAuthorize("hasAuthority('TEACHER')")
//    @RequestMapping(
//            path = "/create",
//            method = RequestMethod.POST,
//            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    @Operation(
//            summary = "create a quiz",
//            security = @SecurityRequirement(name = SECURITY_SCHEME_NAME)
//    )
//    public ResponseEntity<?> createQuiz(@ModelAttribute final UpdateQuizRequest createQuizRequest) {
//        try {
//            return ResponseEntity.ok(quizService.createQuiz(createQuizRequest));
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//    }
}
