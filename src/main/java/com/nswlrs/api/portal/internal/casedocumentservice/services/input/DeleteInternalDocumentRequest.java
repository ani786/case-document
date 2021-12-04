package com.nswlrs.api.portal.internal.casedocumentservice.services.input;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;
import lombok.extern.slf4j.Slf4j;

/**
 * The type Delete internal document request.
 */
@Slf4j
@Jacksonized
@Builder
@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@ToString
public class DeleteInternalDocumentRequest {

    @Size(max = 20)
    @NotBlank(message = "User Code cannot be blank")
    private final String userCode;

    @Valid
    @NotNull
    private final DeleteInternalDocument deleteInternalDocument;

    @Builder.Default
    private final String actionedByDate =
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MMM/yyyy HH:mm:ss"));

}
