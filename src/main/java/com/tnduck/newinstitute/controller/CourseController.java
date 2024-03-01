package com.tnduck.newinstitute.controller;

import com.tnduck.newinstitute.dto.request.course.CreateCourseRequest;
import com.tnduck.newinstitute.dto.request.course.FilterCourseRequest;
import com.tnduck.newinstitute.dto.request.course.UpdateCourseRequest;
import com.tnduck.newinstitute.dto.response.DetailedErrorResponse;
import com.tnduck.newinstitute.dto.response.SuccessResponse;
import com.tnduck.newinstitute.dto.response.course.CourseResponse;
import com.tnduck.newinstitute.dto.response.course.CoursesPaginationResponse;
import com.tnduck.newinstitute.dto.response.user.UserResponse;
import com.tnduck.newinstitute.dto.response.user.UsersPaginationResponse;
import com.tnduck.newinstitute.entity.Course;
import com.tnduck.newinstitute.entity.specification.criteria.CourseCriteria;
import com.tnduck.newinstitute.entity.specification.criteria.PaginationCriteria;
import com.tnduck.newinstitute.repository.CourseRepository;
import com.tnduck.newinstitute.service.CourseService;
import com.tnduck.newinstitute.service.MessageSourceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static com.tnduck.newinstitute.util.Constants.SECURITY_SCHEME_NAME;

/**
 * @author ductn
 * @project The new institute
 * @created 23/02/2024 - 10:30 PM
 */
@RestController
@RequestMapping("/course")
@RequiredArgsConstructor
@Tag(name = "003. Course", description = "Course API")
public class CourseController extends AbstractBaseController{
    private final CourseService courseService;
    private final MessageSourceService messageSourceService;
    private final CourseRepository courseRepository;
    private static final String[] SORT_COLUMNS = new String[]{"id",  "name", "price", "status",
            "createdAt", "updatedAt"};

    @RequestMapping(
            path = "/create",
            method = RequestMethod.POST,
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(
            summary = "Create endpoint",
            security = @SecurityRequirement(name = SECURITY_SCHEME_NAME),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successful operation",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = SuccessResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "422",
                            description = "Validation failed",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = DetailedErrorResponse.class)
                            )
                    )
            }
    )
    public ResponseEntity<?> createCourse(@Parameter(description = "Request body to login", required = true)
                                         @ModelAttribute @Validated final CreateCourseRequest request) throws BindException {
        CourseResponse course = courseService.create(request);

        return ResponseEntity.ok(course);
    }
    @RequestMapping(
            path = "/update",
            method = RequestMethod.POST,
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(
            summary = "Create endpoint",
            security = @SecurityRequirement(name = SECURITY_SCHEME_NAME),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successful operation",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = SuccessResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "422",
                            description = "Validation failed",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = DetailedErrorResponse.class)
                            )
                    )
            }
    )
    public ResponseEntity<?> updateCourse(@Parameter(description = "Request body to login", required = true)
                                          @ModelAttribute @Validated final UpdateCourseRequest request) throws BindException {
        CourseResponse courseResponse = courseService.update(request);

        return ResponseEntity.ok(courseResponse);
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
            @Parameter(name = "status", description = "status", example = "unchecked")
            @RequestParam(required = false) final String status,

            @Parameter(name = "page", description = "Page number", example = "1")
            @RequestParam(defaultValue = "1", required = false) final Integer page,

            @Parameter(name = "size", description = "Page size", example = "20")
            @RequestParam(defaultValue = "${spring.data.web.pageable.default-page-size}",
                    required = false) final Integer size,

            @Parameter(name = "sortBy", description = "Sort by column", example = "createdAt",
                    schema = @Schema(type = "String", allowableValues = {"id", "name", "price", "status", "createdAt", "updatedAt"}))
            @RequestParam(defaultValue = "createdAt", required = false) final String sortBy,

            @Parameter(name = "sort", description = "Sort direction", schema = @Schema(type = "string",
                    allowableValues = {"asc", "desc"}, defaultValue = "asc"))
            @RequestParam(defaultValue = "asc", required = false) @Pattern(regexp = "asc|desc") final String sort
    ){
        sortColumnCheck(messageSourceService, SORT_COLUMNS, sortBy);
        Page<Course> courses = courseService.findAll(
                CourseCriteria.builder()
                        .keyword(keyword)
                        .priceMin(priceMin)
                        .priceMax(priceMax)
                        .createdAtStart(createdAtStart)
                        .createdAtEnd(createdAtEnd)
                        .status(status)
                        .build(),
                PaginationCriteria.builder()
                        .page(page)
                        .size(size)
                        .sortBy(sortBy)
                        .sort(sort)
                        .columns(SORT_COLUMNS)
                        .build()
        );
        System.out.println(courses.stream().count());
        return ResponseEntity.ok(new CoursesPaginationResponse(courses, courses.stream()
                .map(CourseResponse::convert)
                .toList()));
    }
    @GetMapping("/get")
    public List<Course> getall(){
        return courseRepository.findAll();
    }
}
