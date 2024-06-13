package com.tnduck.newinstitute.controller;

import com.tnduck.newinstitute.service.CartService;
import com.tnduck.newinstitute.service.EnrollmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static com.tnduck.newinstitute.util.Constants.SECURITY_SCHEME_NAME;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "012. Cart", description = "Cart API")
public class CartController {
    private final EnrollmentService enrollmentService;
    @PostMapping("/add")
    @PreAuthorize("hasAuthority('USER')")
    @Operation(
            summary = "Create a new Lesson",
            security = @SecurityRequirement(name = SECURITY_SCHEME_NAME)
    )
    public ResponseEntity<?> addCart(
            @Parameter(name = "idCourse", description = "Course ID", example = "00000000-0000-0000-0000-000000000001")
            @RequestParam(required = true) final String idCourse
    )  {
        try {
            return enrollmentService.addCart(idCourse);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @PostMapping("/add")
    @PreAuthorize("hasAuthority('USER')")
    @Operation(
            summary = "Create a new Lesson",
            security = @SecurityRequirement(name = SECURITY_SCHEME_NAME)
    )
    @GetMapping("/get-by-user-id")
    public ResponseEntity<?> getListEnroll(
    ) {
        try {
            return enrollmentService.getCart();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

        }
    }
    @PreAuthorize("hasAuthority('USER')")
    @Operation(
            summary = "Create a new Lesson",
            security = @SecurityRequirement(name = SECURITY_SCHEME_NAME)
    )
    @DeleteMapping("/delete-by-cart-id")
    public ResponseEntity<?> delete(
            @Parameter(name = "idCart", description = "Cart ID", example = "00000000-0000-0000-0000-000000000001")
            @RequestParam(required = true) final String idCart
    ) {
        try {
            return enrollmentService.deleteEnroll(UUID.fromString(idCart));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
