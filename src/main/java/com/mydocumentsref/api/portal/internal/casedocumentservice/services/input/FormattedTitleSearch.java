package com.mydocumentsref.api.portal.internal.casedocumentservice.services.input;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.Range;

/**
 * The type Formatted title search.
 */
@Slf4j
@Jacksonized
@Builder
@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@ToString
public class FormattedTitleSearch {

    @Range(min = 1, max = 99999999)
    private final long packetId;
    private final String titleRef;
    private final String searchType;

}
