package com.tnduck.newinstitute.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

/**
 * @author ductn
 * @project The new institute
 * @created 21/02/2024 - 10:58 PM
 */
@Service
@RequiredArgsConstructor
public class CloudinaryService {

    private static final Logger log = LoggerFactory.getLogger(CloudinaryService.class);
    private final Cloudinary cloudinary;

    public Map upload(MultipartFile file) {
        try {
            if (isImage(file)) {
                log.info("Uploading image");
                return this.cloudinary.uploader().upload(file.getBytes(), Map.of());
            } else {
                log.info("Uploading video : " + file.getSize() + " bytes");
                return this.cloudinary.uploader().uploadLarge(file.getBytes(),
                        ObjectUtils.asMap("resource_type", "video"));
            }
        } catch (IOException io) {
            log.error("Error uploading file to Cloudinary:", io);
            throw new RuntimeException("Upload fail");
        }
    }
    public void delete(String publicId) throws IOException {
        // Thực hiện xóa tệp từ Cloudinary bằng publicId
        try {
            Map result = cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
            log.info("Deleted from Cloudinary: {}", result.toString());
        } catch (IOException e) {
            log.error("Error deleting from Cloudinary: ", e);
            throw e;
        }
    }
    private boolean isImage(MultipartFile file) {
        log.info("Checking");
        // Kiểm tra phần mở rộng của file để xác định loại file
        String fileName = file.getOriginalFilename();
        if (fileName != null) {
            String extension = fileName.substring(fileName.lastIndexOf(".") + 1);
            // Thêm các phần mở rộng của các định dạng ảnh mà bạn hỗ trợ
            return extension.equalsIgnoreCase("jpg") ||
                    extension.equalsIgnoreCase("jpeg") ||
                    extension.equalsIgnoreCase("png") ||
                    extension.equalsIgnoreCase("gif");
        }
        return false;
    }
}