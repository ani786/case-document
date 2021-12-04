package com.mydocumentsref.api.portal.internal.casedocumentservice.services.input;

import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;
import lombok.extern.slf4j.Slf4j;

/**
 * The type View image.
 */
@Slf4j
@Jacksonized
@Builder
@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@ToString
public class ViewImage {

    private final long packetId;
    private final String dlgStatus;

    @Pattern(regexp = "^([A-Za-z]{2}[0-9]{1,6}|^[A-Za-z][0-9]{1,6}|^[0-9]{6,7}$)", message =
                "Invalid Format: The Dealing Number format must be no more than 8 characters and consist of either 2 letters and up to 6 numbers, 1 letter and up to 6 numbers or" +
                            " 6-7 numbers e.g. AP123456, A123456, 1234567")

    private String fmtDlgNum;

    /**
     * Gets fmt dlg num.
     *
     * @return the fmt dlg num
     */
    public String getFmtDlgNum() {
        this.fmtDlgNum = fmtDlgNum.toUpperCase();
        return fmtDlgNum;
    }

}
