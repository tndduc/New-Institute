package com.tnduck.newinstitute.controller;

import com.tnduck.newinstitute.service.CloudinaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * @author ductn
 * @project The new institute
 * @created 21/02/2024 - 10:59 PM
 */
@RestController
@RequestMapping("/cloudinary")
@RequiredArgsConstructor
public class Test {
    private final CloudinaryService cloudinaryService;

    @RequestMapping(
            path = "/upload",
            method = RequestMethod.POST,
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Map> uploadImage(@RequestPart("file") MultipartFile file){
        Map data = this.cloudinaryService.upload(file);
        return new ResponseEntity<>(data, HttpStatus.OK);
    }
}
