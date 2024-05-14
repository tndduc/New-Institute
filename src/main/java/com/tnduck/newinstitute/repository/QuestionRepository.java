package com.tnduck.newinstitute.repository;

import com.tnduck.newinstitute.entity.Question;
import com.tnduck.newinstitute.entity.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @author ductn
 * @project The new institute
 * @created 31/01/2024 - 11:45 PM
 */
@Repository
public interface QuestionRepository extends JpaRepository<Question, UUID> {
    @Query(value = "SELECT * FROM questions WHERE quiz_id = ?1", nativeQuery = true)
    List<Question> findListByIdQuiz(UUID quiz_id);
}
