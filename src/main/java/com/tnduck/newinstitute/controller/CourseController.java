package com.tnduck.newinstitute.controller;

import com.tnduck.newinstitute.dto.request.course.CreateCourseRequest;
import com.tnduck.newinstitute.dto.request.course.UpdateCourseRequest;
import com.tnduck.newinstitute.dto.request.course.tag.TagRequest;
import com.tnduck.newinstitute.dto.request.lesson.LessonRequest;
import com.tnduck.newinstitute.dto.response.DetailedErrorResponse;
import com.tnduck.newinstitute.dto.response.SuccessResponse;
import com.tnduck.newinstitute.dto.response.course.CourseResponse;
import com.tnduck.newinstitute.dto.response.course.CoursesPaginationResponse;
import com.tnduck.newinstitute.dto.response.course.category.CategoryResponse;
import com.tnduck.newinstitute.dto.response.course.tag.TagResponse;
import com.tnduck.newinstitute.dto.response.user.UserResponse;
import com.tnduck.newinstitute.dto.response.user.UsersPaginationResponse;
import com.tnduck.newinstitute.dto.validator.Level;
import com.tnduck.newinstitute.entity.*;
import com.tnduck.newinstitute.entity.specification.criteria.CourseCriteria;
import com.tnduck.newinstitute.entity.specification.criteria.PaginationCriteria;
import com.tnduck.newinstitute.exception.BadRequestException;
import com.tnduck.newinstitute.exception.NotFoundException;
import com.tnduck.newinstitute.repository.CategoryCourseRepository;
import com.tnduck.newinstitute.repository.CourseRepository;
import com.tnduck.newinstitute.repository.TagCourseRepository;
import com.tnduck.newinstitute.service.CourseService;
import com.tnduck.newinstitute.service.EnrollmentService;
import com.tnduck.newinstitute.service.MessageSourceService;
import com.tnduck.newinstitute.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import jdk.jfr.Category;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BindException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

import static com.tnduck.newinstitute.util.Constants.SECURITY_SCHEME_NAME;

/**
 * @author ductn
 * @project The new institute
 * @created 23/02/2024 - 10:30 PM
 */
@RestController
@RequestMapping("/course")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "003. Course", description = "Course API")
public class CourseController extends AbstractBaseController {
    private final CourseService courseService;
    private final MessageSourceService messageSourceService;
    private final CourseRepository courseRepository;
    private final CategoryCourseRepository categoryCourseRepository;
    private final TagCourseRepository tagCourseRepository;
    private final UserService userService;
    private final EnrollmentService enrollmentService;
    private static final String[] SORT_COLUMNS = new String[]{"id", "name", "price", "status",
            "createdAt", "updatedAt"};

