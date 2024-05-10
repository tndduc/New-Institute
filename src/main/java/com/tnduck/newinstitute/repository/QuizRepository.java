package com.tnduck.newinstitute.repository;

import com.tnduck.newinstitute.entity.Quiz;
import com.tnduck.newinstitute.entity.Video;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
@Repository
public interface QuizRepository extends JpaRepository<Quiz, UUID> {
    @Query(value = "SELECT * FROM quizzes WHERE lesson_id = ?1", nativeQuery = true)
    List<Quiz> findByLessonId(UUID lessonId);
}
