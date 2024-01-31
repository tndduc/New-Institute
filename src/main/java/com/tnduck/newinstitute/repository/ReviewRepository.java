package com.tnduck.newinstitute.repository;

import com.tnduck.newinstitute.entity.ReviewCourse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * @author ductn
 * @project The new institute
 * @created 31/01/2024 - 11:18 PM
 */
public interface ReviewRepository extends JpaRepository<ReviewCourse, UUID> {
}
