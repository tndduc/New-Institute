package com.tnduck.newinstitute.repository;

import com.tnduck.newinstitute.entity.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * @author ductn
 * @project The new institute
 * @created 23/02/2024 - 10:30 PM
 */
@Repository
public interface FileRepository extends JpaRepository<File,UUID> {
}
