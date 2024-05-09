package com.tnduck.newinstitute.repository;

import com.tnduck.newinstitute.entity.Certificate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CertificateRepository extends JpaRepository<Certificate, UUID> {
}
