package com.tnduck.newinstitute.repository;

import com.tnduck.newinstitute.entity.Choice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository

public interface ChoiceRepository extends JpaRepository<Choice, UUID> {
}
