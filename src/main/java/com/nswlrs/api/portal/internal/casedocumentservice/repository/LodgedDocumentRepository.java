package com.nswlrs.api.portal.internal.casedocumentservice.repository;

import java.util.List;

import com.nswlrs.api.portal.internal.casedocumentservice.repository.statement.LodgedDocumentListStatement;
import com.nswlrs.api.portal.internal.casedocumentservice.result.LodgedDocumentExtractor;
import com.nswlrs.api.portal.internal.casedocumentservice.services.output.LodgedDocument;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

/**
 * The type Lodged document repository.
 */
@Slf4j
@Repository
@RequiredArgsConstructor
public class LodgedDocumentRepository {

    private final NamedParameterJdbcTemplate lodgedDocumentDao;

    /**
     * Gets lodged documents.
     *
     * @param packetId the packet id
     * @return the lodged documents
     */
    public List<LodgedDocument> getLodgedDocuments(final int packetId) {

        SqlParameterSource sqlParameterSource = new MapSqlParameterSource().addValue("packetId", packetId);
        return lodgedDocumentDao.query(LodgedDocumentListStatement.GET_LODGED_DOCUMENT_LIST, sqlParameterSource,
                    new LodgedDocumentExtractor());
    }
}
