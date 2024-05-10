package com.tnduck.newinstitute.repository;

import com.tnduck.newinstitute.entity.CategoryCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * @author ductn
 * @project The new institute
 * @created 06/03/2024 - 3:38 PM
 */
@Repository
public interface CategoryCourseRepository extends JpaRepository<CategoryCourse, UUID> {
    Optional<CategoryCourse> findByName(String categoryName);
}
