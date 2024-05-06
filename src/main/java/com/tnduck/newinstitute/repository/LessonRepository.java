package com.tnduck.newinstitute.repository;

import com.tnduck.newinstitute.entity.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

/**
 * @author ductn
 * @project The new institute
 * @created 31/01/2024 - 10:20 PM
 */
public interface LessonRepository extends JpaRepository<Lesson, UUID> {
    List<Lesson> findByCourse_Id(UUID courseId);
}
