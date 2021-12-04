package com.nswlrs.api.portal.internal.casedocumentservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.With;
import lombok.extern.jackson.Jacksonized;

/**
 * The type Image subtype.
 */
@Getter
@Builder
@Jacksonized
@With
@ToString
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ImageSubtype {

    private final String imageCategory;
    @JsonProperty("image_subtype")
    private final String subtypeName;
    private final String imageSubtypeDesc;
    private final String packetImageId;
    private final String path;
    private final String responseError;
    private final String responseErrorCode;

}
