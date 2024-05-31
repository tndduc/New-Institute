package com.tnduck.newinstitute.dto.request.lesson;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * @author ductn
 * @project New-Institute
 * @created 31/05/2024
 */
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class LessonUpdateRequest {
    private String title;
    private String content;
}
