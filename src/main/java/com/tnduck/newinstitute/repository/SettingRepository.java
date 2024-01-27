package com.tnduck.newinstitute.repository;

import com.tnduck.newinstitute.entity.Setting;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;
/**
 * @author ductn
 * @project The new institute
 * @created 26/01/2024 - 8:55 PM
 */
public interface SettingRepository extends JpaRepository<Setting, UUID> {
}
