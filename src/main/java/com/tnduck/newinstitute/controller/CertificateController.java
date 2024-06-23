package com.tnduck.newinstitute.controller;

import com.tnduck.newinstitute.service.CertificateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.tnduck.newinstitute.util.Constants.SECURITY_SCHEME_NAME;

@RestController
@RequestMapping("/certificate")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "015. CertificateController", description = "CertificateController API")
public class CertificateController {
    private final CertificateService certificateService;

    @PostMapping("/get-by-id")
    public ResponseEntity<?> get(
            @Parameter(name = "idUser", description = "User ID", example = "00000000-0000-0000-0000-000000000001")
            @RequestParam(required = true) final String idUser
    )  {
        try {
            return certificateService.getByIdUser(idUser);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @PostMapping("/get")
    public ResponseEntity<?> getByAuthor(
    )  {
        try {
            return certificateService.getByAuthor();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
