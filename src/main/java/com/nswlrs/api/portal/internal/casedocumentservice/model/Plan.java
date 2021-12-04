package com.nswlrs.api.portal.internal.casedocumentservice.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.With;
import lombok.extern.jackson.Jacksonized;

/**
 * The type Plan.
 */
@Getter
@Builder
@Jacksonized
@With
@ToString
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonPropertyOrder({"planNumber", "imageSubtypes"})
public class Plan {
    @JsonProperty("display_name")
    private final String planNumber;
    private final List<ImageSubtype> imageSubtypes;

}
