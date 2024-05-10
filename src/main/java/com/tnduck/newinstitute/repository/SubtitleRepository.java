package com.tnduck.newinstitute.repository;

import com.tnduck.newinstitute.entity.Subtitle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * @author ductn
 * @project The new institute
 * @created 31/01/2024 - 11:03 PM
 */
@Repository
public interface SubtitleRepository extends JpaRepository<Subtitle, UUID> {
}
