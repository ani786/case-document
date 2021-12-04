package com.mydocumentsref.api.portal.internal.casedocumentservice.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

/**
 * The type Internal documents.
 */
@Getter
@Builder
@Jacksonized
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class InternalDocuments {
    private final long packetImageId;
    private final String packetImageGroup;
    private final String imageType;
    private final String imageSubtype;
    private final String imageCategory;
    private final String imageName;
    private final String path;
    private final String note;
    private final String addedDate;
    private final String addedByUser;
    private final String modifiedDate;

}
