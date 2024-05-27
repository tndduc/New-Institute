package com.tnduck.newinstitute.service;

import com.tnduck.newinstitute.dto.request.question.CreateQuestionRequest;
import com.tnduck.newinstitute.dto.request.question.UpdateQuestionRequest;
import com.tnduck.newinstitute.dto.request.quiz.CreateQuizRequest;
import com.tnduck.newinstitute.dto.response.question.QuestionResponse;
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
public class QuestionService {
    private final QuizRepository quizRepository;
    private final UserService userService;
    private final QuestionRepository questionRepository;
    private final CourseRepository courseRepository;
    private final ChoiceRepository choiceRepository;

    /**
     * Creates a new question for a given quiz.
     *
     * @param createQuestionRequest the request containing the question details
     * @return a response containing the created question
     */
    public ResponseEntity<?> createQuestion(CreateQuestionRequest createQuestionRequest) {
        // Find the quiz by its unit ID
        Optional<Quiz> quizOptional = quizRepository.findById(UUID.fromString(createQuestionRequest.getIdQuiz()));
        if (quizOptional.isEmpty()) {
            // If the quiz is not found, return a NOT_FOUND response
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Quiz not found");
        }

        // Find the course associated with the quiz
        Optional<Course> courseOptional = courseRepository.findById(quizOptional.get().getUnit().getLesson().getCourse().getId());
        User teacher = userService.getUser();
        if (teacher == null || courseOptional.isEmpty() || !teacher.getId().equals(courseOptional.get().getUser().getId())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized access or invalid course");
        }

        // Create a new question with the provided details and associate it with the given quiz
        Question question = new Question().builder().content(createQuestionRequest.getContent()).level(createQuestionRequest.getLevel()).description(createQuestionRequest.getDescription()).quiz(quizOptional.get()).build();

        // Save the newly created question to the database Return a response containing the created question
        return ResponseEntity.ok(QuestionResponse.convert(questionRepository.save(question)));
    }

    /**
     * Updates an existing question.
     *
     * @param updateQuestionRequest the request containing the updated question details
     * @return a response containing the updated question
     */

    public ResponseEntity<?> updateQuestion(UpdateQuestionRequest updateQuestionRequest) {
        // Find the question by its ID
        Optional<Question> questionOptional = questionRepository.findById(UUID.fromString(updateQuestionRequest.getId()));
        if (questionOptional.isEmpty()) {
            // If the question is not found, return a NOT_FOUND response
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Question not found");
        }
        // Get the question object
        Question question = questionOptional.get();
        // Check if the logged-in user is authorized to update the question
        Optional<Course> courseOptional = courseRepository.findById(question.getQuiz().getUnit().getLesson().getCourse().getId());
        User teacher = userService.getUser();
        if (teacher == null || courseOptional.isEmpty() || !teacher.getId().equals(courseOptional.get().getUser().getId())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized access or invalid course");
        }
        // Update the question content if provided
        if (updateQuestionRequest.getContent() != null) {
            question.setContent(updateQuestionRequest.getContent());
        }
        // Update the question description if provided
        if (updateQuestionRequest.getDescription() != null) {
            question.setDescription(updateQuestionRequest.getDescription());
        }
        // Update the question level if provided
        if (updateQuestionRequest.getLevel() > 0) {
            question.setLevel(updateQuestionRequest.getLevel());
        }
        // Save the updated question
        Question questionUpdate = questionRepository.save(question);
        return ResponseEntity.ok(QuestionResponse.convert(questionUpdate));
    }

    /**
     * Retrieves a list of questions associated with a given quiz.
     *
     * @param uuid the unique identifier of the quiz
     * @return a response containing a list of questions associated with the given quiz
     */
    public ResponseEntity<?> getListQuestionByIdQuiz(UUID uuid) {
        // Find the quiz by its unique identifier
        Optional<Quiz> quizOptional = quizRepository.findById(uuid);
        if (quizOptional.isEmpty()) {
            // If the quiz is not found, return a NOT_FOUND response
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Quiz not found");
        }

        // Retrieve the list of questions associated with the given quiz
        List<Question> questions = questionRepository.findListByIdQuiz(quizOptional.get().getId());

        // Create a list of responses containing the retrieved questions
        List<QuestionResponse> responses = new ArrayList<>();
        for (Question question : questions) {
            // Retrieve the list of choices associated with the given question
            List<Choice> choice = choiceRepository.findListByIdQuestion(question.getId());

            // Convert the question and its choices to a response format
            responses.add(QuestionResponse.convert(question, choice));
        }

        // Return a response containing the list of questions and their choices
        return ResponseEntity.ok(responses);
    }

    /**
     * Retrieves a single question by its unique identifier.
     *
     * @param uuid the unique identifier of the question
     * @return a response containing the retrieved question and its choices
     */
    public ResponseEntity<?> getQuestionById(UUID uuid) {
        // Find the question by its unique identifier
        Optional<Question> questionOptional = questionRepository.findById(uuid);
        if (questionOptional.isEmpty()) {
            // If the question is not found, return a NOT_FOUND response
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Question not found");
        }

        // Get the question object
        Question question = questionOptional.get();

        // Retrieve the list of choices associated with the given question
        List<Choice> choice = choiceRepository.findListByIdQuestion(question.getId());

        // Convert the question and its choices to a response format
        return ResponseEntity.ok(QuestionResponse.convert(question, choice));
    }

    /**
     * Deletes a question by its unique identifier.
     *
     * @param uuid the unique identifier of the question to be deleted
     * @return a response containing a success message upon successful deletion
     */

    public ResponseEntity<?> deleteQuestion(UUID uuid) {
        // Find the question by its unique identifier
        Optional<Question> questionOptional = questionRepository.findById(uuid);
        if (questionOptional.isEmpty()) {
            // If the question is not found, return a NOT_FOUND response
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Question not found");
        }
        // Get the question object
        Question question = questionOptional.get();
        // Check if the logged-in user is authorized to update the question
        Optional<Course> courseOptional = courseRepository.findById(question.getQuiz().getUnit().getLesson().getCourse().getId());
        User teacher = userService.getUser();
        if (teacher == null || courseOptional.isEmpty() || !teacher.getId().equals(courseOptional.get().getUser().getId())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized access or invalid course");
        }

        // Retrieve the list of choices associated with the given question
        List<Choice> choice = choiceRepository.findListByIdQuestion(question.getId());

        // Delete the question from the database
        questionRepository.delete(question);

        // Delete the choices associated with the deleted question
        choiceRepository.deleteAll(choice);

        // Return a response containing a success message upon successful deletion
        return ResponseEntity.ok("Delete question success");
    }
}
