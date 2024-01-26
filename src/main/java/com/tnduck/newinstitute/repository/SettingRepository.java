package com.tnduck.newinstitute.repository;

import com.tnduck.newinstitute.entity.Setting;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SettingRepository extends JpaRepository<Setting, UUID> {
}
