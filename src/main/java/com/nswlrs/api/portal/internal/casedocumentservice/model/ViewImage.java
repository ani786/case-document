package com.nswlrs.api.portal.internal.casedocumentservice.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;
import lombok.With;
import lombok.extern.jackson.Jacksonized;

/**
 * The type Packet image.
 */
@Getter
@Builder
@Jacksonized
@With
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ViewImage {
    private final long dealingImageId;
    private final String path ;
    private final String responseError;
    private final String responseErrorCode;

}
