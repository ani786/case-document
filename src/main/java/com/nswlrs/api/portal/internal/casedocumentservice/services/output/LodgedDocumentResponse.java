package com.nswlrs.api.portal.internal.casedocumentservice.services.output;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * The type Lodged document response.
 */
@Slf4j
@Data
@Component
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class LodgedDocumentResponse {

    private List<LodgedDocument> lodgedDocuments;

    private List<LodgedDocument> previousVesions;
}
