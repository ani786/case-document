package com.nswlrs.api.portal.internal.casedocumentservice.services.output;

import java.util.List;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.nswlrs.api.common.commonservice.util.Constants;
import com.nswlrs.api.portal.internal.casedocumentservice.model.PacketImage;
import lombok.Builder;
import lombok.Getter;
import lombok.With;
import lombok.extern.jackson.Jacksonized;
import lombok.extern.slf4j.Slf4j;

/**
 * The type Add internal document response.
 */
@Slf4j
@Jacksonized
@Builder
@Getter
@With
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class AddInternalDocumentResponse {
    private final List<PacketImage> packetImages;

    private final String userCode;
    private final int recordsInserted;
    private final int recordUpdated;
    @Builder.Default
    private final String responseMessage = Constants.FAILURE;
    private final String responseMessageDescription;
    private final String responseError;
    private final String responseErrorCode;
    private final String timestamp;

}
