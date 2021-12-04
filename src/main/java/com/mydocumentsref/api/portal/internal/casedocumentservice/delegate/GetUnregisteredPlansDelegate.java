package com.mydocumentsref.api.portal.internal.casedocumentservice.delegate;

import com.mydocumentsref.api.portal.internal.casedocumentservice.repository.GetUnregisteredPlansJDBCRepository;
import com.mydocumentsref.api.portal.internal.casedocumentservice.services.output.GetUnregisteredPlansResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * The type Get unregistered plans delegate.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class GetUnregisteredPlansDelegate {

    private final GetUnregisteredPlansJDBCRepository jdbcRepository;

    /**
     * Gets unregistered plans.
     *
     * @param packetId the packet id
     * @return the unregistered plans
     */
    public GetUnregisteredPlansResponse getUnregisteredPlans(final long packetId) {
        log.info("delegate  Get  Document titles unregistered plan  for packetId {} ", packetId);
        return GetUnregisteredPlansResponse.builder().unregisteredPlans(jdbcRepository.getUnregisteredPlans(packetId))
                    .build();
    }

}
