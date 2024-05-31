package com.tnduck.newinstitute.service;

import com.tnduck.newinstitute.dto.request.lesson.LessonUpdateRequest;
import com.tnduck.newinstitute.dto.request.video.CreateVideoLessonRequest;
import com.tnduck.newinstitute.dto.request.lesson.LessonRequest;
import com.tnduck.newinstitute.dto.response.lesson.LessonResponse;
import com.tnduck.newinstitute.dto.response.unit.UnitResponse;
import com.tnduck.newinstitute.dto.response.video.VideoResponse;
import com.tnduck.newinstitute.entity.*;
import com.tnduck.newinstitute.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

/**
 * @author ductn
 * @project The new institute
 * @created 05/03/2024 - 8:10 AM
 */
@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class LessonService {
    private final LessonRepository lessonRepository;
    private final CourseRepository courseRepository;
    private final CloudinaryService cloudinaryService;
    private final VideoRepository videoRepository;
    private final UserService userService;
    private final VideoService videoService;
    private final QuizRepository quizRepository;
    private final UnitService unitService;
    private final UnitRepository unitRepository;

    /**
     * Retrieves a list of lessons for a given course.
     *
     * @param uuid the unique identifier of the course
     * @return a {@link ResponseEntity} containing a list of {@link LessonResponse} objects representing the lessons in the specified course
     * @throws Exception if an error occurs during the retrieval process
     */
    public ResponseEntity<?> getLessonByIdCourse(UUID uuid) throws Exception {
        Optional<Course> courseOptional = courseRepository.findById(uuid);
        if (courseOptional.isEmpty()) {
            log.error("Could not find course with uuid " + uuid);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not found for uuid " + uuid);
        }
        List<Lesson> lessons = lessonRepository.findByCourse_Id(uuid);
        List<LessonResponse> lessonResponses = new ArrayList<>();
        for (Lesson lesson : lessons) {
            // get the list of unit by id lesson
            List<UnitResponse> unitResponses = unitService.getUnitAndAssociatedContent(lesson.getId());
            // add the units to the list
            lessonResponses.add(LessonResponse.convert(lesson, unitResponses));
        }
        return ResponseEntity.ok(lessonResponses);
    }

    /**
     * Creates a new lesson for a given course.
     *
     * @param lessonRequest the request containing the details of the new lesson
     * @return a {@link ResponseEntity} containing the created lesson's details if successful, or an error message if the operation fails
     * @throws Exception if an error occurs during the creation process
     */
    public ResponseEntity<?> createLesson(LessonRequest lessonRequest) {
        Optional<Course> courseOptional = courseRepository.findById(UUID.fromString(lessonRequest.getIdCourse()));
        if (courseOptional.isEmpty()) {
            log.error("Could not find course with uuid " + UUID.fromString(lessonRequest.getIdCourse()));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not found for uuid " + UUID.fromString(lessonRequest.getIdCourse()));
        }
        User teacher = userService.getUser();
        if (teacher == null || !teacher.getId().equals(courseOptional.get().getUser().getId())) {
            log.error("Unauthenticated");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized access");
        }

        Course course = courseOptional.get();
        List<Lesson> lessons = lessonRepository.findByCourse_Id(course.getId());
        if (lessons.stream().anyMatch(existingLesson -> existingLesson.getTitle().equals(lessonRequest.getTitle()))) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Lesson with title '" + lessonRequest.getTitle() + "' already exists in this course");
        }

        int ordinalNumber = lessons.isEmpty() ? 1 : lessons.get(lessons.size() - 1).getOrdinalNumber() + 1;

        Lesson lesson = Lesson.builder()
                .title(lessonRequest.getTitle())
                .content(lessonRequest.getContent())
                .course(course)
                .ordinalNumber(ordinalNumber).
                build();
        Lesson lessonSave = lessonRepository.save(lesson);

        return ResponseEntity.status(HttpStatus.CREATED).body(LessonResponse.convert(lessonSave));

    }

    /**
     * Updates the details of a lesson.
     *
     * @param lessonId the unique identifier of the lesson to be updated
     * @param lessonUpdateRequest    the new title and content for the lesson (optional)
     * @return a {@link ResponseEntity} containing the updated lesson's details if successful, or an error message if the operation fails
     * @throws Exception if an error occurs during the update process
     */
    public ResponseEntity<?> updateLesson(String lessonId, LessonUpdateRequest lessonUpdateRequest) {
        try {
            Optional<Lesson> lessonOptional = lessonRepository.findById(UUID.fromString(lessonId));
            if (lessonOptional.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Lesson not found");
            }
            User teacher = userService.getUser();
            Lesson existingLesson = lessonOptional.get();
            if (!teacher.getId().equals(existingLesson.getCourse().getUser().getId())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized access");
            }
            List<Lesson> lessonsInCourse = lessonRepository.findByCourse_Id(existingLesson.getCourse().getId());
            Optional<Lesson> lessonWithSameTitle = lessonsInCourse.stream()
                    .filter(lesson -> !lesson.getId().equals(existingLesson.getId()))
                    .filter(lesson -> lesson.getTitle().equals(lessonUpdateRequest.getTitle())).findAny();
            if (lessonWithSameTitle.isPresent()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Lesson with title '" + lessonUpdateRequest.getTitle() + "' already exists in this course");
            }

            if (lessonUpdateRequest.getTitle() != null) {
                existingLesson.setTitle(lessonUpdateRequest.getTitle());
            }
            if (lessonUpdateRequest.getContent() != null) {
                existingLesson.setContent(lessonUpdateRequest.getContent());
            }
            Lesson updatedLesson = lessonRepository.save(existingLesson);

            return ResponseEntity.ok(LessonResponse.convert(updatedLesson));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    /**
     * Deletes a lesson by its unique identifier.
     *
     * @param lessonId the unique identifier of the lesson to be deleted
     * @return a {@link ResponseEntity} containing a success message if the lesson is successfully deleted, or an error message if the operation fails
     * @throws Exception if an error occurs during the deletion process
     */
    public ResponseEntity<?> deleteLesson(String lessonId) throws Exception {
        try {
            Optional<Lesson> lessonOptional = lessonRepository.findById(UUID.fromString(lessonId));
            if (lessonOptional.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Lesson not found");
            }
            User teacher = userService.getUser();
            Lesson existingLesson = lessonOptional.get();
            if (!teacher.getId().equals(existingLesson.getCourse().getUser().getId())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized access");
            }
            List<Unit> units = unitRepository.findByLessonId(existingLesson.getId());
            if (units.isEmpty()) {
                lessonRepository.delete(existingLesson);
                return ResponseEntity.ok("Lesson deleted successfully");
            }
            for (Unit unit : units) {
                Optional<Video> video = videoRepository.findByUnitId(unit.getId());
                if (video.isPresent()) {
                    videoService.deleteVideo(video.get().getId().toString());
                }
            }
            for (Unit unit : units) {
                Optional<Quiz> quizOptional = quizRepository.findByUnitId(unit.getId());
                if (quizOptional.isPresent()) {
                    quizRepository.delete(quizOptional.get());
                }
            }
            for (Unit unit : units) {
                unitRepository.delete(unit);
            }
            lessonRepository.delete(existingLesson);
            return ResponseEntity.ok("Lesson deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }


}
