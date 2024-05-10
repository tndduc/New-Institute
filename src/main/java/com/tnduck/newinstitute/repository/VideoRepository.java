package com.tnduck.newinstitute.repository;

import com.tnduck.newinstitute.entity.Video;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * @author ductn
 * @project The new institute
 * @created 31/01/2024 - 10:53 PM
 */
@Repository
public interface VideoRepository extends JpaRepository<Video, UUID> {
    @Query("SELECT v FROM Video v WHERE v.lesson.id = :lessonId")
    List<Video> findByLessonId(@Param("lessonId") UUID lessonId);

}