    @PreAuthorize("hasAuthority('TEACHER')")
    @RequestMapping(
            path = "/create",
            method = RequestMethod.POST,
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(
            summary = "Create a new course",
            security = @SecurityRequirement(name = SECURITY_SCHEME_NAME)
    )
    public ResponseEntity<CourseResponse> createCourse(@ModelAttribute final CreateCourseRequest request) {
        try {
            CourseResponse course = courseService.create(request);
            return ResponseEntity.ok(course);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @PreAuthorize("hasAuthority('TEACHER')")
    @RequestMapping(
            path = "/update",
            method = RequestMethod.PUT,
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(
            summary = "Update a new course",
            security = @SecurityRequirement(name = SECURITY_SCHEME_NAME)
    )
    public ResponseEntity<?> updateCourse(@ModelAttribute final UpdateCourseRequest request
    ) {
        try {
            return  courseService.updateByTeacher(request);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @GetMapping("/get-all")
    public ResponseEntity<CoursesPaginationResponse> list(
            @Parameter(name = "createdAtStart", description = "Created date start", example = "2022-10-25T22:54:58")
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) final LocalDateTime createdAtStart,

            @Parameter(name = "createdAtEnd", description = "Created date end", example = "2022-10-25T22:54:58")
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) final LocalDateTime createdAtEnd,

            @Parameter(name = "priceMin", description = "Min Price", example = "0")
            @RequestParam(required = false) final BigDecimal priceMin,

            @Parameter(name = "priceMax", description = "Max Price", example = "100")
            @RequestParam(required = false) final BigDecimal priceMax,

            @Parameter(name = "keyword", description = "Search keyword", example = "lorem")
            @RequestParam(required = false) final String keyword,

//            @Parameter(name = "status", description = "status", example = "uploaded")
//            @RequestParam(required = false) final String status,

            @Parameter(name = "page", description = "Page number", example = "1")
            @RequestParam(defaultValue = "1", required = false) final Integer page,

            @Parameter(name = "size", description = "Page size", example = "20")
            @RequestParam(defaultValue = "${spring.data.web.pageable.default-page-size}", required = false) final Integer size,

            @Parameter(name = "sortBy", description = "Sort by column", example = "createdAt",
                    schema = @Schema(type = "String", allowableValues = {"id", "name", "price", "status", "createdAt", "updatedAt"}))
            @RequestParam(defaultValue = "createdAt", required = false) final String sortBy,

            @Parameter(name = "sort", description = "Sort direction", schema = @Schema(type = "string",
                    allowableValues = {"asc", "desc"}, defaultValue = "asc"))
            @RequestParam(defaultValue = "asc", required = false) @Pattern(regexp = "asc|desc") final String sort
    ) {
        try {
            sortColumnCheck(messageSourceService, SORT_COLUMNS, sortBy);
            Page<Course> courses = courseService.findAll(
                    CourseCriteria.builder()
                            .keyword(keyword)
                            .priceMin(priceMin)
                            .priceMax(priceMax)
                            .createdAtStart(createdAtStart)
                            .createdAtEnd(createdAtEnd)
//                            .status(status)
                            .build(),
                    PaginationCriteria.builder()
                            .page(page)
                            .size(size)
                            .sortBy(sortBy)
                            .sort(sort)
                            .columns(SORT_COLUMNS)
                            .build()
            );
            List<CourseResponse> courseResponses = courses.stream()
                    .map(CourseResponse::convert)
                    .toList();
//            List<Course> userCourses = new ArrayList<>();
//            Optional<User> user = userService.getUserOptional();
//            if (!user.isEmpty()) {
//                List<Enrollment> enrollments = enrollmentService.getEnrollmentByUserId(user.get().getId());
//                for (Enrollment enrollment : enrollments) {
//                    userCourses.add(enrollment.getCourse());
//                }
//            }
//            List<Course> filteredCourses = new ArrayList<>();
//            for (Course course : courses.getContent()) {
//                if ("public".equals(course.getStatusTeacher())) {
//                    filteredCourses.add(course);
//                }
//            }
//            for (Course course : filteredCourses) {
//                for (Course courseUser : userCourses)
//                    if (courseUser.getId().equals(course.getId())) {
//                        filteredCourses.remove(course);
//                    }
//            }
//
//            List<CourseResponse> courseResponses = filteredCourses.stream()
//                    .map(CourseResponse::convert)
//                    .toList();

            return ResponseEntity.ok(new CoursesPaginationResponse(courses, courseResponses));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    @GetMapping("/get-by-id")
    public ResponseEntity<CourseResponse> getById(
            @Parameter(name = "id", description = "Course ID", example = "00000000-0000-0000-0000-000000000001")
            @RequestParam(required = true) final UUID id) {

        try {
            Course course = courseRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Course not found with id: " + id));

            CourseResponse courseResponse = CourseResponse.convert(course);
            return ResponseEntity.ok().body(courseResponse);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build(); // Course not found
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null); // Other errors
        }
    }
    @GetMapping("/get-by-teacher")
    @PreAuthorize("hasAuthority('TEACHER')")
    @Operation(
            summary = "Create a new Lesson",
            security = @SecurityRequirement(name = SECURITY_SCHEME_NAME)
    )
    public ResponseEntity<?> getByTeacher(
    )  {
        try {
            return courseService.getCourseByAuthor();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @GetMapping("/get-by-user")
    @PreAuthorize("hasAuthority('USER')")
    @Operation(
            summary = "Create a new Lesson",
            security = @SecurityRequirement(name = SECURITY_SCHEME_NAME)
    )
    public ResponseEntity<?> getByUser(
    )  {
        try {
            return courseService.getCourseInEnrollByUser();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    public ResponseEntity<?> getCategoriesByName( @Parameter(name = "name", description = "Name Category", example = "backend")
                                                         @RequestParam(required = true) final String name) {
        try {
            return courseService.getCategoryByName(name);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/categories/get-all")
    public ResponseEntity<List<CategoryResponse>> getAllCategories() {
        try {
            List<CategoryResponse> categoryResponses = new ArrayList<>();
            List<CategoryCourse> categoriesFromRepository = categoryCourseRepository.findAll();
            for (CategoryCourse categoryCourse : categoriesFromRepository) {
                categoryResponses.add(CategoryResponse.convert(categoryCourse));
            }

            return ResponseEntity.ok().body(categoryResponses);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    @GetMapping("/tags/get-all")
    public ResponseEntity<List<TagResponse>> getAllTags() {
        try {
            List<TagResponse> tagResponses = new ArrayList<>();
            List<TagCourse> tagCourses = tagCourseRepository.findAll();
            for (TagCourse tagCourse : tagCourses) {
                tagResponses.add(TagResponse.convert(tagCourse));
            }

            return ResponseEntity.ok().body(tagResponses);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    @GetMapping("/tags/get-by-name")
    public ResponseEntity<?> getTagByName(@Parameter(name = "name", description = "Name tag", example = "backend")
                                              @RequestParam(required = true) final String name) {
        try {
           return courseService.getTagByName(name);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    @GetMapping("/categories/get-by-name")
    public ResponseEntity<?> getCategoryByName(@Parameter(name = "name", description = "Name Category", example = "backend")
                                          @RequestParam(required = true) final String name) {
        try {
            return courseService.getCategoryByName(name);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PreAuthorize("hasAuthority('TEACHER')")
    @DeleteMapping("/delete-by-teacher")
    public ResponseEntity<CourseResponse> deleteByTeacher(
            @Parameter(name = "id", description = "Course ID", example = "00000000-0000-0000-0000-000000000001")
            @RequestParam(required = true) final UUID id) {

        try {
            CourseResponse deletedCourse = courseService.deleteByTeacher(id);
            if (deletedCourse != null) {
                return ResponseEntity.ok(deletedCourse);
            } else {
                // Course not found
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            // Log the exception
            log.error("An error occurred while deleting the course.", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @PostMapping("/update-status-by-teacher")
    @PreAuthorize("hasAuthority('TEACHER')")
    @Operation(
            summary = "Update status course by teacher",
            security = @SecurityRequirement(name = SECURITY_SCHEME_NAME)
    )
    public ResponseEntity<?> updateStatusByTeacher(
            @Parameter(name = "id", description = "id of course")
            @RequestParam(required = true) final String id,
            @Parameter(name = "status", description = "status by teacher", example = "public",
                    schema = @Schema(type = "String", allowableValues = {"on_create", "public", "private", "delete"}))
            @RequestParam(defaultValue = "createdAt", required = false) final String status
    )  {
        try {
            return courseService.updateStatusByTeacher(id, status);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
