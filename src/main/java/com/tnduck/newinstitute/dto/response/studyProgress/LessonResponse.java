package com.tnduck.newinstitute.dto.response.studyProgress;

import com.tnduck.newinstitute.dto.response.video.VideoResponse;
import com.tnduck.newinstitute.entity.Lesson;
import com.tnduck.newinstitute.entity.Unit;
import com.tnduck.newinstitute.entity.Video;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@SuperBuilder
public class LessonResponse {
    @Schema(
            name = "id",
            description = "UUID",
            type = "String"
    )
    private String id;
    @Schema(
            name = "listIdUnit",
            description = "list of unit",
            type = "List<UnitResponse>"
    )
    private List<UnitResponse> listIdUnit;
    public static LessonResponse convert(List<Unit> lessonList,String id) {
        List<UnitResponse> unitResponses = new ArrayList<>();
        for (Unit unit : lessonList) {
            unitResponses.add(UnitResponse.convert(unit));
        }
        return LessonResponse.builder()
                .id(id)
                .listIdUnit(unitResponses)
                .build();
    }
}
