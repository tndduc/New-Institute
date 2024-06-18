package com.tnduck.newinstitute.service;

import com.tnduck.newinstitute.dto.request.unit.CreateUnitRequest;
import com.tnduck.newinstitute.dto.request.unit.UpdateUnitRequest;
import com.tnduck.newinstitute.dto.response.unit.UnitResponse;
import com.tnduck.newinstitute.entity.*;
import com.tnduck.newinstitute.exception.NotFoundException;
import com.tnduck.newinstitute.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UnitService {
    private final UnitRepository unitRepository;
    private final LessonRepository lessonRepository;
    private final VideoRepository videoRepository;
    private final UserService userService;
    private final CourseRepository courseRepository;
    private final QuizRepository quizRepository;

    /**
     * Get the units and associated content for a given lesson.
     *
     * @param uuid the id of the lesson
     * @return a list of {@link UnitResponse} objects representing the units and associated content
     */
    public ResponseEntity<?> getUnitByIdLesson(UUID uuid) {
        Optional<Lesson> lessonOptional = lessonRepository.findById(uuid);
        if (lessonOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Lesson not found");
        }
        Optional<Course> courseOptional = courseRepository.findById(lessonOptional.get().getCourse().getId());
        User teacher = userService.getUser();
        if (teacher == null || courseOptional.isEmpty() || !teacher.getId().equals(courseOptional.get().getUser().getId())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized access or invalid course");
        }
        // getUnitAndAssociatedContent by id lesson
        List<UnitResponse> responseList = getUnitAndAssociatedContent(lessonOptional.get().getId());
        return ResponseEntity.ok(responseList);
    }

    /**
     * Get the units and associated content for a given lesson.
     *
     * @param unitId the id of the lesson
     * @return a list of {@link UnitResponse} objects representing the units and associated content
     */
    public List<UnitResponse> getUnitAndAssociatedContent(UUID unitId) {
        List<Unit> unitList = unitRepository.findByLessonId(unitId);
        List<UnitResponse> responseList = new ArrayList<>();
        for (Unit unit : unitList) {
            Optional<Video> videoOptional = videoRepository.findByUnitId(unit.getId());
            if (unit.getType().equals("video")) {
                videoOptional.ifPresent(video -> responseList.add(UnitResponse.convert(unit, video)));
            }
            Optional<Quiz> quizOptional = quizRepository.findByUnitId(unit.getId());
            if (unit.getType().equals("quiz")) {
                quizOptional.ifPresent(quiz -> responseList.add(UnitResponse.convert(unit, quiz)));
            }
        }
        return responseList;
    }

    /**
     * Creates a new unit for a given lesson.
     *
     * @param request the request containing the details of the new unit
     * @return a response indicating the successful creation of the unit
     */
    @Transactional
    public ResponseEntity<?> createUnit(CreateUnitRequest request) {
        // Check if the specified lesson exists
        Optional<Lesson> lessonOptional = lessonRepository.findById(UUID.fromString(request.getLessonId()));
        if (lessonOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Lesson not found");
        }

        // Check if the user has access to the specified course
        Optional<Course> courseOptional = courseRepository.findById(lessonOptional.get().getCourse().getId());
        User teacher = userService.getUser();
        if (teacher == null || courseOptional.isEmpty() || !teacher.getId().equals(courseOptional.get().getUser().getId())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized access or invalid course");
        }

        // Create a new unit with the provided details
        Unit unit = new Unit();
        unit.setTitle(request.getTitle());
        unit.setOrdinalNumber(request.getOrdinalNumber());
        unit.setType(request.getType());
        unit.setLesson(lessonOptional.get());
        // Save the new unit to the database
        Unit unitSave = unitRepository.save(unit);

        // Return a response indicating the successful creation of the unit
        return ResponseEntity.ok(UnitResponse.convert(unitSave));
    }

    /**
     * Updates an existing unit with the provided details.
     *
     * @param updateUnitRequest the request containing the details of the unit to be updated
     * @return a response indicating the successful update of the unit
     */
    @Transactional
    public ResponseEntity<?> updateUnit(UpdateUnitRequest updateUnitRequest) {
        // Check if the specified unit exists
        Optional<Unit> unitOptional = unitRepository.findById(UUID.fromString(updateUnitRequest.getId()));
        if (unitOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Unit not found");
        }

        // Check if the user has access to the specified course
        Optional<Course> courseOptional = courseRepository.findById(unitOptional.get().getLesson().getCourse().getId());
        User teacher = userService.getUser();
        if (teacher == null || courseOptional.isEmpty() || !teacher.getId().equals(courseOptional.get().getUser().getId())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized access or invalid course");
        }
        Boolean isPreviewAvailable = false;
        if (updateUnitRequest.getIsPreview().equals("true")){
            isPreviewAvailable = true;
        }
        // Update the unit with the provided details
        Unit unit = unitOptional.get();
        unit.setTitle(updateUnitRequest.getTitle() != null ? updateUnitRequest.getTitle() : unit.getTitle());
        unit.setType(updateUnitRequest.getType() != null ? updateUnitRequest.getType() : unit.getType());
        unit.setIsPreview(isPreviewAvailable);
        // Save the updated unit to the database
        Unit unitSave = unitRepository.save(unit);

        // Return a response indicating the successful update of the unit
        return ResponseEntity.ok(UnitResponse.convert(unitSave));
    }

    /**
     * Deletes a unit with the specified id.
     *
     * @param unitId the id of the unit to be deleted
     * @return a response indicating the successful deletion of the unit
     */
    @Transactional
    public ResponseEntity<?> deleteUnit(UUID unitId) {
        // Check if the specified unit exists
        Optional<Unit> unitOptional = unitRepository.findById(unitId);
        if (unitOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Unit not found");
        }

        // Check if the user has access to the specified course
        Optional<Course> courseOptional = courseRepository.findById(unitOptional.get().getLesson().getCourse().getId());
        User teacher = userService.getUser();
        if (teacher == null || courseOptional.isEmpty() || !teacher.getId().equals(courseOptional.get().getUser().getId())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized access or invalid course");
        }

        // Get the unit to be deleted
        Unit unit = unitOptional.get();

        // Delete associated video, if any
        Optional<Video> videoOptional = videoRepository.findByUnitId(unit.getId());
        videoOptional.ifPresent(video -> videoRepository.delete(video));

        // Delete associated quiz, if any
        Optional<Quiz> quizOptional = quizRepository.findByUnitId(unit.getId());
        quizOptional.ifPresent(quiz -> quizRepository.delete(quiz));

        // Delete the unit from the database
        unitRepository.delete(unit);

        return ResponseEntity.ok("Unit have id :" + unitId + " deleted successfully");
    }

}
