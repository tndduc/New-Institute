package com.tnduck.newinstitute.repository;

import com.tnduck.newinstitute.entity.Video;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * @author ductn
 * @project The new institute
 * @created 31/01/2024 - 10:53 PM
 */
public interface VideoRepository extends JpaRepository<Video, UUID> {
}
