package com.nswlrs.api.portal.internal.casedocumentservice.delegate;

import com.nswlrs.api.portal.internal.casedocumentservice.repository.GetInternalDocumentsJDBCRepository;
import com.nswlrs.api.portal.internal.casedocumentservice.services.output.GetInternalDocumentsResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * The type Get internal documents delegate.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class GetInternalDocumentsDelegate {

    private final GetInternalDocumentsJDBCRepository jdbcRepository;

    /**
     * Gets internal documents.
     *
     * @param packetId the packet id
     * @return the internal documents
     */
    public GetInternalDocumentsResponse getInternalDocuments(final long packetId) {
        log.info("delegate getInternalDocuments for packetId {}", packetId);
        GetInternalDocumentsResponse getInternalDocumentsResponse =
                    GetInternalDocumentsResponse.builder().internalDocuments(jdbcRepository.getInternalDocuments(packetId)).build();

        return getInternalDocumentsResponse;

    }

}
