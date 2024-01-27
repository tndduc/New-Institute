package com.tnduck.newinstitute.entity.specification.criteria;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * @author ductn
 * @project The new institute
 * @created 27/01/2024 - 8:55 PM
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public final class PaginationCriteria {
    private Integer page;

    private Integer size;

    private String sortBy;

    private String sort;

    private String[] columns;
}
