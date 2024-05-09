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
            name = "title",
            description = "Title of video",
            type = "String",
            example = "0"
    )
    private String title;
    @Schema(
            name = "duration",
            description = "duration of video",
            type = "String",
            example = "0"
    )
    private String duration;
    @Schema(
            name = "url",
            description = "url of video",
            type = "String",
            example = "0"
    )
    private String url;
    public static VideoResponse convert(Video videos) {
        return VideoResponse.builder()
                .id(videos.getId())
                .title(videos.getTitle())
                .duration(videos.getDuration())
                .url(videos.getUrl())
                .build();
    }
}
