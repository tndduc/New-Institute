package com.tnduck.newinstitute.service;

import com.tnduck.newinstitute.dto.request.video.CreateVideoLessonRequest;
import com.tnduck.newinstitute.dto.response.lesson.LessonResponse;
import com.tnduck.newinstitute.dto.response.unit.UnitResponse;
import com.tnduck.newinstitute.dto.response.video.VideoResponse;
import com.tnduck.newinstitute.entity.*;
import com.tnduck.newinstitute.exception.NotFoundException;
import com.tnduck.newinstitute.repository.CourseRepository;
import com.tnduck.newinstitute.repository.LessonRepository;
import com.tnduck.newinstitute.repository.UnitRepository;
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
    private final UnitRepository unitRepository;
    // Constructor injection for dependencies but i have @RequiredArgsConstructor
//    public VideoService(CloudinaryService cloudinaryService, VideoRepository videoRepository) {
//        this.cloudinaryService = cloudinaryService;
//        this.videoRepository = videoRepository;
//    }

    public ResponseEntity<?> getVideoFromUnit(UUID uuid){
        try {
            UnitResponse unitResponse = getVideoByIdUnit(uuid);
            if (unitResponse == null) {
                log.error("Could not find Unit with uuid " + uuid);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not found unit for uuid " + uuid);
            }
            return ResponseEntity.ok(unitResponse);
        } catch (NotFoundException e) {
            log.error("Error occurred while getting video by id: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
        }
    }
    public UnitResponse getVideoByIdUnit(UUID uuid) {
        try {
            Optional<Unit> unitOptional = unitRepository.findById(uuid);
            if (unitOptional.isEmpty()) {
                log.error("Could not find Unit with uuid " + uuid);
                throw new NotFoundException("Not found unit for uuid " + uuid);
            }
            log.info("Got lesson: " + unitOptional.get().getId());
            Video video = getVideo(uuid);
            log.info("Got videos: " + video.getId());
            return UnitResponse.convert(unitOptional.get(), video);
        } catch (NotFoundException e) {
            log.error("Error occurred while getting video by id: " + e.getMessage());
            return null;
        }
    }

    public ResponseEntity<?> createVideo(CreateVideoLessonRequest videoLessonRequest){
        Optional<Lesson> lessonOptional = lessonRepository.findById(UUID.fromString(videoLessonRequest.getIdLesson()));
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
        Boolean isPreviewAvailable = false;
        if (videoLessonRequest.getIsPreview().equals("true")){
            isPreviewAvailable = true;
        }
        // Create a new unit with the provided details
        Unit unit = new Unit();
        unit.setTitle(videoLessonRequest.getTitle());
        unit.setType("video");
        unit.setIsPreview(isPreviewAvailable);
        unit.setDescription(videoLessonRequest.getDescription());
        unit.setOrdinalNumber(ordinal);
        unit.setLesson(lessonOptional.get());
        Unit unitSave = unitRepository.save(unit);
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
                .unit(unitSave)
                .url(url)
                .publicId(publicId)
                .build();
        Video videoSave = videoRepository.save(video);
//        VideoResponse videoResponse = VideoResponse.convert(videoSave);
        UnitResponse unitResponse = UnitResponse.convert(unitSave,videoSave);
        return ResponseEntity.ok(unitResponse);
    }

    public Video getVideo(UUID uuidUnit) {
        Optional<Video> video = videoRepository.findByUnitId(uuidUnit);
        return video.get();
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
            Optional<Course> courseOptional = courseRepository.findById(videoOptional.get().getUnit().getLesson().getCourse().getId());
            User teacher = userService.getUser();
            if (teacher == null || courseOptional.isEmpty() || !teacher.getId().equals(courseOptional.get().getUser().getId())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized access or invalid course");
            }
            Video video = videoOptional.get();
            cloudinaryService.delete(video.getPublicId());
            videoRepository.delete(video);
            return ResponseEntity.ok("Video deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
    public ResponseEntity<?> updateVideo(String videoId, String title) {
        try {
            Optional<Video> videoOptional = videoRepository.findById(UUID.fromString(videoId));
            if (videoOptional.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Video not found");
            }
            Optional<Course> courseOptional = courseRepository.findById(videoOptional.get().getUnit().getLesson().getCourse().getId());
            User teacher = userService.getUser();
            if (teacher == null || courseOptional.isEmpty() || !teacher.getId().equals(courseOptional.get().getUser().getId())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized access or invalid course");
            }
            Video video = videoOptional.get();
//            if (title.equals(video.getTitle())) {
//                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Title is not same as old title");
//            }
            if (title.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Title cannot be empty");
            }
//            video.setTitle(title);
            videoRepository.save(video);
            return ResponseEntity.ok(video);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }


}
