package com.tnduck.newinstitute.repository;

import com.tnduck.newinstitute.entity.Course;
import com.tnduck.newinstitute.entity.Quiz;
import com.tnduck.newinstitute.entity.Unit;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @author ductn
 * @project The new institute
 * @created 27/01/2024 - 10:20 PM
 */
@Repository
public interface CourseRepository extends JpaRepository<Course, UUID>, JpaSpecificationExecutor<Course> {
    @Query(value = "SELECT * FROM courses WHERE teacher_id = ?1", nativeQuery = true)
    List<Course> findByUserId(UUID unitID);
}
