package com.tnduck.newinstitute.dto.response.choice;

import com.tnduck.newinstitute.dto.response.course.CourseResponse;
import com.tnduck.newinstitute.dto.response.lesson.LessonResponse;
import com.tnduck.newinstitute.dto.response.video.VideoResponse;
import com.tnduck.newinstitute.entity.Choice;
import com.tnduck.newinstitute.entity.Lesson;
import com.tnduck.newinstitute.entity.Video;
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
public class ChoiceResponse {
    @Schema(
            name = "id",
            description = "UUID",
            type = "String"
    )
    private String id;
    @NotBlank(message = "{not_blank}")
    @Size(max = 50, message = "{max_length}")
    @Schema(
            name = "content",
            description = "content of choice",
            type = "String"
    )
    private String content;
    @NotBlank(message = "{not_blank}")
    @Size(max = 50, message = "{max_length}")
    @Schema(
            name = "content",
            description = "content of choice",
            type = "Boolean"
    )
    private Boolean is_correct;
    public static ChoiceResponse convert(Choice choice) {
        return ChoiceResponse.builder()
                .id(choice.getId().toString())
                .content(choice.getContent())
                .build();
    }
    public static ChoiceResponse convert(Choice choice,Boolean isCorrect) {
        return ChoiceResponse.builder()
                .id(choice.getId().toString())
                .content(choice.getContent())
                .is_correct(isCorrect)
                .build();
    }

}
