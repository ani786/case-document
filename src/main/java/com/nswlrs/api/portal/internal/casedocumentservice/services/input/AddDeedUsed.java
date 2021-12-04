package com.nswlrs.api.portal.internal.casedocumentservice.services.input;

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
 * The type Add deed used.
 */
@Slf4j
@Jacksonized
@Builder
@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@ToString

public class AddDeedUsed {

    @Range(min = 1, max = 99999999)
    private final long packetId;

    @Pattern(regexp = "^(^[Bb][Kk] ?([0-9]{1,4}) ?[Nn][Oo] ?([0-9]{1,4})$)", message =
                "Invalid Format: The Deed format must start with 'BK' with 1-4 numbers followed by ‘No' with 1-4 numbers e.g. BK1234 NO1234")

    private String encumNum;

    /**
     * Gets encum num.
     *
     * @return the encum num
     */
    public String getEncumNum() {
        this.encumNum = StringUtils.deleteWhitespace(encumNum.toUpperCase());
        return encumNum;
    }
}
