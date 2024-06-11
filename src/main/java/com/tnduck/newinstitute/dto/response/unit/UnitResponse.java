package com.tnduck.newinstitute.dto.response.unit;

import com.tnduck.newinstitute.dto.response.quiz.QuizResponse;
import com.tnduck.newinstitute.dto.response.video.VideoResponse;
import com.tnduck.newinstitute.entity.Quiz;
import com.tnduck.newinstitute.entity.Unit;
import com.tnduck.newinstitute.entity.Video;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@SuperBuilder
public class UnitResponse {
    @Schema(
            name = "id",
            description = "id of unit",
            type = "String",
            example = "0"
    )
    private UUID id;
    @Schema(
            name = "title",
            description = "title of unit",
            type = "String",
            example = "0"
    )
    private String title;
    @Schema(
            name = "ordinalNumber",
            description = "ordinalNumber of unit",
            type = "String",
            example = "0"
    )
    private int ordinalNumber;
    @Schema(
            name = "duration",
            description = "duration of unit",
            type = "String",
            example = "0"
    )
    private String duration;
    @Schema(
            name = "description",
            description = "description of unit",
            type = "String",
            example = "0"
    )
    private String description;
    @Schema(
            name = "type",
            description = "type of unit",
            type = "String",
            example = "0"
    )
    private String type;
    @Schema(
            name = "idLesson",
            description = "id of lesson",
            type = "String",
            example = "0"
    )
    private String idLesson;
    @Schema(
            name = "video",
            description = "video object",
            type = "VideoResponse",
            example = "0"
    )
    private VideoResponse video;
    @Schema(
            name = "quizResponse",
            description = "QuizResponse",
            type = "QuizResponse",
            example = "0"
    )
    private QuizResponse quizResponse;
    public static UnitResponse convert(Unit unit) {
        return UnitResponse.builder()
                .id(unit.getId())
                .title(unit.getTitle())
                .description(unit.getDescription())
                .ordinalNumber(unit.getOrdinalNumber())
                .type(unit.getType())
                .idLesson(unit.getLesson().getId().toString())
                .build();
    }
    public static UnitResponse convert(Unit unit, Video video) {
        VideoResponse videoResponse = video != null ? VideoResponse.convert(video) : null;
        return UnitResponse.builder()
                .id(unit.getId())
                .title(unit.getTitle())
                .description(unit.getDescription())
                .ordinalNumber(unit.getOrdinalNumber())
                .type(unit.getType())
                .idLesson(unit.getLesson().getId().toString())
                .video(videoResponse)
                .build();
    }
    public static UnitResponse convert(Unit unit, Quiz quiz) {
        QuizResponse quizResponse = quiz != null ? QuizResponse.convert(quiz) : null;
        return UnitResponse.builder()
                .id(unit.getId())
                .title(unit.getTitle())
                .description(unit.getDescription())
                .ordinalNumber(unit.getOrdinalNumber())
                .type(unit.getType())
                .idLesson(unit.getLesson().getId().toString())
                .quizResponse(quizResponse)
                .build();
    }
}
