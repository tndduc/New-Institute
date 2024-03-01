package com.tnduck.newinstitute.service;

import com.tnduck.newinstitute.entity.File;
import com.tnduck.newinstitute.repository.FileRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * @author ductn
 * @project The new institute
 * @created 28/02/2024 - 3:53 PM
 */
@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class FileService {
    private final CloudinaryService cloudinaryService;
    private final FileRepository fileRepository;

    public File createFile(MultipartFile file) {
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
        String format = (String) cloudinaryUpload.get("format");

        // Create and persist a new File entity
        File newFile = new File();
        newFile.setName(file.getOriginalFilename());
        newFile.setUrl(url);
        newFile.setType(format);
        newFile.setStatus("uploaded"); // Set status as appropriate

        File savedFile = fileRepository.save(newFile);
        return savedFile;
    }
}
