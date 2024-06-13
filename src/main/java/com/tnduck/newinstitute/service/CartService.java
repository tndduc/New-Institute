package com.tnduck.newinstitute.service;

import com.tnduck.newinstitute.dto.response.cart.CartResponse;
import com.tnduck.newinstitute.dto.response.enroll.EnrollResponse;
import com.tnduck.newinstitute.dto.validator.CourseStatus;
import com.tnduck.newinstitute.entity.Cart;
import com.tnduck.newinstitute.entity.Course;
import com.tnduck.newinstitute.entity.Enrollment;
import com.tnduck.newinstitute.entity.User;
import com.tnduck.newinstitute.repository.CartRepository;
import com.tnduck.newinstitute.repository.CourseRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CartService {
    private final CartRepository cartRepository;
    private final UserService userService;
    private final CourseRepository courseRepository;
    public ResponseEntity<?> addToCart(String idCourse) {
        User learner = userService.getUser();
        Optional<Course> courseOptional = courseRepository.findById(UUID.fromString(idCourse));
        if (courseOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course not found");
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
        String statusCourse = String.valueOf(CourseStatus.APPROVED);
        if (course.getPrice().compareTo(BigDecimal.ZERO) == 0) {
            statusCourse = String.valueOf(CourseStatus.APPROVED);
        } else {
            statusCourse = String.valueOf(CourseStatus.WAITLIST);
        }
        Cart cartRaw = Cart.builder()
                .status(statusCourse)
                .course(course)
                .user(learner)
                .build();
        Cart cartSave = cartRepository.save(cartRaw);
        return ResponseEntity.status(HttpStatus.CREATED).body(CartResponse.convert(cartSave));
    }
    public ResponseEntity<?> getCart(UUID userId) {
        List<CartResponse> cartResponses = new ArrayList<>();
        List<Cart> carts = cartRepository.getCartListByUserID(userId);
        for (Cart cart: carts){
            cartResponses.add(CartResponse.convert(cart));
        }
        return ResponseEntity.status(HttpStatus.FOUND).body(cartResponses);
    }
    public ResponseEntity<?> deleteCart(UUID cartId) {
        Optional<Cart> cartOptional = cartRepository.findById(cartId);
        if (cartOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cart not found");
        }
        Cart cart = cartOptional.get();
        User learner = userService.getUser();
        if (!learner.equals(cart.getUser())) { // Use .equals() for object comparison
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized access");
        }
        cartRepository.delete(cart);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("delete_cart_success");
    }
}
