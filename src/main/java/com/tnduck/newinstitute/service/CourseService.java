package com.tnduck.newinstitute.service;

import com.tnduck.newinstitute.dto.request.course.CreateCourseRequest;
import com.tnduck.newinstitute.dto.request.course.UpdateCourseRequest;
import com.tnduck.newinstitute.dto.response.course.CourseResponse;
import com.tnduck.newinstitute.entity.Course;
import com.tnduck.newinstitute.entity.File;
import com.tnduck.newinstitute.entity.User;
import com.tnduck.newinstitute.entity.specification.CourseFilterSpecification;
import com.tnduck.newinstitute.entity.specification.UserFilterSpecification;
import com.tnduck.newinstitute.entity.specification.criteria.CourseCriteria;
import com.tnduck.newinstitute.entity.specification.criteria.PaginationCriteria;
import com.tnduck.newinstitute.entity.specification.criteria.UserCriteria;
import com.tnduck.newinstitute.repository.CourseRepository;
import com.tnduck.newinstitute.repository.FileRepository;
import com.tnduck.newinstitute.util.PageRequestBuilder;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

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

    public CourseResponse create(CreateCourseRequest createCourseRequest) {
        User teacher = userService.getUser();
        MultipartFile fileRequest = createCourseRequest.getFile(); // fileRequest is file in request :>>
        File fileUpload = fileService.createFile(fileRequest); // and this is file return before save in cloudinary and db
        Course course = Course.builder().user(teacher).name(createCourseRequest.getName()).description(createCourseRequest.getDescription()).price(createCourseRequest.getPrice()).file(fileUpload).status("unchecked").build();
        Course courseSave = courseRepository.save(course);

        return CourseResponse.convert(courseSave);
    }

    public Page<Course> findAll(CourseCriteria criteria, PaginationCriteria paginationCriteria) {
        return courseRepository.findAll(new CourseFilterSpecification(criteria), PageRequestBuilder.build(paginationCriteria));
    }

    public CourseResponse deleteByTeacher(String uuid) {
        UUID uuidCourse = UUID.fromString(uuid);
        Course course = courseRepository.findById(uuidCourse).orElseThrow(() -> new EntityNotFoundException("Could not find course"));
        course.setStatus("delete_by_teacher");
        return CourseResponse.convert(course);
    }
    public CourseResponse checkCourseByAdmin(String id)throws Exception{
        UUID uuid = UUID.fromString(id);
        Course course = courseRepository.findById(uuid).orElseThrow(() -> new EntityNotFoundException("Could not find course"));
        course.setStatus("checked");
        return CourseResponse.convert(courseRepository.save(course));

    }
    public CourseResponse updateByTeacher(String id, MultipartFile file, String name, String description, BigDecimal price) throws Exception {
        User teacher = userService.getUser();
        UUID uuid = UUID.fromString(id);
        Course course = courseRepository.findById(uuid).orElseThrow(() -> new EntityNotFoundException("Could not find course"));
        if (!teacher.getId().equals(course.getUser().getId())) {
            throw new Exception("You are not the course owner");
        } else {
            course.setName(name != null ? name : course.getName());
            course.setDescription(description != null ? description : course.getDescription());
            course.setPrice(price != null ? price :course.getPrice());
            if (teacher.getId().equals(course.getUser().getId())) {
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
