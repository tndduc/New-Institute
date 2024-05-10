package com.tnduck.newinstitute.dto.response.question;

import com.tnduck.newinstitute.dto.response.choice.ChoiceResponse;
import com.tnduck.newinstitute.dto.response.course.CourseResponse;
import com.tnduck.newinstitute.dto.response.lesson.LessonResponse;
import com.tnduck.newinstitute.dto.response.video.VideoResponse;
import com.tnduck.newinstitute.entity.Choice;
import com.tnduck.newinstitute.entity.Lesson;
import com.tnduck.newinstitute.entity.Question;
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
public class QuestionResponse {
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
    @Schema(
            name = "description",
            description = "description of choice",
            type = "String"
    )
    private String description;
    @Schema(
            name = "level",
            description = "level of question",
            type = "int"
    )
    private int level;
    @ArraySchema(
            schema = @Schema(
                    description = "List of choice"
            )
    )
    private List<ChoiceResponse> choiceResponses;
    public static QuestionResponse convert(Question question, List<Choice> choices) {
        List<ChoiceResponse> choiceResponseList = new ArrayList<>();
        for (Choice choice : choices) {
            choiceResponseList.add(ChoiceResponse.convert(choice));
        }
        return QuestionResponse.builder()
                .id(question.getId().toString())
                .content(question.getContent())
                .description(question.getDescription())
                .level(question.getLevel())
                .choiceResponses(choiceResponseList)
                .build();
    }
    public static QuestionResponse convert(Question question){
        return QuestionResponse.builder()
               .id(question.getId().toString())
               .content(question.getContent())
               .description(question.getDescription())
               .level(question.getLevel())
               .build();
    }
}
