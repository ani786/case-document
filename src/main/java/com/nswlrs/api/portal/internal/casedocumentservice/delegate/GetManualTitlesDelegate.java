package com.nswlrs.api.portal.internal.casedocumentservice.delegate;

import com.nswlrs.api.portal.internal.casedocumentservice.repository.GetManualTitlesJDBCRepository;
import com.nswlrs.api.portal.internal.casedocumentservice.services.output.GetManualTitlesResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * The type Get manual titles delegate.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class GetManualTitlesDelegate {

    private final GetManualTitlesJDBCRepository jdbcRepository;

    /**
     * Gets manual titles.
     *
     * @param packetId the packet id
     * @return the manual titles
     */
    public GetManualTitlesResponse getManualTitles(final long packetId) {
        log.info("delegate Get  Manual titles  for packetId {} ", packetId);
        return GetManualTitlesResponse.builder().manualTitles(jdbcRepository.getManualTitles(packetId)).build();
    }

}
