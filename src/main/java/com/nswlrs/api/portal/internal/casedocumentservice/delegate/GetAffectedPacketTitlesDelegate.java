package com.nswlrs.api.portal.internal.casedocumentservice.delegate;

import java.util.List;

import com.nswlrs.api.portal.internal.casedocumentservice.model.AffectedPacketTitle;
import com.nswlrs.api.portal.internal.casedocumentservice.repository.GetAffectedPacketTitlesJDBCRepository;
import com.nswlrs.api.portal.internal.casedocumentservice.services.output.GetAffectedPacketTitlesResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.ListUtils;
import org.springframework.stereotype.Service;

/**
 * The type Get affected packet titles delegate.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class GetAffectedPacketTitlesDelegate {

    private final GetAffectedPacketTitlesJDBCRepository jdbcRepository;

    /**
     * Gets packet get affected packet titles.
     *
     * @param packetId the packet id
     * @return the packet get affected packet titles
     */
    public GetAffectedPacketTitlesResponse getPacketGetAffectedPacketTitles(final long packetId) {
        log.info("delegate Get  Document titles affected  for packetId {} ", packetId);
        GetAffectedPacketTitlesResponse previousVersionLodgedDocFromFolioParcel =
                    GetAffectedPacketTitlesResponse.builder()
                                .affectedPacketTitles(
                                            jdbcRepository.getPreviousVersionLodgedDocFromFolioParcel(packetId))
                                .build();

        GetAffectedPacketTitlesResponse recordsetFromDEALINGOSREF = GetAffectedPacketTitlesResponse.builder()
                    .affectedPacketTitles(jdbcRepository.getRecordsetFromDEALINGOSREF(packetId)).build();

        List<AffectedPacketTitle> unionList =
                    ListUtils.union(previousVersionLodgedDocFromFolioParcel.getAffectedPacketTitles(),
                                recordsetFromDEALINGOSREF.getAffectedPacketTitles());

        return GetAffectedPacketTitlesResponse.builder().affectedPacketTitles(unionList).build();

    }

}
