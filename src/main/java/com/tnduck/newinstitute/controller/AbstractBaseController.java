package com.tnduck.newinstitute.controller;

import com.tnduck.newinstitute.exception.BadRequestException;
import com.tnduck.newinstitute.service.MessageSourceService;

import java.util.Arrays;
/**
 * @author ductn
 * @project The new institute
 * @created 27/01/2024 - 9:20 PM
 */
public abstract class AbstractBaseController {
    /**
     * Sort column check.
     *
     * @param messageSourceService MessageSourceService
     * @param sortColumns          String[]
     * @param sortBy               String
     */
    protected void sortColumnCheck(final MessageSourceService messageSourceService,
                                   final String[] sortColumns,
                                   final String sortBy) {
        if (sortBy != null && !Arrays.asList(sortColumns).contains(sortBy)) {
            throw new BadRequestException(messageSourceService.get("invalid_sort_column"));
        }
    }
}
