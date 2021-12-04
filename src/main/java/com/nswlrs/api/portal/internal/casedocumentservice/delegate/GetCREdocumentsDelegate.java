package com.nswlrs.api.portal.internal.casedocumentservice.delegate;

import com.nswlrs.api.portal.internal.casedocumentservice.repository.GetCREdocumentsJDBCRepository;
import com.nswlrs.api.portal.internal.casedocumentservice.services.output.GetCREdocumentsResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * The type Get cr edocuments delegate.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class GetCREdocumentsDelegate {

    private final GetCREdocumentsJDBCRepository jdbcRepository;

    /**
     * Gets cr edocuments.
     *
     * @param packetId the packet id
     * @return the cr edocuments
     */
    public GetCREdocumentsResponse getCREdocuments(final long packetId) {
        log.info("delegate Get CRE Used for packetId {} ", packetId);
        return GetCREdocumentsResponse.builder()
                    .creDocuments(jdbcRepository.getCREdocuments(packetId))
                    .build();
    }

}
