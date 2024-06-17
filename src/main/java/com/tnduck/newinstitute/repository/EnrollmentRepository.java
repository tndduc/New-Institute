package com.tnduck.newinstitute.repository;

import com.tnduck.newinstitute.entity.Course;
import com.tnduck.newinstitute.entity.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @author ductn
 * @project The new institute
 * @created 31/01/2024 - 11:26 PM
 */
@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, UUID> {
    @Query(value = "SELECT * FROM enrollments WHERE student_id = ?1", nativeQuery = true)
    List<Enrollment> getEnrollmentListByUserID(UUID userID);
    @Query(value = "SELECT * FROM enrollments WHERE student_id = ?1 AND course_id = ?2", nativeQuery = true)
    Optional<Enrollment> getEnrollmentListByUserIDAndIDCourse(UUID userID,UUID courseID);
}
