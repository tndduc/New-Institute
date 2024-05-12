package com.tnduck.newinstitute.dto.response.lesson;


import com.tnduck.newinstitute.dto.response.AbstractBaseResponse;
import com.tnduck.newinstitute.dto.response.course.CourseResponse;
import com.tnduck.newinstitute.dto.response.quiz.QuizResponse;
import com.tnduck.newinstitute.dto.response.unit.UnitResponse;
import com.tnduck.newinstitute.dto.response.video.VideoResponse;
import com.tnduck.newinstitute.entity.Lesson;
import com.tnduck.newinstitute.entity.Quiz;
import com.tnduck.newinstitute.entity.Video;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@SuperBuilder
public class LessonResponse extends AbstractBaseResponse {
    @Schema(
            name = "id",
            description = "UUID",
            type = "String",
            example = "91b2999d-d327-4dc8-9956-2fadc0dc8778"
    )
    private String id;
    @NotBlank(message = "{not_blank}")
    @Size(max = 50, message = "{max_length}")
    @Schema(
            name = "title",
            description = "Title of course",
            type = "String",
            requiredMode = Schema.RequiredMode.REQUIRED,
            example = "Nextjs Zero to Hero"
    )
    private String title;
    @Schema(
            name = "content",
            description = "Content of course",
            type = "String",
            requiredMode = Schema.RequiredMode.REQUIRED,
            example = "Become a Web Development Pro with these valuable skills. Find the right instructor for you. Choose from many topics, skill levels, and languages. Download To Your Phone. Courses in 75 Languages. Stay Updated with AI. Learn ChatGPT. ChatGPT AI Course."
    )
    private String content;
    @Schema(
            name = "idCourse",
            description = "idCourse of course",
            type = "String",
            requiredMode = Schema.RequiredMode.REQUIRED,
            example = ""
    )
    private String idCourse;
    @Schema(
            name = "ordinal_number",
            description = "ordinal number",
            type = "int",
            example = "1"
    )
    private int ordinalNumber;
    @ArraySchema(
            schema = @Schema(
                    description = "List of unit"
            )
    )
    private List<UnitResponse> units;

    public static LessonResponse convert(Lesson lesson, List<UnitResponse> unitResponse) {

        CourseResponse courseResponse = CourseResponse.convert(lesson.getCourse());
        return LessonResponse.builder()
                .id(lesson.getId().toString())
                .title(lesson.getTitle())
                .content(lesson.getContent())
                .idCourse(courseResponse.getId())
                .units(unitResponse)
                .ordinalNumber(lesson.getOrdinalNumber())
                .build();
    }
    public static LessonResponse convert(Lesson lesson) {
        return LessonResponse.builder()
                .id(lesson.getId().toString())
                .title(lesson.getTitle())
                .content(lesson.getContent())
                .idCourse(lesson.getCourse().getId().toString())
                .ordinalNumber(lesson.getOrdinalNumber())
                .build();
    }
}
