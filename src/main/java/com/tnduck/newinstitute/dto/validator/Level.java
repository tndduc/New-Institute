package com.tnduck.newinstitute.dto.validator;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @author ductn
 * @project The new institute
 * @created 06/03/2024 - 8:12 AM
 */
@Getter
public enum Level {
    BEGIN("begin"),
    ALLLEVEL("expert"),
    MIDDLE("middle"),
    EXPERT("expert");


    private String value;

    Level(String value) {
        this.value = value;
    }

    @JsonCreator
    public static Level fromValue(String value) {
        for (Level level : values()) {
            if (level.value.equalsIgnoreCase(value)) {
                return level;
            }
        }
        throw new IllegalArgumentException("Invalid level: " + value);
    }

    @JsonValue
    public String getValue() {
        return value;
    }
}