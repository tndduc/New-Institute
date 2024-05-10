package com.tnduck.newinstitute.repository;

import com.tnduck.newinstitute.entity.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * @author ductn
 * @project The new institute
 * @created 31/01/2024 - 11:26 PM
 */
@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, UUID> {
}
