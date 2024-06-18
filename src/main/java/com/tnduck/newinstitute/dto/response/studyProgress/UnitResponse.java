package com.tnduck.newinstitute.dto.response.studyProgress;

import com.tnduck.newinstitute.entity.Unit;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class UnitResponse {
    @Schema(
            name = "id",
            description = "UUID",
            type = "String"
    )
    private String id;
    public static UnitResponse convert(Unit unit){
        return UnitResponse.builder()
                .id(unit.getId().toString())
                .build();
    }
}
