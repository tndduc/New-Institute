package com.tnduck.newinstitute.controller;

import com.tnduck.newinstitute.dto.request.course.CreateCourseRequest;
import com.tnduck.newinstitute.entity.File;
import com.tnduck.newinstitute.service.CloudinaryService;
import com.tnduck.newinstitute.service.FileService;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
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
    private final FileService fileService;
    @RequestMapping(
            path = "/upload",
            method = RequestMethod.POST,
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadImage(@Parameter(description = "Request body to login", required = true)
                                         @ModelAttribute  @Validated final CreateCourseRequest request){
        MultipartFile file = request.getFile();
        System.out.println("File name : "+file.getName());
        System.out.println("Type : " + file.getContentType());
        System.out.println("Name : " + file.getOriginalFilename());
        System.out.println("Size : " + file.getSize());
        System.out.println(request.getName());
        File data = this.fileService.createFile(file);

        return new ResponseEntity<>(data, HttpStatus.OK);
    }
//    @RequestMapping(
//            path = "/cloud",
//            method = RequestMethod.POST,
//            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public ResponseEntity<Map> uploadImage(@RequestParam("image")MultipartFile file){
//        File data = this.fileService.createFile(file);
//        return new ResponseEntity<>(data, HttpStatus.OK);
//    }

}
