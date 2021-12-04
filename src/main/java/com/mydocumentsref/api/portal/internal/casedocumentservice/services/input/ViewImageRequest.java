package com.mydocumentsref.api.portal.internal.casedocumentservice.services.input;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;
import lombok.extern.slf4j.Slf4j;

/**
 * The type View image request.
 */
@Slf4j
@Jacksonized
@Builder
@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@ToString
public class ViewImageRequest {

    private final String userCode;

    @Valid
    @NotNull
    private final ViewImage viewImage;

    @Builder.Default
    private final String actionedByDate =
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MMM/yyyy HH:mm:ss"));

}
