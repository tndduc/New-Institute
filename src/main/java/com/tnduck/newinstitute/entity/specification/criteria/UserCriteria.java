package com.tnduck.newinstitute.entity.specification.criteria;

import com.tnduck.newinstitute.util.Constants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public final class UserCriteria {
    private List<Constants.RoleEnum> roles;

    private Boolean isAvatar;

    private LocalDateTime createdAtStart;

    private LocalDateTime createdAtEnd;

    private Boolean isEmailActivated;

    private Boolean isBlocked;

    private String q;
}
