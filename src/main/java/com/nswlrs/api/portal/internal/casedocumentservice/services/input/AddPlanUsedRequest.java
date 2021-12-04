package com.nswlrs.api.portal.internal.casedocumentservice.services.input;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

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
 * The type Add plan used request.
 */
@Slf4j
@Jacksonized
@Builder
@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@ToString
public class AddPlanUsedRequest {

    @Size(max = 20)
    @NotBlank(message = "user_code cannot be blank")
    private final String userCode;

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
        this.planNumber = StringUtils.deleteWhitespace(planNumber).toUpperCase();
        return planNumber;
    }

    @Valid
    @NotNull
    private final List<PlanImages> planImages;

    @Builder.Default
    private final String actionedByDate =
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MMM/yyyy HH:mm:ss"));

}
