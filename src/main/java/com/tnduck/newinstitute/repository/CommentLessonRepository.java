package com.tnduck.newinstitute.repository;

import com.tnduck.newinstitute.entity.CommentLesson;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * @author ductn
 * @project The new institute
 * @created 31/01/2024 - 10:32 PM
 */
public interface CommentLessonRepository extends JpaRepository<CommentLesson, UUID> {
}
