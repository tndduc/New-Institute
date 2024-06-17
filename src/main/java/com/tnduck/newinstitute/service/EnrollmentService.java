package com.tnduck.newinstitute.service;

import com.tnduck.newinstitute.dto.request.OrderRequestDTO;
import com.tnduck.newinstitute.dto.response.enroll.EnrollResponse;
import com.tnduck.newinstitute.dto.validator.CourseStatus;
import com.tnduck.newinstitute.entity.Course;
import com.tnduck.newinstitute.entity.Enrollment;
import com.tnduck.newinstitute.entity.Payment;
import com.tnduck.newinstitute.entity.User;
import com.tnduck.newinstitute.repository.CourseRepository;
import com.tnduck.newinstitute.repository.EnrollmentRepository;
import com.tnduck.newinstitute.repository.PaymentRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class EnrollmentService {
    private final EnrollmentRepository enrollmentRepository;
    private final UserService userService;
    private final CourseRepository courseRepository;
    private final OrderPaymentService orderPaymentService;
    private final PaymentRepository paymentRepository;
    public ResponseEntity<?> createEnroll(String idCourse) {
        User learner = userService.getUser();
        Optional<Course> courseOptional = courseRepository.findById(UUID.fromString(idCourse));
        if (courseOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course not found");
        }
        List<Enrollment> enrollments = enrollmentRepository.getEnrollmentListByUserID(learner.getId());
        for (Enrollment enrollment : enrollments) {
            if (enrollment.getCourse().getId().equals(courseOptional.get().getId())) {
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("You have already enrolled this course");
            }
        }
        if (learner == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized access");
        }
        Course course = courseOptional.get();
        if (course.getStatusAdmin().equals("ban")) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("This course have been ban by admin");
        }
        if (!course.getStatusTeacher().equals("public")) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("access_denied");
        }
        String statusEnrollment = String.valueOf(CourseStatus.APPROVED);
        if (course.getPrice().compareTo(BigDecimal.ZERO) == 0) {
            statusEnrollment = String.valueOf(CourseStatus.APPROVED);
        } else {
            statusEnrollment = String.valueOf(CourseStatus.PAYMENT_PENDING);
        }
        Enrollment enrollment = Enrollment.builder()
                .status(statusEnrollment)
                .course(course)
                .user(learner)
                .build();
        Enrollment enrollmentSave = enrollmentRepository.save(enrollment);
        return ResponseEntity.status(HttpStatus.CREATED).body(EnrollResponse.convert(enrollmentSave));
    }
    public List<Enrollment> getEnrollmentByUserId(UUID userId) {
        return enrollmentRepository.getEnrollmentListByUserID(userId);
    }

    public ResponseEntity<?> addCart(String idCourse) {
        User learner = userService.getUser();
        Optional<Course> courseOptional = courseRepository.findById(UUID.fromString(idCourse));
        if (courseOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course not found");
        }
        List<Enrollment> enrollments = enrollmentRepository.getEnrollmentListByUserID(learner.getId());
        for (Enrollment enrollment : enrollments) {
            if (enrollment.getCourse().getId().equals(courseOptional.get().getId())) {
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("You have already add cart this course");
            }
        }
        if (learner == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized access");
        }
        Course course = courseOptional.get();
        if (course.getStatusAdmin().equals("ban")) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("This course have been ban by admin");
        }
        if (!course.getStatusTeacher().equals("public")) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("access_denied");
        }
        String statusEnrollment = String.valueOf(CourseStatus.APPROVED);
        if (course.getPrice().compareTo(BigDecimal.ZERO) == 0) {
            statusEnrollment = String.valueOf(CourseStatus.APPROVED);
        } else {
            statusEnrollment = String.valueOf(CourseStatus.ON_CART);
        }
        Enrollment enrollment = Enrollment.builder()
                .status(statusEnrollment)
                .course(course)
                .user(learner)
                .build();
        Enrollment enrollmentSave = enrollmentRepository.save(enrollment);
        return ResponseEntity.status(HttpStatus.CREATED).body(EnrollResponse.convert(enrollmentSave));
    }
    public ResponseEntity<?> deleteEnroll(UUID id) {
        Optional<Enrollment> enrollmentOptional = enrollmentRepository.findById(id);
        if (enrollmentOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Enrollment or Cart not found");
        }
        Enrollment enrollment = enrollmentOptional.get();
        User learner = userService.getUser();
        if (!learner.equals(enrollment.getUser())) { // Use .equals() for object comparison
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized access");
        }
        enrollmentRepository.delete(enrollment);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    public ResponseEntity<?> getEnrollment() {
        User learner = userService.getUser();
        List<Enrollment> enrollments = enrollmentRepository.getEnrollmentListByUserID(learner.getId());
        if (enrollments.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No enrollments found for user.");
        }
        List<EnrollResponse> enrollResponses = enrollments.stream()
                .filter(enrollment ->
                        enrollment.getStatus().equals(CourseStatus.APPROVED.toString()) ||
                                enrollment.getStatus().equals(CourseStatus.COMPLETED.toString()) ||
                                enrollment.getStatus().equals(CourseStatus.PAYMENT_PENDING.toString()) ||
                                enrollment.getStatus().equals(CourseStatus.CERTIFIED.toString()))
                .map(EnrollResponse::convert)
                .collect(Collectors.toList());
        return ResponseEntity.ok(enrollResponses);
    }

    public ResponseEntity<?> getCart() {
        User learner = userService.getUser();
        List<EnrollResponse> enrollResponses = new ArrayList<>();
        List<Enrollment> enrollments = enrollmentRepository.getEnrollmentListByUserID(learner.getId());
        for (Enrollment enrollment: enrollments){
            if (enrollment.getStatus().equals(CourseStatus.ON_CART.toString())
                    || enrollment.getStatus().equals(CourseStatus.PAYMENT_PENDING.toString())
            ){
                enrollResponses.add(EnrollResponse.convert(enrollment));
            }

        }
        return ResponseEntity.status(HttpStatus.OK).body(enrollResponses);
    }

    public ResponseEntity<?> buyAll(HttpServletRequest request) throws UnsupportedEncodingException {
        User learner = userService.getUser();
        List<Course> courseToBuy = new ArrayList<>();
        List<Enrollment> enrollments = enrollmentRepository.getEnrollmentListByUserID(learner.getId());
        for (Enrollment enrollment: enrollments){
            if (enrollment.getStatus().equals(CourseStatus.ON_CART.toString())
                    || enrollment.getStatus().equals(CourseStatus.PAYMENT_PENDING.toString())
            ){
                courseToBuy.add(enrollment.getCourse());
            }
        }
        Payment payment = Payment.builder()
                .courses(courseToBuy)
                .status(CourseStatus.PAYMENT_PENDING.toString())
                .user(learner)
                .build();
        Payment paymentSave = paymentRepository.save(payment);
        BigDecimal price = BigDecimal.ZERO;
        for (Course c : courseToBuy) {
            price = price.add(c.getPrice().subtract(c.getDiscount()));
        }
        Long amount = price.longValueExact();
        OrderRequestDTO orderRequestDTO = OrderRequestDTO.builder()
                .amount(amount)
                .orderInfo(paymentSave.getId().toString())
                .build();
        Map<String, Object> result = orderPaymentService.createOrder(request, orderRequestDTO);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    public void confirmPayment(Map<String, Object> callBackInfo) {
        String orderInfo = (String) callBackInfo.get("vnp_OrderInfo");
        UUID orderId = UUID.fromString(orderInfo);
        Optional<Payment> payment = paymentRepository.findById(orderId);
        payment.get().setStatus(CourseStatus.APPROVED.toString());
        paymentRepository.save(payment.get());
        List<Course> courses = payment.get().getCourses();
        User user = payment.get().getUser();
        List<Enrollment> enrollments = new ArrayList<>();
        for (Course course : courses) {
            Optional<Enrollment> enrollment = enrollmentRepository.getEnrollmentListByUserIDAndIDCourse(user.getId(), course.getId());
            if (!enrollment.isPresent()){
                Enrollment enrollmentAdd = Enrollment.builder()
                        .status(CourseStatus.APPROVED.toString())
                        .course(course)
                        .user(payment.get().getUser())
                        .build();
                enrollmentRepository.save(enrollmentAdd);
            }else {
                Enrollment enrollmentSave = enrollment.get();
                enrollmentSave.setStatus(CourseStatus.APPROVED.toString());
                enrollmentRepository.save(enrollmentSave);
            }
        }
    }

    public ResponseEntity<?> buyOne(HttpServletRequest request, String idCourse) throws UnsupportedEncodingException {
        User learner = userService.getUser();
        Optional<Course> courseToBuy = courseRepository.findById(UUID.fromString(idCourse));
        if (!courseToBuy.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course not found");
        }
        Course course = courseToBuy.get();
        List<Course> courses = new ArrayList<>();
        courses.add(course);
        Payment payment = Payment.builder()
                .courses(courses)
                .status(CourseStatus.PAYMENT_PENDING.toString())
                .user(learner)
                .build();
        Payment paymentSave = paymentRepository.save(payment);
        BigDecimal price = BigDecimal.ZERO;
        price = price.add(course.getPrice().subtract(course.getDiscount()));
        Long amount = price.longValueExact();
        OrderRequestDTO orderRequestDTO = OrderRequestDTO.builder()
                .amount(amount)
                .orderInfo(paymentSave.getId().toString())
                .build();
        Map<String, Object> result = orderPaymentService.createOrder(request, orderRequestDTO);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
