package com.mydocumentsref.api.portal.internal.casedocumentservice.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.With;
import lombok.extern.jackson.Jacksonized;

/**
 * The type Plan number detail.
 */
@Getter
@Builder
@Jacksonized
@With
@ToString
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class PlanNumberDetail {
    private final String planNumber;
    private final String source;

}
