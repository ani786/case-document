package com.mydocumentsref.api.portal.internal.casedocumentservice.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

/**
 * The type Unregistered document.
 */
@Getter
@Builder
@Jacksonized
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class UnregisteredDocument {
    private final long packetId;
    private final long dlgId;
    private final String fmtDlgNum;
    private final String documentName;
}
