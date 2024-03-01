package com.tnduck.newinstitute.repository;

import com.tnduck.newinstitute.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

/**
 * @author ductn
 * @project The new institute
 * @created 27/01/2024 - 10:20 PM
 */
public interface CourseRepository extends JpaRepository<Course, UUID>, JpaSpecificationExecutor<Course> {
}
