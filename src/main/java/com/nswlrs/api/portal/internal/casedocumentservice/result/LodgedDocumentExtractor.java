package com.nswlrs.api.portal.internal.casedocumentservice.result;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.nswlrs.api.common.commonservice.util.DateUtils;
import com.nswlrs.api.portal.internal.casedocumentservice.services.output.LodgedDocument;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

/**
 * The type Lodged document extractor.
 */
public class LodgedDocumentExtractor implements ResultSetExtractor<List<LodgedDocument>> {

    public List<LodgedDocument> extractData(ResultSet rs) throws SQLException, DataAccessException {
        List<LodgedDocument> lodgedDocuments = new ArrayList<>();
        while (rs.next()) {
            LodgedDocument lodgedDocument = getLodgedDocumentResult(rs);
            lodgedDocuments.add(lodgedDocument);
        }
        return lodgedDocuments;
    }

    private LodgedDocument getLodgedDocumentResult(final ResultSet rs) throws SQLException {
        LodgedDocument lodgedDocument = new LodgedDocument();
        lodgedDocument.setFmtDlgNum(rs.getString("fmt_dlg_num"));
        lodgedDocument.setDealingType(rs.getString("dealing_type"));
        lodgedDocument.setDealingImageId(rs.getInt("dealing_image_id"));
        lodgedDocument.setImageType(rs.getString("image_type"));
        lodgedDocument.setImageTypeDesc(rs.getString("image_type_desc"));
        lodgedDocument.setAddedDate(DateUtils.convertTimestampToString(rs.getTimestamp("added_date")));
        lodgedDocument.setVersion(rs.getInt("version"));
        lodgedDocument.setSuperseded(rs.getString("superseded"));
        lodgedDocument.setRegnSeqNum(rs.getInt("regn_seq_num"));
        lodgedDocument.setCodeValue(rs.getString("code_value"));
        lodgedDocument.setPath(rs.getString("path"));
        return lodgedDocument;
    }
}
