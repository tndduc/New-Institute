package com.tnduck.newinstitute.service;

import com.tnduck.newinstitute.dto.request.choice.CreateChoiceRequest;
import com.tnduck.newinstitute.dto.request.choice.UpdateChoiceRequest;
import com.tnduck.newinstitute.dto.response.choice.ChoiceResponse;
import com.tnduck.newinstitute.entity.Choice;
import com.tnduck.newinstitute.entity.Course;
import com.tnduck.newinstitute.entity.Question;
import com.tnduck.newinstitute.entity.User;
import com.tnduck.newinstitute.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.protocol.HTTP;
import org.springframework.data.repository.core.RepositoryCreationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ChoiceService {
    private final LessonRepository lessonRepository;
    private final QuizRepository quizRepository;
    private final UserService userService;
    private final QuestionRepository questionRepository;
    private final CourseRepository courseRepository;
    private final ChoiceRepository choiceRepository;

    public ResponseEntity<?> createChoice(CreateChoiceRequest createChoiceRequest) {
        Optional<Question> question = questionRepository.findById(UUID.fromString(createChoiceRequest.getIdQuestion()));
        if (question.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Question not found");
        }
        Optional<Course> courseOptional = courseRepository.findById(question.get().getQuiz().getUnit().getLesson().getCourse().getId());
        User teacher = userService.getUser();
        if (teacher == null || courseOptional.isEmpty() || !teacher.getId().equals(courseOptional.get().getUser().getId())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized access or invalid course");
        }
        Choice choice = Choice.builder()
                .question(question.get())
                .content(createChoiceRequest.getContent())
                .isCorrect(createChoiceRequest.isCorrect())
                .build();

        return ResponseEntity.ok(ChoiceResponse.convert(choiceRepository.save(choice),choice.isCorrect()));
    }
    public ResponseEntity<?> updateChoice(UpdateChoiceRequest updateChoiceRequest){
        Optional<Choice> choiceOptional = choiceRepository.findById(UUID.fromString(updateChoiceRequest.getIdChoice()));
        if (choiceOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Choice not found");
        }
        Optional<Course> courseOptional = courseRepository.findById(choiceOptional.get().getQuestion().getQuiz().getUnit().getLesson().getCourse().getId());
        User teacher = userService.getUser();
        if (teacher == null || courseOptional.isEmpty() || !teacher.getId().equals(courseOptional.get().getUser().getId())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized access or invalid course");
        }
        Choice choice = choiceOptional.get();
        if (updateChoiceRequest.getContent() != null) {
            choice.setContent(updateChoiceRequest.getContent());
        }
        choice.setCorrect(updateChoiceRequest.isCorrect());
        try {
            // Attempt to save the choice
            Choice choiceUpdate = choiceRepository.save(choice);
            // If saved successfully, return the updated choice
            return ResponseEntity.ok(ChoiceResponse.convert(choiceUpdate,choiceUpdate.isCorrect()));
        } catch (Exception e) {
            // If an exception occurs during the save operation, handle it
            // Here, you can log the exception for debugging purposes
            log.error("Failed to update choice: {}", e.getMessage());
            // Return an appropriate error response
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to update choice: " + e.getMessage());
        }

    }
    public ResponseEntity<?> getChoiceByIdQuestion(UUID questionId){
        Optional<Question> question = questionRepository.findById(questionId);
        if (question.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Question not found");
        }
        List<Choice> choices = choiceRepository.findListByIdQuestion(questionId);
        List<ChoiceResponse> responses = new ArrayList<>();
        for (Choice choice : choices) {
            responses.add(ChoiceResponse.convert(choice));
        }
        return ResponseEntity.ok(responses);
    }
    public ResponseEntity<?> getChoiceById(UUID choiceId){
        Optional<Choice> choice = choiceRepository.findById(choiceId);
        if (choice.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Choice not found");
        }
        return ResponseEntity.ok(ChoiceResponse.convert(choice.get()));
    }
    public ResponseEntity<?> deleteChoice(UUID choiceId){
        Optional<Choice> choiceOptional = choiceRepository.findById(choiceId);
        if (choiceOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Choice not found");
        }
        Optional<Course> courseOptional = courseRepository.findById(choiceOptional.get().getQuestion().getQuiz().getUnit().getLesson().getCourse().getId());
        User teacher = userService.getUser();
        if (teacher == null || courseOptional.isEmpty() || !teacher.getId().equals(courseOptional.get().getUser().getId())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized access or invalid course");
        }
        choiceRepository.delete(choiceOptional.get());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
