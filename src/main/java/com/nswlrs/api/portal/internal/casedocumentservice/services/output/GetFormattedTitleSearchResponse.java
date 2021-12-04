package com.nswlrs.api.portal.internal.casedocumentservice.services.output;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.nswlrs.api.common.commonservice.capi.model.ApiResultPages;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;
import lombok.extern.slf4j.Slf4j;

/**
 * The type Get formatted title search response.
 */
@Slf4j
@Jacksonized
@Builder
@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class GetFormattedTitleSearchResponse {
    private final ApiResultPages apiResultPages;
}
