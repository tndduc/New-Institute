package com.tnduck.newinstitute.service;

import com.tnduck.newinstitute.entity.Setting;
import com.tnduck.newinstitute.repository.SettingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SettingService {
    private final SettingRepository settingRepository;

    public Setting create() {
        return settingRepository.save(Setting.builder().key("foo").value("bar").build());
    }
}
