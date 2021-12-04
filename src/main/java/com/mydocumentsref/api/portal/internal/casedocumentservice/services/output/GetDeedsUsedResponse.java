package com.mydocumentsref.api.portal.internal.casedocumentservice.services.output;

import java.util.List;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.mydocumentsref.api.portal.internal.casedocumentservice.model.DeedUsed;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;
import lombok.extern.slf4j.Slf4j;

/**
 * The type Get deeds used response.
 */
@Slf4j
@Jacksonized
@Builder
@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class GetDeedsUsedResponse {
    private final List<DeedUsed> deedsUsed;
}
