package com.tnduck.newinstitute.service;

import com.tnduck.newinstitute.dto.response.studyProgress.LessonResponse;
import com.tnduck.newinstitute.dto.response.studyProgress.StudyProgressResponse;
import com.tnduck.newinstitute.entity.*;
import com.tnduck.newinstitute.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.function.EntityResponse;

import javax.swing.text.html.parser.Entity;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class StudyProgressService {
    private final LessonRepository lessonRepository;
    private final UserService userService;
    private final CourseRepository courseRepository;
    private final UnitRepository unitRepository;
    private final StudyProgressRepository studyProgressRepository;

    public ResponseEntity<?> updateStudyProgress(String unitID) {
        User user = userService.getUser();
        Optional<Unit> unit = unitRepository.findById(UUID.fromString(unitID));
        if(!unit.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Unit not found");
        }
        Optional<Course> course = courseRepository.findById(unit.get().getLesson().getCourse().getId());
        List<Lesson> lessons = lessonRepository.findByCourse_Id(course.get().getId());
        List<Unit> units = new ArrayList<>();
        for (Lesson lesson : lessons) {
            List<Unit> unitList = unitRepository.findByLessonId(lesson.getId());
            units.addAll(unitList);
        }
        for (Unit u : units) {
            if (u.getId().equals(unit.get().getId())) {
                return ResponseEntity.status(HttpStatus.OK).body("unit have been learned");
            }
        }
        StudyProgress studyProgress = StudyProgress.builder()
                .user(user)
                .status("on_progress")
                .unit(unit.get())
                .build();
        StudyProgress studyProgressSave = studyProgressRepository.save(studyProgress);

        return ResponseEntity.status(HttpStatus.CREATED).body("success add unit to study progress");
    }
    public ResponseEntity<?> getStudyProgress(String IdCourse){
        Optional<Course> course = courseRepository.findById(UUID.fromString(IdCourse));
        if (!course.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("course not found");
        }
        List<Lesson> lessons = lessonRepository.findByCourse_Id(course.get().getId());
        User user = userService.getUser();
        List<StudyProgressResponse> studyProgressResponses = new ArrayList<>();
        List<StudyProgress> studyProgresses = studyProgressRepository.findByUserId(user.getId());
        List<LessonResponse> lessonResponseList = new ArrayList<>();
        for (Lesson lesson : lessons) {
            List<Unit> unitList = unitRepository.findByLessonId(lesson.getId());
            List<Unit> unitStudyProgress = new ArrayList<>();
            for (Unit unit : unitList) {
                for (StudyProgress studyProgress : studyProgresses) {
                    if (studyProgress.getUnit().getId().equals(unit.getId())) {
                        unitStudyProgress.add(unit);
                    }
                }
            }
            lessonResponseList.add(LessonResponse.convert(unitStudyProgress, lesson.getId().toString()));
        }
        StudyProgressResponse studyProgressResponse = StudyProgressResponse.convert(lessonResponseList,course.get().getId().toString());
        return ResponseEntity.status(HttpStatus.FOUND).body(studyProgressResponse);
    }
}
