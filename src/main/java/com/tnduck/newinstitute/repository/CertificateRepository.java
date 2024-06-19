package com.tnduck.newinstitute.repository;

import com.tnduck.newinstitute.entity.Certificate;
import com.tnduck.newinstitute.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
@Repository

public interface CertificateRepository extends JpaRepository<Certificate, UUID> {
    @Query(value = "SELECT * FROM certificates WHERE student_id = ?1", nativeQuery = true)
    List<Certificate> findByUserId(UUID unitID);
}
