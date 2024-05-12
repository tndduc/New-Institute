package com.tnduck.newinstitute.dto.request.video;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.web.multipart.MultipartFile;
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class CreateVideoLessonRequest {
    @Schema(
            name = "idUnit",
            description = "id of unit",
            type = "String",
            example = "0"
    )
    private String idUnit;
    @Schema(
            name = "file",
            description = "File of course",
            type = "File"
    )
    private MultipartFile file;
    @Schema(
            name = "title",
            description = "Title of video",
            type = "String",
            example = "0"
    )
    private String title;


}
