package com.tnduck.newinstitute.service;

import com.tnduck.newinstitute.dto.request.quizResult.QuizResultRequest;
import com.tnduck.newinstitute.dto.response.quizResult.AnswerResultResponse;
import com.tnduck.newinstitute.entity.Choice;
import com.tnduck.newinstitute.entity.Question;
import com.tnduck.newinstitute.entity.Quiz;
import com.tnduck.newinstitute.entity.User;
import com.tnduck.newinstitute.repository.ChoiceRepository;
import com.tnduck.newinstitute.repository.CourseRepository;
import com.tnduck.newinstitute.repository.QuestionRepository;
import com.tnduck.newinstitute.repository.QuizRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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

        // Iterate through user answers and set isTrue based on comparison with correct answers
        for (AnswerResultResponse userAnswer : userAnswers) {
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

        return ResponseEntity.ok("Submitted successfully"); // Or any other relevant response
    }

}
