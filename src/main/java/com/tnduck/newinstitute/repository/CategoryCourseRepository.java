package com.tnduck.newinstitute.repository;

import com.tnduck.newinstitute.entity.CategoryCourse;
import com.tnduck.newinstitute.entity.Choice;
import com.tnduck.newinstitute.entity.Unit;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
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
    @Query(value = "SELECT * FROM categories WHERE name LIKE %?1%", nativeQuery = true)
    List<CategoryCourse> findListByName(String name);
}
