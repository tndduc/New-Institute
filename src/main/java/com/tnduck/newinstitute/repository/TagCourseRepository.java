package com.tnduck.newinstitute.repository;

import com.tnduck.newinstitute.dto.request.course.tag.TagRequest;
import com.tnduck.newinstitute.entity.TagCourse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

/**
 * @author ductn
 * @project The new institute
 * @created 31/01/2024 - 10:35 PM
 */
public interface TagCourseRepository extends JpaRepository<TagCourse, UUID> {
    Optional<TagCourse> findByName(String tagName);
}
