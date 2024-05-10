package com.tnduck.newinstitute.service;

import com.tnduck.newinstitute.dto.request.quiz.CreateQuizRequest;
import com.tnduck.newinstitute.dto.request.quiz.UpdateQuizRequest;
import com.tnduck.newinstitute.dto.response.quiz.QuizResponse;
import com.tnduck.newinstitute.entity.Course;
import com.tnduck.newinstitute.entity.Lesson;
import com.tnduck.newinstitute.entity.Quiz;
import com.tnduck.newinstitute.entity.User;
import com.tnduck.newinstitute.repository.CourseRepository;
import com.tnduck.newinstitute.repository.LessonRepository;
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

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class QuizService {
    private final LessonRepository lessonRepository;
    private final QuizRepository quizRepository;
    private final UserService userService;
    private final CourseRepository courseRepository;
    public ResponseEntity<?> createQuiz(CreateQuizRequest createQuizRequest) {
        Optional<Lesson> lessonOptional = lessonRepository.findById(UUID.fromString(createQuizRequest.getIdLesson()));
        if (lessonOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Lesson not found");
        }
        Optional<Course> courseOptional = courseRepository.findById(lessonOptional.get().getCourse().getId());
        User teacher = userService.getUser();
        if (teacher == null || courseOptional.isEmpty() || !teacher.getId().equals(courseOptional.get().getUser().getId())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized access or invalid course");
        }
        Quiz quiz = Quiz.builder()
                .description(createQuizRequest.getDescription())
                .isFinalExam(createQuizRequest.isFinalExam())
                .title(createQuizRequest.getTitle())
                .lesson(lessonOptional.get())
                .build();
        return ResponseEntity.ok(QuizResponse.convert(quizRepository.save(quiz)));
    }

    public ResponseEntity<?> getQuizByIdLesson(UUID id) {
        Optional<Lesson> lessonOptional = lessonRepository.findById(id);
        if (lessonOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Lesson not found");
        }
        List<Quiz> quizList = quizRepository.findByLessonId(id);
        List<QuizResponse> quizResponsesList = new ArrayList<>();
        for (Quiz quiz : quizList) {
            quizResponsesList.add(QuizResponse.convert(quiz));
        }
        return ResponseEntity.ok(quizResponsesList);
    }
    public ResponseEntity<?> updateQuiz(UpdateQuizRequest updateQuizRequest) {
        Optional<Quiz> quizOptional = quizRepository.findById(UUID.fromString(updateQuizRequest.getId()));
        if (quizOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Quiz not found");
        }
        User teacher = userService.getUser();
        if (teacher == null || !teacher.getId().equals(quizOptional.get().getLesson().getCourse().getUser().getId())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized access");
        }
        Quiz quizToUpdate = quizOptional.get();

        if (updateQuizRequest.getTitle() != null) {
            quizToUpdate.setTitle(updateQuizRequest.getTitle());
        }
        if (updateQuizRequest.getDescription() != null) {
            quizToUpdate.setDescription(updateQuizRequest.getDescription());
        }

        quizToUpdate.setFinalExam(updateQuizRequest.isFinalExam());


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
        if (teacher == null || !teacher.getId().equals(quizOptional.get().getLesson().getCourse().getUser().getId())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized access");
        }
        quizRepository.deleteById(quizId);
        return ResponseEntity.ok("Quiz deleted successfully");
    }

}
