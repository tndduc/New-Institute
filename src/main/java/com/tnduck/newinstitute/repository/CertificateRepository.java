package com.tnduck.newinstitute.repository;

import com.tnduck.newinstitute.entity.Certificate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository

public interface CertificateRepository extends JpaRepository<Certificate, UUID> {
}
