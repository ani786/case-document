package com.nswlrs.api.portal.internal.casedocumentservice.delegate;

import com.nswlrs.api.portal.internal.casedocumentservice.repository.GetDeedsUsedJDBCRepository;
import com.nswlrs.api.portal.internal.casedocumentservice.services.output.GetDeedsUsedResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * The type Get deeds used delegate.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class GetDeedsUsedDelegate {

    private final GetDeedsUsedJDBCRepository jdbcRepository;

    /**
     * Gets deeds used.
     *
     * @param packetId the packet id
     * @return the deeds used
     */
    public GetDeedsUsedResponse getDeedsUsed(final long packetId) {
        log.info("delegate Get  Deeds Used for packetId {} ", packetId);
        return GetDeedsUsedResponse.builder()
                    .deedsUsed(jdbcRepository.getDeedsUsed(packetId))
                    .build();
    }

}
