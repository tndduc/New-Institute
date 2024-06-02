package com.tnduck.newinstitute.service;

import com.tnduck.newinstitute.dto.request.quiz.CreateQuizRequest;
import com.tnduck.newinstitute.dto.request.quiz.UpdateQuizRequest;
import com.tnduck.newinstitute.dto.response.quiz.QuizResponse;
import com.tnduck.newinstitute.entity.*;
import com.tnduck.newinstitute.repository.*;
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

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class QuizService {
    private final LessonRepository lessonRepository;
    private final QuizRepository quizRepository;
    private final UserService userService;
    private final CourseRepository courseRepository;
    private final VideoRepository videoRepository;
    private final UnitRepository unitRepository;
    public ResponseEntity<?> createQuiz(CreateQuizRequest createQuizRequest) {
        Optional<Lesson> lessonOptional = lessonRepository.findById(UUID.fromString(createQuizRequest.getIdLesson()));
        if (lessonOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Lesson not found");
        }
        List<Unit> unitList = unitRepository.findByLessonId(lessonOptional.get().getId());
        // Check if the user has access to the specified course
        Optional<Course> courseOptional = courseRepository.findById(lessonOptional.get().getCourse().getId());
        User teacher = userService.getUser();
        if (teacher == null || courseOptional.isEmpty() || !teacher.getId().equals(courseOptional.get().getUser().getId())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized access or invalid course");
        }
        int ordinal = unitList.size()+1;

        // Create a new unit with the provided details
        Unit unit = new Unit();
        unit.setTitle(createQuizRequest.getTitle());
        unit.setType("quiz");
        unit.setOrdinalNumber(ordinal);
        unit.setLesson(lessonOptional.get());

        Unit unitSave = unitRepository.save(unit);
        boolean isFinal = false;
        if (createQuizRequest.equals("true")){
            isFinal = true;
        }
        Quiz quiz = Quiz.builder()
                .description(createQuizRequest.getDescription())
                .isFinalExam(isFinal)
                .title(createQuizRequest.getTitle())
                .unit(unitSave)
                .build();
        return ResponseEntity.ok(QuizResponse.convert(quizRepository.save(quiz)));
    }
    public Quiz getQuizByIdUnit(UUID uuid) {
        Optional<Unit> unit = unitRepository.findById(uuid);
        if (unit.isEmpty()) {
            return null;
        }
        Optional<Quiz> quiz = quizRepository.findByUnitId(uuid);
        return quiz.orElse(null);
    }
    public ResponseEntity<?> getQuizByFromIDUnit(UUID id) {
        Optional<Unit> unitOptional = unitRepository.findById(id);
        if (unitOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Lesson not found");
        }
        Optional<Quiz> quiz = quizRepository.findByUnitId(id);
        if (quiz.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Quiz not found");
        }
        return ResponseEntity.ok(QuizResponse.convert(quiz.get()));
    }
    public ResponseEntity<?> updateQuiz(UpdateQuizRequest updateQuizRequest) {
        Optional<Quiz> quizOptional = quizRepository.findById(UUID.fromString(updateQuizRequest.getId()));
        if (quizOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Quiz not found");
        }
        User teacher = userService.getUser();
        if (teacher == null || !teacher.getId().equals(quizOptional.get().getUnit().getLesson().getCourse().getUser().getId())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized access");
        }
        Quiz quizToUpdate = quizOptional.get();

        if (updateQuizRequest.getTitle() != null) {
            quizToUpdate.setTitle(updateQuizRequest.getTitle());
        }
        if (updateQuizRequest.getDescription() != null) {
            quizToUpdate.setDescription(updateQuizRequest.getDescription());
        }
        boolean isFinal = false;
        if (updateQuizRequest.getIsFinalExam()!= null) {
          if (updateQuizRequest.getIsFinalExam().equals("true")) {
              isFinal = true;
          }
            quizToUpdate.setFinalExam(isFinal);

        }


        // Save the updated quiz
        Quiz updatedQuiz = quizRepository.save(quizToUpdate);
        return ResponseEntity.ok(QuizResponse.convert(updatedQuiz));
    }
    public ResponseEntity<?> deleteQuiz(UUID quizId) {
        Optional<Quiz> quizOptional = quizRepository.findById(quizId);
        if (quizOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Quiz not found");
        }
        User teacher = userService.getUser();
        if (teacher == null || !teacher.getId().equals(quizOptional.get().getUnit().getLesson().getCourse().getUser().getId())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized access");
        }
        quizRepository.deleteById(quizId);
        return ResponseEntity.ok("Quiz deleted successfully");
    }

}
