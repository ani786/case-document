package com.mydocumentsref.api.portal.internal.casedocumentservice.model;

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
public class PacketImage {
    private final String displayName;
    private final long packetImageId;
    private final String path ;

}
