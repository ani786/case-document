package com.mydocumentsref.api.portal.internal.casedocumentservice.repository;

import com.mydocumentsref.api.portal.internal.casedocumentservice.repository.statement.AddInternalDocumentStatement;
import com.mydocumentsref.api.portal.internal.casedocumentservice.services.input.AddInternalDocumentRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

/**
 * The type Add internal document jdbc repository.
 */
@Repository
@RequiredArgsConstructor
@Slf4j
public class AddInternalDocumentJDBCRepository {

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    /**
     * Insert record into packet image tbl int.
     *
     * @param request the request
     * @return the int
     */
    public int insertRecordIntoPacketImageTbl(final AddInternalDocumentRequest request) {
        log.info("repository AddInternalDocument insertRecordIntoPacketImageTbl for request {} ", request);
        SqlParameterSource sqlParameterSource =
                    new MapSqlParameterSource().addValue("packetId", request.getAddInternalDocument().getPacketId())
                                .addValue("imageCategory", request.getAddInternalDocument().getImageCategory())
                                .addValue("pathValue", request.getAddInternalDocument().getPath()).addValue("note", request.getAddInternalDocument().getNote())
                                .addValue("userCode", request.getUserCode()).addValue("fileName", request.getAddInternalDocument().getFileName());
        return namedParameterJdbcTemplate
                    .update(AddInternalDocumentStatement.INSERT_RECORD_INTO_PACKET_IMAGE_TBL, sqlParameterSource);

    }

    /**
     * Update packet modified date int.
     *
     * @param request the request
     * @return the int
     */
    public int updatePacketModifiedDate(final AddInternalDocumentRequest request) {
        log.info("repository AddInternalDocument updatePacketModifiedDate for dealingId {}  ", request.getUserCode());
        return jdbcTemplate
                    .update(AddInternalDocumentStatement.UPDATE_PACKET_MODIFIED_DATE, request.getAddInternalDocument().getPacketId());
    }

    /**
     * Update packet count int.
     *
     * @param request the request
     * @return the int
     */
    public int updatePacketCount(final AddInternalDocumentRequest request) {
        log.info("repository AddInternalDocument updatePacketCount for dealingId {} , String userCode {}", request.getUserCode());
        return jdbcTemplate.update(AddInternalDocumentStatement.UPDATE_PACKET_COUNT, request.getUserCode());

    }

}
