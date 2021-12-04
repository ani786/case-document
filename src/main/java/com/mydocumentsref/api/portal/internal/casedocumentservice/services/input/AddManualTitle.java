package com.mydocumentsref.api.portal.internal.casedocumentservice.services.input;

import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.Range;

/**
 * The type Add manual title.
 */
@Slf4j
@Jacksonized
@Builder
@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@ToString
public class AddManualTitle {

    @Range(min = 1, max = 99999999)
    private final long packetId;

    @Pattern(regexp = "^([0-9]{1,5}[-][0-9]{1,3})$", message = "Invalid Format: The Manual Title format must be 1-5 numbers with a hyphen, followed by 1-3 numbers e.g. 12345-123")
    private final String titleRef;

}
