package com.tnduck.newinstitute.repository;

import com.tnduck.newinstitute.entity.Course;
import com.tnduck.newinstitute.entity.StudyProgress;
import com.tnduck.newinstitute.entity.Unit;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface StudyProgressRepository extends JpaRepository<StudyProgress, UUID> {
    @Query(value = "SELECT * FROM study_progress WHERE student_id = ?1", nativeQuery = true)
    List<StudyProgress> findByUserId(UUID unitID);
}
