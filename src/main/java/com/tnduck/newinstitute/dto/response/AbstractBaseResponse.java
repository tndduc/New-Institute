package com.tnduck.newinstitute.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.experimental.SuperBuilder;
/**
 * @author ductn
 * @project The new institute
 * @created 27/01/2024 - 8:56 PM
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@SuperBuilder
public abstract class AbstractBaseResponse {
    protected AbstractBaseResponse() {
    }
}
