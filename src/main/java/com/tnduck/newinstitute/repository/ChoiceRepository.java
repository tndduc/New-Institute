package com.tnduck.newinstitute.repository;

import com.tnduck.newinstitute.entity.Choice;
import com.tnduck.newinstitute.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
@Repository

public interface ChoiceRepository extends JpaRepository<Choice, UUID> {
    @Query(value = "SELECT * FROM choices WHERE question_id = ?1", nativeQuery = true)
    List<Choice> findListByIdQuestion(UUID question_id);
}
