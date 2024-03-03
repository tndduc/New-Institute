//package com.tnduck.newinstitute.controller.admin;
//
//import com.tnduck.newinstitute.dto.response.course.CourseResponse;
//import com.tnduck.newinstitute.service.CourseService;
//import com.tnduck.newinstitute.service.MessageSourceService;
//import com.tnduck.newinstitute.service.UserService;
//import io.swagger.v3.oas.annotations.Parameter;
//import io.swagger.v3.oas.annotations.tags.Tag;
//import lombok.RequiredArgsConstructor;
//import org.springframework.web.bind.annotation.*;
//
///**
// * @author ductn
// * @project The new institute
// * @created 03/03/2024 - 8:28 PM
// */
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("/admin/courses")
//@Tag(name = "102. Admin - Course", description = "Admin - Courses API")
//public class CourseController {
//    private final UserService userService;
//
//    private final MessageSourceService messageSourceService;
//    private final CourseService courseService;
//    @PutMapping("/delete-by-teacher")
//    public CourseResponse checkCourseByAdmin(@Parameter(name = "id", description = "Search keyword", example = "lorem")
//                                          @RequestParam(required = true) final String id) throws Exception {
//        return courseService.checkCourseByAdmin(id);
//    }
//}
