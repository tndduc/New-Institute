package com.tnduck.newinstitute.dto.response.quizResult;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class BaseQuizResultResponse {
    private String idCourse;
    private String idUser;
    private String score;
    private Boolean isFinal;
    private Boolean isPass;
}
