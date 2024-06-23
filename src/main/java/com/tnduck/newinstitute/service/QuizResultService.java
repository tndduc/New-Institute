package com.tnduck.newinstitute.service;

import com.tnduck.newinstitute.dto.request.quizResult.QuizResultRequest;
import com.tnduck.newinstitute.dto.response.quizResult.AnswerResultResponse;
import com.tnduck.newinstitute.dto.response.quizResult.BaseQuizResultResponse;
import com.tnduck.newinstitute.entity.*;
import com.tnduck.newinstitute.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class QuizResultService {
    private final CertificateRepository certificateRepository;
    private final UnitRepository unitRepository;
    private final QuizResultRepository quizResultRepository;
    private final QuizRepository quizRepository;
    private final UserService userService;
    private final QuestionRepository questionRepository;
    private final CourseRepository courseRepository;
    private final ChoiceRepository choiceRepository;

    public ResponseEntity<?> submit(QuizResultRequest request) {
        Optional<Quiz> quizOptional = quizRepository.findById(UUID.fromString(request.getQuizId()));
        if (quizOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Quiz not found");
        }
        User learner = userService.getUser();
        if (learner == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("U need to login");
        List<Question> questionList = questionRepository.findListByIdQuiz(quizOptional.get().getId());
        List<AnswerResultResponse> AnswerResultsFromDB = new ArrayList<>();

        for (Question question : questionList) {
            List<Choice> choices = choiceRepository.findListByIdQuestion(question.getId());
            AnswerResultsFromDB.addAll(AnswerResultResponse.convert(choices, true));
        }
        List<AnswerResultResponse> correctAnswerResultsFromDB = new ArrayList<>();
        for (AnswerResultResponse answer : AnswerResultsFromDB){
            if (answer.getIsTrue()){
                correctAnswerResultsFromDB.add(answer);
            }
        }
        // Prepare a list to store user's answers with isTrue set to false by default
        List<AnswerResultResponse> userAnswers = request.getQuestionResultRequestList().stream()
                .flatMap(questionResultRequest -> questionResultRequest.getAnswerResults().stream())
                .map(answerResultRequest -> AnswerResultResponse.builder()
                        .answerId(answerResultRequest.getAnswerId())
                        .build())
                .collect(Collectors.toList());
        List<Choice> choices = new ArrayList<>();
        // Iterate through user answers and set isTrue based on comparison with correct answers
        for (AnswerResultResponse userAnswer : userAnswers) {
            Optional<Choice> choice = choiceRepository.findById(UUID.fromString(userAnswer.getAnswerId()));
            if (choice.isPresent()) {
                choices.add(choice.get());
            }
            for (AnswerResultResponse correctAnswer : correctAnswerResultsFromDB) {
                if (userAnswer.getAnswerId().equals(correctAnswer.getAnswerId())) {
                    userAnswer.setIsTrue(true);
                    break;
                }
            }
        }
        List<AnswerResultResponse> finalAnswer = new ArrayList<>();
        for (AnswerResultResponse finalAnswerResponse : userAnswers){
            if (finalAnswerResponse.getIsTrue()){
                finalAnswer.add(finalAnswerResponse);
            }
        }
        Course course = quizOptional.get().getUnit().getLesson().getCourse();


        QuizResult quizResult = QuizResult.builder()
                .quiz(quizOptional.get())
                .score(finalAnswer.size())
                .user(learner)
                .choices(choices)
                .build();
        QuizResult quizResultSave = quizResultRepository.save(quizResult);
        Boolean isFinal = false;
        Boolean isPass = true;
        String score = finalAnswer.size() + "/" + correctAnswerResultsFromDB.size();
        if (quizOptional.get().isFinalExam()){
            isFinal = true;
            isPass = false;
            if(finalAnswer.size() > correctAnswerResultsFromDB.size() /2.0){
                isPass = true;
                log.warn("User answers size is greater than 50% of correct answers size.");
                Certificate certificate = Certificate.builder()
                        .expiryDate(LocalDateTime.now().plusYears(1))
                        .issueDate(LocalDateTime.now())
                        .course(course)
                        .user(learner)
                        .quizResult(quizResultSave)
                        .score(score)
                        .build();
                Certificate certificateSave = certificateRepository.save(certificate);
                BaseQuizResultResponse baseQuizResultResponse = BaseQuizResultResponse.builder()
                        .idUser(learner.getId().toString())
                        .idCourse(course.getId().toString())
                        .isPass(isPass)
                        .score(score)
                        .isFinal(isFinal)
                        .build();
                return ResponseEntity.ok(baseQuizResultResponse);
            }
            BaseQuizResultResponse baseQuizResultResponse = BaseQuizResultResponse.builder()
                    .isPass(isPass)
                    .idUser(learner.getId().toString())
                    .idCourse(course.getId().toString())
                    .score(score)
                    .isFinal(isFinal)
                    .build();
            return ResponseEntity.ok(baseQuizResultResponse);
        } BaseQuizResultResponse baseQuizResultResponse = BaseQuizResultResponse.builder()
                .isPass(isPass)
                .idUser(learner.getId().toString())
                .idCourse(course.getId().toString())
                .score(score)
                .isFinal(isFinal)
                .build();

        return ResponseEntity.ok(baseQuizResultResponse); // Or any other relevant response
    }
    public ResponseEntity<?> getQuizResultByIDQuiz(String idQuiz) {
        Optional<Quiz> quizOptional = quizRepository.findById(UUID.fromString(idQuiz));
        if (quizOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Quiz not found");
        }
        User user = userService.getUser();
        QuizResult quizResult = quizResultRepository.findByUser_IdAndQuiz_Id(user.getId(), quizOptional.get().getId());
        List<AnswerResultResponse> answerResultResponses = AnswerResultResponse.convert(quizResult.getChoices(),true);
        return ResponseEntity.ok(answerResultResponses);
    }
}
