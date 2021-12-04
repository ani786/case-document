package com.mydocumentsref.api.portal.internal.casedocumentservice.services.input;

import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Range;

/**
 * The type Search plan used.
 */
@Slf4j
@Jacksonized
@Builder
@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@ToString
public class SearchPlanUsed {

    @Range(min = 1, max = 99999999)
    private final long packetId;

    @Pattern(regexp = "^(DP|SP|dp|sp)(\\d{1,7})$", message =
                "Invalid Format: The Plan Number format must start with either DP or SP followed by 1-7 numbers e.g. DP1 or SP123456")
    private String planNumber;

    /**
     * Gets plan number.
     *
     * @return the plan number
     */
    public String getPlanNumber() {
        this.planNumber = StringUtils.deleteWhitespace(planNumber.toUpperCase());
        return planNumber;
    }
}
