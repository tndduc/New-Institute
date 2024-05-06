package com.tnduck.newinstitute.service;

import com.tnduck.newinstitute.dto.request.lesson.CreateVideoLessonRequest;
import com.tnduck.newinstitute.dto.request.lesson.LessonRequest;
import com.tnduck.newinstitute.dto.response.course.CourseResponse;
import com.tnduck.newinstitute.dto.response.lesson.LessonResponse;
import com.tnduck.newinstitute.dto.response.lesson.VideoResponse;
import com.tnduck.newinstitute.entity.*;
import com.tnduck.newinstitute.repository.CourseRepository;
import com.tnduck.newinstitute.repository.LessonRepository;
import com.tnduck.newinstitute.repository.VideoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.authenticator.SingleSignOn;
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
    public ResponseEntity<?> getVideo(UUID uuid) throws Exception{
        Optional<Lesson> lessonOptional = lessonRepository.findById(uuid);
        if (lessonOptional.isEmpty()) {
            log.error("Could not find");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not found for uuid " + uuid);
        }
        log.info("Got lesson: " + lessonOptional.get().getId());
        List<Video> videos = videoService.getVideos(uuid);
        log.info("Got videos: " + videos.get(0).getId());
        return ResponseEntity.ok(LessonResponse.convert(lessonOptional.get(), videos));
    }
    public Lesson createLesson(LessonRequest lessonRequest) throws Exception {
        Optional<Course> courseOptional = courseRepository.findById(UUID.fromString(lessonRequest.getIdCourse()));
        User teacher = userService.getUser();
        if (teacher==null){
            throw new Exception("Teacher not found");
        }
        if (!teacher.getId().equals(courseOptional.get().getUser().getId())) {
            throw new Exception("You are not the course owner");
        }
        log.info("roleTeacher : " + teacher.getRoles().toString());
        if (courseOptional.isEmpty()) {
            throw new Exception("Course not found");
        }
        Course course = courseOptional.get();
        Lesson lesson = Lesson.builder()
                .title(lessonRequest.getTitle())
                .content(lessonRequest.getContent())
                .course(course)
                .build();
        Lesson lessonSave = lessonRepository.save(lesson);
        return lesson;
    }
    public VideoResponse uploadVideo(CreateVideoLessonRequest videoLessonRequest)throws Exception{
        Optional<Lesson> lessonOptional = lessonRepository.findById(UUID.fromString(videoLessonRequest.getIdLesson()));
        if (lessonOptional.isEmpty()) {
            throw new Exception("Course not found");
        }
        MultipartFile file  = videoLessonRequest.getFile();
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("File cannot be null or empty.");
        }

        Map<String, Object> cloudinaryUpload; // Use generic type for flexibility
        try {
            cloudinaryUpload = cloudinaryService.upload(file);
        } catch (Exception e) {
            log.error("Error uploading file to Cloudinary:", e);
            throw new RuntimeException("Error uploading file to Cloudinary.", e); // Rethrow with cause
        }

        // Extract relevant information from Cloudinary upload result
        String url = (String) cloudinaryUpload.get("url");
        log.info("Video information : " + url);

        // Create and persist a new File entity
        Video video = Video.builder()
                .lesson(lessonOptional.get())
                .url(url)
                .title(videoLessonRequest.getTitle())
                .build();
        Video videoSave = videoRepository.save(video);
        return  VideoResponse.convert(videoSave);
    }
}
