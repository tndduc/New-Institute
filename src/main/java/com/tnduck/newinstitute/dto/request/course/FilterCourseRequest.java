package com.tnduck.newinstitute.dto.request.course;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author ductn
 * @project The new institute
 * @created 29/02/2024 - 10:26 AM
 */
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class FilterCourseRequest {
    @Schema(
            name = "status",
            description = "Status of course",
            type = "String",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    private String status;
    @Schema(
            name = "keyword",
            description = "Keyword of course, it can be name,description,...",
            type = "String",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    private String keyword;
    @Schema(
            name = "description",
            description = "Created date start",
            type = "LocalDateTime",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED,
            example = "2022-10-25T22:54:58"
    )
    private LocalDateTime createdAtStart;
    @Schema(
            name = "description",
            description = "Created date end",
            type = "LocalDateTime",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED,
            example = "2022-10-25T22:54:58"
    )
    private LocalDateTime createdAtEnd;
    @Schema(
            name = "price",
            description = "Max price of course",
            type = "BigDecimal",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED,
            example = "500"
    )
    private BigDecimal priceMax;
    @Schema(
            name = "price",
            description = "Min price of course",
            type = "BigDecimal",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED,
            example = "0"
    )
    private BigDecimal priceMin;
    @Schema(
            name = "page",
            description = "Page number",
            example = "1",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    private Integer page;
    @Schema(
            name = "size",
            description = "Page size",
            example = "20",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    private Integer size;
    @Schema(
            name = "sort",
            description = "Sort direction",
            type = "String",
            allowableValues = {"asc", "desc"}, defaultValue = "asc",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    private String sort;
    @Schema(
            name = "sortBy",
            description = "Sort by column",
            example = "createdAt",
            type = "String",
            allowableValues = {"id", "name",
                                "status",
                                "createdAt", "updatedAt","price"},
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    private String sortBy;

}
