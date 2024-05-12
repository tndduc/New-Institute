package com.tnduck.newinstitute.repository;

import com.tnduck.newinstitute.entity.TagCourse;
import com.tnduck.newinstitute.entity.Unit;
import com.tnduck.newinstitute.entity.Video;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UnitRepository extends JpaRepository<Unit, UUID> {
    @Query("SELECT v FROM Unit v WHERE v.lesson.id = :lessonId")
    List<Unit> findByLessonId(@Param("lessonId") UUID lessonId);
}
