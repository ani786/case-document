package com.mydocumentsref.api.portal.internal.casedocumentservice.repository;

import com.mydocumentsref.api.portal.internal.casedocumentservice.repository.statement.DeleteInternalDocumentStatement;
import com.mydocumentsref.api.portal.internal.casedocumentservice.services.input.DeleteInternalDocumentRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * The type Delete internal document jdbc repository.
 */
@Repository
@RequiredArgsConstructor
@Slf4j
public class DeleteInternalDocumentJDBCRepository {

    private final JdbcTemplate jdbcTemplate;

    /**
     * Gets packet added by.
     *
     * @param request the request
     * @return the packet added by
     */
    public String getPacketAddedBy(DeleteInternalDocumentRequest request) {
        log.info("repository DeleteInternalDocument getPacketAddedBy  PacketId() {}", request.getDeleteInternalDocument().getPacketImageId());
        try {
            return jdbcTemplate.queryForObject(DeleteInternalDocumentStatement.GET_PACKET_ADDED_BY, String.class, request.getDeleteInternalDocument().getPacketImageId());
        } catch (EmptyResultDataAccessException exception) {
            return null;
        }
    }

    /**
     * Update document int.
     *
     * @param request the request
     * @return the int
     */
    public int updateDocument(DeleteInternalDocumentRequest request) {
        log.info("repository DeleteInternalDocument updateDocument PacketId() {}", request.getDeleteInternalDocument().getPacketImageId());
        return jdbcTemplate.update(DeleteInternalDocumentStatement.UPDATE_DOCUMENT, request.getDeleteInternalDocument().getPacketImageId());
    }
}
