package com.tnduck.newinstitute.dto.response.video;

import com.tnduck.newinstitute.entity.Video;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Getter
@Setter
@SuperBuilder
public class VideoResponse {
    @Schema(
            name = "id",
            description = "id of video",
            type = "String",
            example = "0"
    )
    private UUID id;
    @Schema(
            name = "url",
            description = "url of video",
            type = "String",
            example = "0"
    )
    private String url;
    @Schema(
            name = "idUnit",
            description = "id of unit",
            type = "String",
            example = "0"
    )
    private String idUnit;
    public static VideoResponse convert(Video video) {
        return VideoResponse.builder()
                .id(video.getId())
                .idUnit(video.getUnit().getId().toString())
                .url(video.getUrl())
                .build();
    }
}
