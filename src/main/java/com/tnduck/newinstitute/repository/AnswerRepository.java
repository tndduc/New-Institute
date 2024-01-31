package com.tnduck.newinstitute.repository;

import com.tnduck.newinstitute.entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * @author ductn
 * @project The new institute
 * @created 31/01/2024 - 11:45 PM
 */
public interface AnswerRepository extends JpaRepository<Answer, UUID> {
}
