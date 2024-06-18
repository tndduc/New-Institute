package com.tnduck.newinstitute.dto.response.studyProgress;

import com.tnduck.newinstitute.entity.Unit;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@SuperBuilder
public class StudyProgressResponse {
    @Schema(
            name = "id",
            description = "UUID",
            type = "String"
    )
    private String id;

    @Schema(
            name = "idUnit",
            description = "id of unit",
            type = "String",
            example = "0"
    )
    private String idUnit;
    @Schema(
            name = "status",
            description = "status of unit",
            type = "String",
            example = "0"
    )
    private String status;
    @Schema(
            name = "listLesson",
            description = "list of lesson",
            type = "List<LessonResponse>"
    )
    private List<LessonResponse> lessonResponseList;

    public static StudyProgressResponse convert(List<LessonResponse> lessonList, String id) {
        return  StudyProgressResponse.builder()
                .id(id)
                .lessonResponseList(lessonList)
                .build();

    }

}
