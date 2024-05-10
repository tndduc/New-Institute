package com.tnduck.newinstitute.repository;

import com.tnduck.newinstitute.entity.CommentLesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * @author ductn
 * @project The new institute
 * @created 31/01/2024 - 10:32 PM
 */
@Repository

public interface CommentLessonRepository extends JpaRepository<CommentLesson, UUID> {
}
