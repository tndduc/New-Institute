package com.tnduck.newinstitute.util;

import org.springframework.data.domain.Sort.Direction;
/**
 * @author ductn
 * @project The new institute
 * @created 26/01/2024 - 10:47 PM
 */
public abstract class AbstractBaseSortDirection {
    /**
     * @param sort String
     * @return Sort.Direction
     */
    protected static Direction getDirection(final String sort) {
        if ("desc".equalsIgnoreCase(sort)) {
            return Direction.DESC;
        }

        return Direction.ASC;
    }
}
