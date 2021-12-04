package com.nswlrs.api.portal.internal.casedocumentservice.model;

import java.util.List;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.nswlrs.api.common.commonservice.util.Constants;
import lombok.Builder;
import lombok.Getter;
import lombok.With;
import lombok.extern.jackson.Jacksonized;

/**
 * The type Add plans used packet image.
 */
@Getter
@Builder
@Jacksonized
@With
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class AddPlansUsedPacketImage {
    private final List<PacketImage> packetImages;
    private final int recordsInserted;
    private final int recordUpdated;
    @Builder.Default
    private final String responseMessage = Constants.FAILURE;
    private final String responseMessageDescription;
    private final String responseError;
    private final String responseErrorCode;

}
