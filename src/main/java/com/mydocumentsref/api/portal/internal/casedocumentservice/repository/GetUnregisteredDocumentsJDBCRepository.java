package com.mydocumentsref.api.portal.internal.casedocumentservice.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.mydocumentsref.api.portal.internal.casedocumentservice.model.UnregisteredDocument;
import com.mydocumentsref.api.portal.internal.casedocumentservice.repository.statement.GetUnregisteredDocumentsStatement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

/**
 * The type Get unregistered documents jdbc repository.
 */
@Repository
@RequiredArgsConstructor
@Slf4j
public class GetUnregisteredDocumentsJDBCRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    /**
     * Gets unregistered documents.
     *
     * @param packetId the packet id
     * @return the unregistered documents
     */
    public List<UnregisteredDocument> getUnregisteredDocuments(final long packetId) {
        log.info("repositoryGet Document titles unregistered for packetId {} ", packetId);
        final SqlParameterSource parameterSource = new MapSqlParameterSource().addValue("packetId", packetId);
        return namedParameterJdbcTemplate
                    .query(GetUnregisteredDocumentsStatement.GET_UNREGISTERED_DOCUMENTS, parameterSource,
                                new RowMapper<UnregisteredDocument>() {
                                    public UnregisteredDocument mapRow(ResultSet rs, int rowNum)
                                                throws SQLException {
                                        UnregisteredDocument unregisteredDocument = UnregisteredDocument.builder()
                                                    .packetId(rs.getLong("packet_id"))
                                                    .dlgId(rs.getLong("dlg_id"))
                                                    .fmtDlgNum(rs.getString("fmt_dlg_num"))
                                                    .documentName(rs.getString("document_name"))
                                                    .build();
                                        return unregisteredDocument;
                                    }

                                });
    }

}
