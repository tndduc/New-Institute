package com.tnduck.newinstitute.service;

import com.tnduck.newinstitute.entity.Setting;
import com.tnduck.newinstitute.repository.SettingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
/**
 * @author ductn
 * @project The new institute
 * @created 26/01/2024 - 10:41 PM
 */
@Service
@RequiredArgsConstructor
public class SettingService {
    private final SettingRepository settingRepository;

    public Setting create() {
        return settingRepository.save(Setting.builder().key("foo").value("bar").build());
    }
}
