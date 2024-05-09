package com.tnduck.newinstitute.repository;

import com.tnduck.newinstitute.entity.Choice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ChoiceRepository extends JpaRepository<Choice, UUID> {
}
