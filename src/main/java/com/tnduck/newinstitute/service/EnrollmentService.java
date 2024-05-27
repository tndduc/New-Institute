package com.tnduck.newinstitute.service;

import com.tnduck.newinstitute.dto.response.enroll.EnrollResponse;
import com.tnduck.newinstitute.dto.validator.EnrollmentStatus;
import com.tnduck.newinstitute.entity.Course;
import com.tnduck.newinstitute.entity.Enrollment;
import com.tnduck.newinstitute.entity.User;
import com.tnduck.newinstitute.repository.CourseRepository;
import com.tnduck.newinstitute.repository.EnrollmentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class EnrollmentService {
    private final EnrollmentRepository enrollmentRepository;
    private final UserService userService;
    private final CourseRepository courseRepository;

    public ResponseEntity<?> createEnroll(String idCourse) {
        User learner = userService.getUser();
        Optional<Course> courseOptional = courseRepository.findById(UUID.fromString(idCourse));
        if (courseOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Question not found");
        }
        if (learner == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized access");
        }
        Course course = courseOptional.get();
        if (course.getStatusAdmin().equals("ban")) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("This course have been ban by admin");
        }
        if (!course.getStatusTeacher().equals("publish")) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("access_denied");
        }
        String statusEnrollment = String.valueOf(EnrollmentStatus.APPROVED);
        if (course.getPrice().compareTo(BigDecimal.ZERO) == 0) {
            statusEnrollment = String.valueOf(EnrollmentStatus.APPROVED);
        } else {
            statusEnrollment = String.valueOf(EnrollmentStatus.PAYMENT_PENDING);
        }
        Enrollment enrollment = Enrollment.builder()
                .status(statusEnrollment)
                .course(course)
                .user(learner)
                .build();
        Enrollment enrollmentSave = enrollmentRepository.save(enrollment);
        return ResponseEntity.status(HttpStatus.CREATED).body(EnrollResponse.convert(enrollmentSave));
    }

}
