package com.mydocumentsref.api.portal.internal.casedocumentservice.services.output;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * The type Lodged document.
 */
@Slf4j
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class LodgedDocument {

    private String fmtDlgNum;
    private String dealingType;
    private Integer dealingImageId;
    private String imageType;
    private String imageTypeDesc;
    private String addedDate;
    private Integer version;
    private String path;

    @JsonIgnore
    private String superseded;

    @JsonIgnore
    private Integer regnSeqNum;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LodgedDocument)) {
            return false;
        }
        LodgedDocument that = (LodgedDocument) o;
        return fmtDlgNum.equals(that.fmtDlgNum) && dealingType.equals(that.dealingType) &&
                    dealingImageId.equals(that.dealingImageId) && imageType.equals(that.imageType) &&
                    addedDate.equals(that.addedDate) && version.equals(that.version) && path.equals(that.path) &&
                    superseded.equals(that.superseded) && regnSeqNum.equals(that.regnSeqNum) &&
                    codeValue.equals(that.codeValue);
    }

    @Override
    public int hashCode() {
        return Objects
                    .hash(fmtDlgNum, dealingType, dealingImageId, imageType, addedDate, version, path, superseded,
                                regnSeqNum,
                                codeValue);
    }

    @JsonIgnore
    private String codeValue;

}
