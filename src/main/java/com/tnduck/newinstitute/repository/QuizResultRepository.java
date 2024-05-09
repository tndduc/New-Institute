package com.tnduck.newinstitute.repository;

import com.tnduck.newinstitute.entity.QuizResult;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * @author ductn
 * @project The new institute
 * @created 31/01/2024 - 11:37 PM
 */
public interface QuizResultRepository extends JpaRepository<QuizResult, UUID> {
}
