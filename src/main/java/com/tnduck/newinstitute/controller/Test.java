package com.tnduck.newinstitute.controller;

import com.tnduck.newinstitute.dto.request.course.CreateCourseRequest;
import com.tnduck.newinstitute.dto.request.quiz.CreateQuizRequest;
import com.tnduck.newinstitute.entity.File;
import com.tnduck.newinstitute.service.CloudinaryService;
import com.tnduck.newinstitute.service.FileService;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class Test {


}
