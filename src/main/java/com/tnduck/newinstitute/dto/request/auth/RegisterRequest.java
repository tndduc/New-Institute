package com.tnduck.newinstitute.dto.request.auth;

import com.tnduck.newinstitute.dto.request.user.AbstractBaseCreateUserRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
/**
 * @author ductn
 * @project The new institute
 * @created 27/01/2024 - 9:37 PM
 */
@Getter
@Setter
@AllArgsConstructor
@SuperBuilder
public class RegisterRequest extends AbstractBaseCreateUserRequest {
}
