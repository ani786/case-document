package com.mydocumentsref.api.portal.internal.casedocumentservice.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

/**
 * The type Cr edocument.
 */
@Getter
@Builder
@Jacksonized
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CREdocument {
    private final String titleRef;
    private final long folRecId;
    private final long packetImageId;
    private final String path;
}
