package com.tnduck.newinstitute.repository;

import com.tnduck.newinstitute.dto.request.course.tag.TagRequest;
import com.tnduck.newinstitute.entity.CategoryCourse;
import com.tnduck.newinstitute.entity.TagCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @author ductn
 * @project The new institute
 * @created 31/01/2024 - 10:35 PM
 */
@Repository
public interface TagCourseRepository extends JpaRepository<TagCourse, UUID> {
    Optional<TagCourse> findByName(String tagName);

    @Query(value = "SELECT * FROM tags WHERE name LIKE %?1%", nativeQuery = true)
    List<TagCourse> findListByName(String name);
}
