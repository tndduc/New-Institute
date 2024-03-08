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
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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

    public CourseResponse updateByTeacher(UUID id, MultipartFile file, String name, String description, String level, BigDecimal discount, BigDecimal price,List<String> tags, List<String> categories) throws Exception {
        User teacher = userService.getUser();
        Course course = courseRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Could not find course"));
        if (!teacher.getId().equals(course.getUser().getId())) {
            throw new Exception("You are not the course owner");
        } else {
            course.setName(name != null ? name : course.getName());
            course.setDescription(description != null ? description : course.getDescription());
            course.setPrice(price != null ? price : course.getPrice());
            course.setDiscount(discount != null ? discount : course.getDiscount());
            course.setLevel(level != null ? level : course.getLevel());
            if (tags != null && !tags.isEmpty()) {
                List<TagCourse> updatedTags = createOrUpdateTags(tags);
                course.setTags(updatedTags);
            }

            // Update or create categories if the list is not empty
            if (categories != null && !categories.isEmpty()) {
                List<CategoryCourse> updatedCategories = createOrUpdateCategories(categories);
                course.setCategories(updatedCategories);
            }
//        MultipartFile fileRequest = updateCourseRequest.getFile();
            if (file != null) {
                File fileUpload = fileService.createFile(file);
                // Update file reference in course (logic depends on your file storage implementation)
                course.setFile(fileUpload);
            }
            return CourseResponse.convert(courseRepository.save(course));
        }
    }
}
