package com.mydocumentsref.api.portal.internal.casedocumentservice.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.mydocumentsref.api.portal.internal.casedocumentservice.model.InternalDocuments;
import com.mydocumentsref.api.portal.internal.casedocumentservice.repository.statement.GetInternalDocumentsStatement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

/**
 * The type Get internal documents jdbc repository.
 */
@Repository
@RequiredArgsConstructor
@Slf4j
public class GetInternalDocumentsJDBCRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    /**
     * Gets internal documents.
     *
     * @param packetId the packet id
     * @return the internal documents
     */
    public List<InternalDocuments> getInternalDocuments(long packetId) {
        log.info("repository getInternalDocumentCategories  no input params required");
        final SqlParameterSource parameterSource = new MapSqlParameterSource().addValue("packetId", packetId);
        return namedParameterJdbcTemplate
                    .query(GetInternalDocumentsStatement.GET_INTERNAL_DOCUMENTS, parameterSource,
                                new RowMapper<InternalDocuments>() {
                                    public InternalDocuments mapRow(ResultSet rs, int rowNum)
                                                throws SQLException {
                                        InternalDocuments internalDocuments = InternalDocuments.builder()
                                                    .packetImageId(rs.getLong("packet_image_id"))
                                                    .packetImageGroup(rs.getString("packet_image_group"))
                                                    .imageType(rs.getString("image_type"))
                                                    .imageSubtype(rs.getString("image_subtype"))
                                                    .imageCategory(rs.getString("image_category"))
                                                    .imageName(rs.getString("image_name"))
                                                    .path(rs.getString("path"))
                                                    .note(rs.getString("note"))
                                                    .addedDate(rs.getString("added_date"))
                                                    .addedByUser(rs.getString("added_by_user"))
                                                    .modifiedDate(rs.getString("modified_date"))
                                                    .build();
                                        return internalDocuments;
                                    }

                                });
    }

}
