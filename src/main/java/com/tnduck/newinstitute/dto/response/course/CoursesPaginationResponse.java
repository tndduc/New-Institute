package com.tnduck.newinstitute.dto.response.course;

import com.tnduck.newinstitute.dto.response.AbstractBaseResponse;
import com.tnduck.newinstitute.dto.response.PaginationResponse;
import com.tnduck.newinstitute.dto.response.user.UserResponse;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author ductn
 * @project The new institute
 * @created 29/02/2024 - 10:29 PM
 */
public class CoursesPaginationResponse extends PaginationResponse<CourseResponse> {

    public CoursesPaginationResponse(final Page<?> pageModel, final List<CourseResponse> items) {
        super(pageModel, items);
    }
}
