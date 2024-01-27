package com.tnduck.newinstitute.util;

import com.tnduck.newinstitute.entity.specification.criteria.PaginationCriteria;
import com.tnduck.newinstitute.exception.BadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
/**
 * @author ductn
 * @project The new institute
 * @created 26/01/2024 - 10:55 PM
 */
@Slf4j
public class PageRequestBuilder extends AbstractBaseSortDirection {
    public static PageRequest build(final PaginationCriteria paginationCriteria) {
        if (paginationCriteria.getPage() == null || paginationCriteria.getPage() < 1) {
            log.warn("Page number is not valid");
            throw new BadRequestException("Page must be greater than 0!");
        }

        paginationCriteria.setPage(paginationCriteria.getPage() - 1);

        if (paginationCriteria.getSize() == null || paginationCriteria.getSize() < 1) {
            log.warn("Page size is not valid");
            throw new BadRequestException("Size must be greater than 0!");
        }

        PageRequest pageRequest = PageRequest.of(paginationCriteria.getPage(), paginationCriteria.getSize());

        if (paginationCriteria.getSortBy() != null && paginationCriteria.getSort() != null) {
            Sort.Direction direction = getDirection(paginationCriteria.getSort());

            List<String> columnsList = new ArrayList<>(Arrays.asList(paginationCriteria.getColumns()));
            if (columnsList.contains(paginationCriteria.getSortBy())) {
                return pageRequest.withSort(Sort.by(direction, paginationCriteria.getSortBy()));
            }
        }

        return pageRequest;
    }
}
