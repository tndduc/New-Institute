package com.tnduck.newinstitute.entity.specification.criteria;

import com.tnduck.newinstitute.util.Constants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author ductn
 * @project The new institute
 * @created 24/02/2024 - 10:26 PM
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public final class CourseCriteria {

    private LocalDateTime createdAtStart;

    private LocalDateTime createdAtEnd;

    private BigDecimal priceMax;

    private BigDecimal priceMin;

//    private String status;

    private String keyword;
}
