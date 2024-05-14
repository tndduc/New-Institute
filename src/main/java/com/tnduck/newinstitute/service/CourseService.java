package com.tnduck.newinstitute.service;

import com.tnduck.newinstitute.dto.request.course.CreateCourseRequest;
import com.tnduck.newinstitute.dto.request.course.UpdateCourseRequest;
import com.tnduck.newinstitute.dto.request.course.tag.TagRequest;
import com.tnduck.newinstitute.dto.response.course.CourseResponse;
import com.tnduck.newinstitute.entity.*;
import com.tnduck.newinstitute.entity.specification.CourseFilterSpecification;
import com.tnduck.newinstitute.entity.specification.UserFilterSpecification;
import com.tnduck.newinstitute.entity.specification.criteria.CourseCriteria;
import com.tnduck.newinstitute.entity.specification.criteria.PaginationCriteria;
import com.tnduck.newinstitute.entity.specification.criteria.UserCriteria;
import com.tnduck.newinstitute.repository.CategoryCourseRepository;
import com.tnduck.newinstitute.repository.CourseRepository;
import com.tnduck.newinstitute.repository.FileRepository;
import com.tnduck.newinstitute.repository.TagCourseRepository;
import com.tnduck.newinstitute.util.PageRequestBuilder;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.function.EntityResponse;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author ductn
 * @project The new institute
 * @created 23/02/2024 - 10:31 PM
 */
@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CourseService {
    private final UserService userService;
    private final CourseRepository courseRepository;
    private final FileService fileService;
    private final TagCourseRepository tagCourseRepository;
    private final CategoryCourseRepository categoryCourseRepository;

    public CourseResponse create(CreateCourseRequest createCourseRequest) {
        User teacher = userService.getUser();
        MultipartFile fileRequest = createCourseRequest.getFile();
        File fileUpload = fileService.createFile(fileRequest);
        List<TagCourse> tags = createOrUpdateTags(createCourseRequest.getTags());
        List<CategoryCourse> categories = createOrUpdateCategories(createCourseRequest.getCategories());

        Course course = buildCourse(teacher, createCourseRequest, fileUpload, tags, categories);

        Course courseSave = courseRepository.save(course);

        return CourseResponse.convert(courseSave);
    }
    public ResponseEntity<?> updateByTeacher(UpdateCourseRequest updateCourseRequest) throws Exception {
        User teacher = userService.getUser();
        Optional<Course> courseOptional = courseRepository.findById(updateCourseRequest.getId());
        if (courseOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Question not found");
        }
        if (!teacher.getId().equals(courseOptional.get().getUser().getId())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized access or invalid course");
        }
        Course course = courseOptional.get();
        course.setName(updateCourseRequest.getName() != null ? updateCourseRequest.getName()  : course.getName());
        course.setDescription(updateCourseRequest.getDescription() != null ? updateCourseRequest.getDescription() : course.getDescription());
        course.setPrice(updateCourseRequest.getPrice() != null ? updateCourseRequest.getPrice() : course.getPrice());
        course.setDiscount(updateCourseRequest.getDiscount() != null ? updateCourseRequest.getDiscount() : course.getDiscount());
        course.setLevel(updateCourseRequest.getLevel() != null ? updateCourseRequest.getLevel() : course.getLevel());
        if (updateCourseRequest.getTags() != null && !updateCourseRequest.getTags().isEmpty()) {
            List<TagCourse> updatedTags = createOrUpdateTags(updateCourseRequest.getTags());
            course.setTags(updatedTags);
        }

        // Update or create categories if the list is not empty
        if (updateCourseRequest.getCategories() != null && !updateCourseRequest.getCategories().isEmpty()) {
            List<CategoryCourse> updatedCategories = createOrUpdateCategories(updateCourseRequest.getCategories());
            course.setCategories(updatedCategories);
        }
//        MultipartFile fileRequest = updateCourseRequest.getFile();
        if (updateCourseRequest.getFile() != null) {
            File fileUpload = fileService.createFile(updateCourseRequest.getFile());
            // Update file reference in course (logic depends on your file storage implementation)
            course.setFile(fileUpload);
        }
        return ResponseEntity.ok(CourseResponse.convert(course));

    }

    private List<TagCourse> createOrUpdateTags(List<String> tagsName) {
        return tagsName.stream()
                .map(tagName -> tagCourseRepository.findByName(tagName)
                        .orElseGet(() -> tagCourseRepository.save(TagCourse.builder().name(tagName).build())))
                .collect(Collectors.toList());
    }

    private List<CategoryCourse> createOrUpdateCategories(List<String> categoriesName) {
        return categoriesName.stream()
                .map(categoryName -> categoryCourseRepository.findByName(categoryName)
                        .orElseGet(() -> categoryCourseRepository.save(CategoryCourse.builder().name(categoryName).build())))
                .collect(Collectors.toList());
    }

    private Course buildCourse(User teacher, CreateCourseRequest createCourseRequest, File fileUpload,
                               List<TagCourse> tags, List<CategoryCourse> categories) {
        return Course.builder()
                .user(teacher)
                .name(createCourseRequest.getName())
                .description(createCourseRequest.getDescription())
                .price(createCourseRequest.getPrice())
                .level(createCourseRequest.getLevel())
                .discount(createCourseRequest.getDiscount())
                .file(fileUpload)
                .status("unchecked")
                .tags(tags)
                .categories(categories)
                .build();
    }


    public Page<Course> findAll(CourseCriteria criteria, PaginationCriteria paginationCriteria) {
        return courseRepository.findAll(new CourseFilterSpecification(criteria), PageRequestBuilder.build(paginationCriteria));
    }

    public CourseResponse deleteByTeacher(UUID uuid) {
        Course course = courseRepository.findById(uuid).orElseThrow(() -> new EntityNotFoundException("Could not find course"));
        course.setStatus("delete_by_teacher");
        return CourseResponse.convert(course);
    }

    public CourseResponse checkCourseByAdmin(UUID id) throws Exception {
        Course course = courseRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Could not find course"));
        course.setStatus("checked");
        return CourseResponse.convert(courseRepository.save(course));

    }
    public ResponseEntity<?> getCategoryByName(String name){
        Optional<CategoryCourse> categoryCourses = categoryCourseRepository.findByName(name);
        if (categoryCourses.isEmpty())return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Category not found");
        return ResponseEntity.ok(categoryCourses);
    }
    public ResponseEntity<?> getTagByName(String name){
        Optional<TagCourse> tagCourses = tagCourseRepository.findByName(name);
        if (tagCourses.isEmpty())return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tag not found");
        return ResponseEntity.ok(tagCourses);
    }


}
