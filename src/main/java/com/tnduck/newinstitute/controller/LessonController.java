package com.tnduck.newinstitute.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ductn
 * @project The new institute
 * @created 03/03/2024 - 8:02 PM
 */
@RestController
@RequestMapping("/course")
@RequiredArgsConstructor
@Tag(name = "003. Course", description = "Course API")
public class LessonController {
}
