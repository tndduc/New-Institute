package com.tnduck.newinstitute.service;

import com.tnduck.newinstitute.entity.File;
import com.tnduck.newinstitute.entity.Lesson;
import com.tnduck.newinstitute.entity.Video;
import com.tnduck.newinstitute.repository.VideoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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

    // Constructor injection for dependencies but i have @RequiredArgsConstructor
//    public VideoService(CloudinaryService cloudinaryService, VideoRepository videoRepository) {
//        this.cloudinaryService = cloudinaryService;
//        this.videoRepository = videoRepository;
//    }

    public  List<Video> getVideos(UUID uuidLesson) {
        List<Video> videos = videoRepository.findByLessonId(uuidLesson);
        return videos;
    }
    public List<Video> create(MultipartFile[] files, Lesson lesson) {
        if (files == null || files.length == 0) {
            throw new IllegalArgumentException("File cannot be null or empty.");
        }

        List<Video> videos = new ArrayList<>();

        try {
            for (MultipartFile file : files) {
                Map<String, Object> cloudinaryUpload = cloudinaryService.upload(file);
                // Assuming cloudinaryUpload contains necessary information for creating Video objects
                String videoUrl = (String) cloudinaryUpload.get("url");
                Video video = new Video();
                video.setUrl(videoUrl);
                video.setLesson(lesson);
                videos.add(video);
            }

            // Save all videos to the repository
            videos = videoRepository.saveAll(videos);
        } catch (Exception e) {
            // Handle exception
        }

        return videos;
    }
}
