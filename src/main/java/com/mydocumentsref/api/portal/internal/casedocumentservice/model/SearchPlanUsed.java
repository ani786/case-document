package com.mydocumentsref.api.portal.internal.casedocumentservice.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.With;
import lombok.extern.jackson.Jacksonized;

/**
 * The type Search plan used.
 */
@Getter
@Builder
@Jacksonized
@With
@ToString
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class SearchPlanUsed {
    private final String planNumber;
    private final String imageCategory;
    private final String imageSubtype;
    private final String subtypeName;
    private final String alreadyInPacket;
    private final String imageAvailable;
    private final String responseError;
    private final String responseErrorCode;
}
