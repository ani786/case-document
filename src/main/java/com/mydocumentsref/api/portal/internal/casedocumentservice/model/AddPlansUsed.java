package com.mydocumentsref.api.portal.internal.casedocumentservice.model;

import java.util.List;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;
import lombok.With;
import lombok.extern.jackson.Jacksonized;

/**
 * The type Add plans used.
 */
@Getter
@Builder
@Jacksonized
@With
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class AddPlansUsed {
    private final List<AddPlansUsedPacketImage> plan;

}
