package com.tnduck.newinstitute.dto.request.user;

import com.tnduck.newinstitute.dto.annotation.FieldMatch;
import com.tnduck.newinstitute.dto.annotation.MinListSize;
import com.tnduck.newinstitute.dto.annotation.Password;
import com.tnduck.newinstitute.dto.annotation.ValueOfEnum;
import com.tnduck.newinstitute.util.Constants;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;
/**
 * @author ductn
 * @project The new institute
 * @created 27/01/2024 - 9:46 PM
 */
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@FieldMatch(first = "password", second = "passwordConfirm", message = "{password_mismatch}")
public class UpdateUserRequest extends AbstractBaseUpdateUserRequest {
    @Password(message = "{invalid_password}")
    @Schema(
        name = "password",
        description = "Password of the user",
        type = "String",
        requiredMode = Schema.RequiredMode.NOT_REQUIRED,
        example = "P@sswd123."
    )
    private String password;

    @Schema(
        name = "passwordConfirm",
        description = "Password for confirmation",
        type = "String",
        requiredMode = Schema.RequiredMode.NOT_REQUIRED,
        example = "P@sswd123."
    )
    private String passwordConfirm;

    @MinListSize(min = 1, message = "{min_list_size}")
    @Schema(
        name = "roles",
        description = "Roles of the user",
        type = "List<String>",
        requiredMode = Schema.RequiredMode.NOT_REQUIRED,
        allowableValues = {"ADMIN", "USER"},
        example = "[\"USER\"]"
    )
    private List<@ValueOfEnum(enumClass = Constants.RoleEnum.class) String> roles;

    @Schema(
        name = "isEmailVerified",
        description = "Is the user's email verified",
        type = "Boolean",
        requiredMode = Schema.RequiredMode.NOT_REQUIRED,
        example = "true"
    )
    @Builder.Default
    private Boolean isEmailVerified = false;

    @Schema(
        name = "isBlocked",
        description = "Is the user blocked",
        type = "Boolean",
        requiredMode = Schema.RequiredMode.NOT_REQUIRED,
        example = "false"
    )
    @Builder.Default
    private Boolean isBlocked = false;
}
