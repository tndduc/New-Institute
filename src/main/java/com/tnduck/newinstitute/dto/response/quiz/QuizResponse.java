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
            name = "description",
            description = "description of quiz",
            type = "String"
    )
    private String description;
    @Schema(
            name = "idLesson",
            description = "id Lesson of course",
            type = "String",
            requiredMode = Schema.RequiredMode.REQUIRED,
            example = ""
    )
    private String idLesson;
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
            questionResponseList.add(QuestionResponse.convert(question,choices));
        }
        return QuizResponse.builder()
                .id(quiz.getId().toString())
                .title(quiz.getTitle())
                .description(quiz.getDescription())
                .idLesson(quiz.getLesson().getId().toString())
                .isFinalExam(quiz.isFinalExam())
                .questionResponses(questionResponseList)
                .build();
    }
    public static QuizResponse convert(Quiz quiz) {
        return QuizResponse.builder()
                .id(quiz.getId().toString())
                .title(quiz.getTitle())
                .description(quiz.getDescription())
                .idLesson(quiz.getLesson().getId().toString())
                .isFinalExam(quiz.isFinalExam())
                .build();
    }

}
