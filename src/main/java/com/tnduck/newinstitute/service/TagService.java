package com.tnduck.newinstitute.service;

import com.tnduck.newinstitute.entity.TagCourse;
import com.tnduck.newinstitute.repository.TagCourseRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class TagService {
    private final TagCourseRepository tagCourseRepository;

    public List<TagCourse> getTagByName(String name) {
        return tagCourseRepository.findByName(name).stream().toList();
    }
}
