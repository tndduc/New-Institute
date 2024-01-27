package com.tnduck.newinstitute.dto.response.user;

import com.tnduck.newinstitute.dto.response.PaginationResponse;
import org.springframework.data.domain.Page;

import java.util.List;
/**
 * @author ductn
 * @project The new institute
 * @created 27/01/2024 - 8:46 PM
 */
public class UsersPaginationResponse extends PaginationResponse<UserResponse> {
    public UsersPaginationResponse(final Page<?> pageModel, final List<UserResponse> items) {
        super(pageModel, items);
    }
}
