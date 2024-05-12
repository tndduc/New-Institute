//package com.tnduck.newinstitute.service;
//
//import com.tnduck.newinstitute.dto.request.question.CreateQuestionRequest;
//import com.tnduck.newinstitute.dto.request.quiz.CreateQuizRequest;
//import com.tnduck.newinstitute.dto.response.quiz.QuizResponse;
//import com.tnduck.newinstitute.entity.Course;
//import com.tnduck.newinstitute.entity.Lesson;
//import com.tnduck.newinstitute.entity.Quiz;
//import com.tnduck.newinstitute.entity.User;
//import com.tnduck.newinstitute.repository.CourseRepository;
//import com.tnduck.newinstitute.repository.LessonRepository;
//import com.tnduck.newinstitute.repository.QuizRepository;
//import jakarta.transaction.Transactional;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Service;
//
//import java.util.Optional;
//import java.util.UUID;
//
//@Service
//@RequiredArgsConstructor
//@Transactional
//@Slf4j
//public class QuestionService {
//    private final LessonRepository lessonRepository;
//    private final QuizRepository quizRepository;
//    private final UserService userService;
//    private final CourseRepository courseRepository;
//    public ResponseEntity<?> createQuestion(CreateQuestionRequest createQuestionRequest) {
//        Optional<Lesson> lessonOptional = lessonRepository.findById(UUID.fromString(createQuizRequest.getIdLesson()));
//        if (lessonOptional.isEmpty()) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Lesson not found");
//        }
//        Optional<Course> courseOptional = courseRepository.findById(lessonOptional.get().getCourse().getId());
//        User teacher = userService.getUser();
//        if (teacher == null || courseOptional.isEmpty() || !teacher.getId().equals(courseOptional.get().getUser().getId())) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized access or invalid course");
//        }
//        Quiz quiz = Quiz.builder()
//                .description(createQuizRequest.getDescription())
//                .isFinalExam(createQuizRequest.isFinalExam())
//                .title(createQuizRequest.getTitle())
//                .lesson(lessonOptional.get())
//                .build();
//        return ResponseEntity.ok(QuizResponse.convert(quizRepository.save(quiz)));
//    }
//}
