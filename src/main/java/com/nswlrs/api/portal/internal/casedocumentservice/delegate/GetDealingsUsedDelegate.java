package com.nswlrs.api.portal.internal.casedocumentservice.delegate;

import com.nswlrs.api.portal.internal.casedocumentservice.repository.GetDealingsUsedJDBCRepository;
import com.nswlrs.api.portal.internal.casedocumentservice.services.output.GetDealingsUsedResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * The type Get dealings used delegate.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class GetDealingsUsedDelegate {

    private final GetDealingsUsedJDBCRepository jdbcRepository;

    /**
     * Gets dealings used.
     *
     * @param packetId the packet id
     * @return the dealings used
     */
    public GetDealingsUsedResponse getDealingsUsed(final long packetId) {
        log.info("delegate Get  Dealings Used for packetId {} ", packetId);
        return GetDealingsUsedResponse.builder()
                    .dealingsUsed(jdbcRepository.getDealingsUsed(packetId))
                    .build();
    }

}
