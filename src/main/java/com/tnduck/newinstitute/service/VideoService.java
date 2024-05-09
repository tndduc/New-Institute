package com.tnduck.newinstitute.service;

import com.tnduck.newinstitute.dto.request.video.CreateVideoLessonRequest;
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
 * @created 05/03/2024 - 8:29 AM
 */
@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class VideoService {
    private final CloudinaryService cloudinaryService;
    private final VideoRepository videoRepository;
    private final LessonRepository lessonRepository;
    private final UserService userService;
    private final CourseService courseService;
    private final CourseRepository courseRepository;
    // Constructor injection for dependencies but i have @RequiredArgsConstructor
//    public VideoService(CloudinaryService cloudinaryService, VideoRepository videoRepository) {
//        this.cloudinaryService = cloudinaryService;
//        this.videoRepository = videoRepository;
//    }

    public ResponseEntity<?> getVideo(UUID uuid) throws Exception {
        Optional<Lesson> lessonOptional = lessonRepository.findById(uuid);
        if (lessonOptional.isEmpty()) {
            log.error("Could not find");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not found for uuid " + uuid);
        }
        log.info("Got lesson: " + lessonOptional.get().getId());
        List<Video> videos = getVideos(uuid);
        log.info("Got videos: " + videos.get(0).getId());
        return ResponseEntity.ok(LessonResponse.convert(lessonOptional.get(), videos));
    }

    public ResponseEntity<?> uploadVideo(CreateVideoLessonRequest videoLessonRequest) throws Exception {
        Optional<Lesson> lessonOptional = lessonRepository.findById(UUID.fromString(videoLessonRequest.getIdLesson()));
        if (lessonOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Lesson not found");
        }
        Lesson lesson = lessonOptional.get();

        Optional<Course> courseOptional = courseRepository.findById(lesson.getCourse().getId());
        User teacher = userService.getUser();
        if (teacher == null || courseOptional.isEmpty() || !teacher.getId().equals(courseOptional.get().getUser().getId())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized access or invalid course");
        }

        // Fetch all videos for the lesson
        List<Video> lessonVideos = videoRepository.findByLessonId(lesson.getId());

        // Calculate the ordinal number for the new video
        int ordinalNumber = lessonVideos.isEmpty() ? 1 : lessonVideos.get(lessonVideos.size() - 1).getOrdinalNumber() + 1;

        MultipartFile file = videoLessonRequest.getFile();
        if (file == null || file.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("File cannot be null or empty.");
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
        String publicId = (String) cloudinaryUpload.get("public_id");
        log.info("Video information : " + url);

        // Create and persist a new Video entity
        Video video = Video.builder()
                .lesson(lesson)
                .url(url)
                .publicId(publicId)
                .title(videoLessonRequest.getTitle())
                .ordinalNumber(ordinalNumber) // Set the ordinal number
                .build();
        Video videoSave = videoRepository.save(video);
        VideoResponse videoResponse = VideoResponse.convert(videoSave);
        return ResponseEntity.ok(videoResponse);
    }

    public List<Video> getVideos(UUID uuidLesson) {
        List<Video> videos = videoRepository.findByLessonId(uuidLesson);
        return videos;
    }

//    public List<Video> create(MultipartFile[] files, Lesson lesson) {
//        if (files == null || files.length == 0) {
//            throw new IllegalArgumentException("File cannot be null or empty.");
//        }
//
//        List<Video> videos = new ArrayList<>();
//
//        try {
//            for (MultipartFile file : files) {
//                Map<String, Object> cloudinaryUpload = cloudinaryService.upload(file);
//                // Assuming cloudinaryUpload contains necessary information for creating Video objects
//                String videoUrl = (String) cloudinaryUpload.get("url");
//                Video video = new Video();
//                video.setUrl(videoUrl);
//                video.setLesson(lesson);
//                videos.add(video);
//            }
//
//            // Save all videos to the repository
//            videos = videoRepository.saveAll(videos);
//        } catch (Exception e) {
//            // Handle exception
//        }
//
//        return videos;
//    }
    public ResponseEntity<?> deleteVideo(String videoId) {
        try {
            Optional<Video> videoOptional = videoRepository.findById(UUID.fromString(videoId));
            if (videoOptional.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Video not found");
            }
            Video video = videoOptional.get();
            cloudinaryService.delete(video.getPublicId());
            videoRepository.delete(video);
            return ResponseEntity.ok("Video deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }



}
