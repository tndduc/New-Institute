package com.tnduck.newinstitute.dto.response.quiz;

import com.tnduck.newinstitute.dto.response.choice.ChoiceResponse;
import com.tnduck.newinstitute.dto.response.question.QuestionResponse;
import com.tnduck.newinstitute.dto.response.video.VideoResponse;
import com.tnduck.newinstitute.entity.Choice;
import com.tnduck.newinstitute.entity.Question;
import com.tnduck.newinstitute.entity.Quiz;
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
public class QuizResponse {
    @Schema(
            name = "id",
            description = "UUID",
            type = "String"
    )
    private String id;

    @Schema(
            name = "description",
            description = "description of quiz",
            type = "String"
    )
    private String description;
    @Schema(
            name = "time",
            description = "time of quiz - minutes",
            type = "String"
    )
    private int time;
    @Schema(
            name = "isHaveTime",
            description = "isHaveTime?",
            type = "boolean"
    )
    private boolean isHaveTime;
    @Schema(
            name = "idUnit",
            description = "id of unit",
            type = "String",
            example = "0"
    )
    private String idUnit;
    @Schema(
            name = "isFinalExam",
            description = "isFinalExam?",
            type = "boolean"
    )
    private boolean isFinalExam;
    @ArraySchema(
            schema = @Schema(
                    description = "List of questionResponses"
            )
    )
    private List<QuestionResponse> questionResponses;
    public static QuizResponse convert(Quiz quiz, List<Question> questions,List<Choice> choices) {
        List<QuestionResponse> questionResponseList = new ArrayList<>();
        for (Question question : questions) {
            for (Choice choice :choices) {
                if (question.getId().equals(choice.getQuestion().getId())) {
                    questionResponseList.add(QuestionResponse.convert(question,choices));
                }
            }
        }
        return QuizResponse.builder()
                .id(quiz.getId().toString())
                .description(quiz.getDescription())
                .idUnit(quiz.getUnit().getId().toString())
                .isFinalExam(quiz.isFinalExam())
                .isHaveTime(quiz.isHaveTime())
                .time(quiz.getTime())
                .questionResponses(questionResponseList)
                .build();
    }
    public static QuizResponse convert(Quiz quiz) {
        return QuizResponse.builder()
                .id(quiz.getId().toString())
                .description(quiz.getDescription())
                .idUnit(quiz.getUnit().getId().toString())
                .isFinalExam(quiz.isFinalExam())
                .isHaveTime(quiz.isHaveTime())
                .time(quiz.getTime())
                .build();
    }

}
