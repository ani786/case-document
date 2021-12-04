package com.mydocumentsref.api.portal.internal.casedocumentservice.services.output;

import java.util.List;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.mydocumentsref.api.portal.internal.casedocumentservice.model.SearchPlanUsed;
import lombok.Builder;
import lombok.Getter;
import lombok.With;
import lombok.extern.jackson.Jacksonized;
import lombok.extern.slf4j.Slf4j;

/**
 * The type Get search plans used response.
 */
@Slf4j
@Jacksonized
@Builder
@Getter
@With
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class GetSearchPlansUsedResponse {
    private final List<SearchPlanUsed> searchPlansUsed;
}
