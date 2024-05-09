package com.tnduck.newinstitute.service;

import com.tnduck.newinstitute.dto.request.video.CreateVideoLessonRequest;
import com.tnduck.newinstitute.dto.request.lesson.LessonRequest;
import com.tnduck.newinstitute.dto.response.lesson.LessonResponse;
import com.tnduck.newinstitute.dto.response.video.VideoResponse;
import com.tnduck.newinstitute.entity.*;
import com.tnduck.newinstitute.repository.CourseRepository;
import com.tnduck.newinstitute.repository.LessonRepository;
import com.tnduck.newinstitute.repository.VideoRepository;
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
    public ResponseEntity<?> getLesson(UUID uuid) throws Exception{
        Optional<Course> courseOptional = courseRepository.findById(uuid);
        if (courseOptional.isEmpty()) {
            log.error("Could not find");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not found for uuid " + uuid);
        }
        List<Video> videos = new ArrayList<>();
        List<Lesson> lessons = lessonRepository.findByCourse_Id(uuid);
        List<LessonResponse> lessonResponses = new ArrayList<>();
        for (Lesson lesson : lessons) {
            lessonResponses.add(LessonResponse.convert(lesson,videos));
        }
        log.info("Got videos: " + lessons.get(0).getId());
        return ResponseEntity.ok(lessonResponses);
    }

    public ResponseEntity<?> createLesson(LessonRequest lessonRequest) {
        try {
            Optional<Course> courseOptional = courseRepository.findById(UUID.fromString(lessonRequest.getIdCourse()));
            User teacher = userService.getUser();
            if (teacher == null || courseOptional.isEmpty() || !teacher.getId().equals(courseOptional.get().getUser().getId())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized access or invalid course");
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

            return ResponseEntity.status(HttpStatus.CREATED).body(LessonResponse.convert(lessonSave, Collections.emptyList()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    public ResponseEntity<?> updateLesson(String lessonId, String title, String content) {
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
                            .filter(lesson -> lesson.getTitle().equals(title)).findAny();
            if (lessonWithSameTitle.isPresent()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Lesson with title '" + title + "' already exists in this course");
            }

            if (title != null) {
                existingLesson.setTitle(title);
            }
            if (content != null) {
                existingLesson.setContent(content);
            }
            Lesson updatedLesson = lessonRepository.save(existingLesson);

            return ResponseEntity.ok(LessonResponse.convert(updatedLesson, Collections.emptyList()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
    public ResponseEntity<?> deleteLesson(String lessonId) {
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

            List<Video> videos = videoRepository.findByLessonId(existingLesson.getId());
            for (Video video : videos) {
                videoService.deleteVideo(video.getId().toString());
            }

            lessonRepository.delete(existingLesson);

            return ResponseEntity.ok("Lesson deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }


}
