package com.nswlrs.api.portal.internal.casedocumentservice.services.output;

import java.util.List;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.nswlrs.api.portal.internal.casedocumentservice.model.AddPlansUsed;
import lombok.Builder;
import lombok.Getter;
import lombok.With;
import lombok.extern.jackson.Jacksonized;
import lombok.extern.slf4j.Slf4j;

/**
 * The type Add plan used response.
 */
@Slf4j
@Jacksonized
@Builder
@Getter
@With
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class AddPlanUsedResponse {
    private final String userCode;
    private final String timestamp;
    private final List<AddPlansUsed> plans;

}
