package com.nswlrs.api.portal.internal.casedocumentservice.delegate;

import com.nswlrs.api.portal.internal.casedocumentservice.repository.GetUnregisteredDocumentsJDBCRepository;
import com.nswlrs.api.portal.internal.casedocumentservice.services.output.GetUnregisteredDocumentsResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * The type Get unregistered documents delegate.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class GetUnregisteredDocumentsDelegate {

    private final GetUnregisteredDocumentsJDBCRepository jdbcRepository;

    /**
     * Gets unregistered documents.
     *
     * @param packetId the packet id
     * @return the unregistered documents
     */
    public GetUnregisteredDocumentsResponse getUnregisteredDocuments(final long packetId) {
        log.info("delegate Get  Document titles unregistered for packetId {} ", packetId);
        return GetUnregisteredDocumentsResponse.builder()
                    .unregisteredDocuments(jdbcRepository.getUnregisteredDocuments(packetId))
                    .build();
    }

}
