package com.tnduck.newinstitute.service;

import com.tnduck.newinstitute.dto.response.certificate.CertificateResponse;
import com.tnduck.newinstitute.entity.Certificate;
import com.tnduck.newinstitute.entity.User;
import com.tnduck.newinstitute.repository.CertificateRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CertificateService {
    private final UserService userService;
    private final CertificateRepository certificateRepository;

    public ResponseEntity<?> getByIdUser(String idUser) {
        List<Certificate> certificates = certificateRepository.findByUserId(UUID.fromString(idUser));
        List<CertificateResponse> certificatesResponse = new ArrayList<>();
        for (Certificate certificate : certificates) {
            certificatesResponse.add(CertificateResponse.convert(certificate));
        }
        return ResponseEntity.ok(certificatesResponse);
    }

    public ResponseEntity<?> getByAuthor() {
        User user = userService.getUser();
        List<Certificate> certificates = certificateRepository.findByUserId(user.getId());
        List<CertificateResponse> certificatesResponse = new ArrayList<>();
        for (Certificate certificate : certificates) {
            certificatesResponse.add(CertificateResponse.convert(certificate));
        }
        return ResponseEntity.ok(certificatesResponse);
    }
}
