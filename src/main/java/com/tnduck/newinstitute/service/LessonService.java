package com.tnduck.newinstitute.service;

import com.tnduck.newinstitute.repository.LessonRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author ductn
 * @project The new institute
 * @created 05/03/2024 - 8:10 AM
 */
@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class LessonService {
    private final LessonRepository lessonRepository;


//    public LessonService createLesson(){
//
//    }
}
