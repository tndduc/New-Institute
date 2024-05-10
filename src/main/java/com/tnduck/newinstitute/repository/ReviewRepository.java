package com.tnduck.newinstitute.repository;

import com.tnduck.newinstitute.entity.ReviewCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * @author ductn
 * @project The new institute
 * @created 31/01/2024 - 11:18 PM
 */
@Repository
public interface ReviewRepository extends JpaRepository<ReviewCourse, UUID> {
}
