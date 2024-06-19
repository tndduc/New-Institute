package com.tnduck.newinstitute.repository;

import com.tnduck.newinstitute.entity.QuizResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * @author ductn
 * @project The new institute
 * @created 31/01/2024 - 11:37 PM
 */
@Repository
public interface QuizResultRepository extends JpaRepository<QuizResult, UUID> {
    QuizResult findByUser_IdAndQuiz_Id(UUID userId, UUID quizId);
}
