package com.tnduck.newinstitute.dto.request.lesson;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * @author ductn
 * @project The new institute
 * @created 06/03/2024 - 7:58 AM
 */
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class LessonRequest {
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
            type = "int",
            requiredMode = Schema.RequiredMode.REQUIRED,
            example = ""
    )
    private String idCourse;

//    @Schema(
//            name = "ordinal_number",
//            description = "ordinal number",
//            type = "int",
//            example = "1"
//    )
//    private int ordinalNumber;
}
