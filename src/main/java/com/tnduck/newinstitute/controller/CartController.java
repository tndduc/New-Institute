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
    private final CartService cartService;
    @PostMapping("/add")
    @PreAuthorize("hasAuthority('USER')")
    @Operation(
            summary = "Create a new Lesson",
            security = @SecurityRequirement(name = SECURITY_SCHEME_NAME)
    )
    public ResponseEntity<?> addCart(
            @Parameter(name = "id", description = "Course ID", example = "00000000-0000-0000-0000-000000000001")
            @RequestParam(required = true) final String idCourse
    )  {
        try {
            return cartService.addToCart(idCourse);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @GetMapping("")
    public ResponseEntity<?> getListEnroll(
            @Parameter(name = "idUser", description = "User ID", example = "00000000-0000-0000-0000-000000000001")
            @RequestParam(required = true) final String idUser
    ) {
        try {
            return cartService.getCart(UUID.fromString(idUser));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

        }
    }

}
