package com.nswlrs.api.portal.internal.casedocumentservice.delegate;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.nswlrs.api.portal.internal.casedocumentservice.repository.LodgedDocumentRepository;
import com.nswlrs.api.portal.internal.casedocumentservice.services.output.LodgedDocument;
import com.nswlrs.api.portal.internal.casedocumentservice.services.output.LodgedDocumentResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * The type Get lodged document delegate.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class GetLodgedDocumentDelegate {

    private final Predicate<LodgedDocument> isSupersededYes = l -> "Y".equalsIgnoreCase(l.getSuperseded());

    private final LodgedDocumentRepository lodgedDocumentRepository;

    /**
     * Gets lodged documents by packet id.
     *
     * @param packetId the packet id
     * @return the lodged documents by packet id
     */
    public LodgedDocumentResponse getLodgedDocumentsByPacketId(final Integer packetId) {
        log.info("start to get lodged Document List by packetId : {} ", packetId);

        LodgedDocumentResponse response = new LodgedDocumentResponse();
        List<LodgedDocument> lodgedDocumentList = lodgedDocumentRepository.getLodgedDocuments(packetId);
        if (!lodgedDocumentList.isEmpty()) {
            List<LodgedDocument> hasSuperseded =
                        lodgedDocumentList.stream().filter(isSupersededYes).collect(Collectors.toList());
            lodgedDocumentList.removeAll(hasSuperseded);
            response.setLodgedDocuments(lodgedDocumentList);
            response.setPreviousVesions(hasSuperseded);
            return response;
        }

        response.setLodgedDocuments(lodgedDocumentList);
        response.setPreviousVesions(lodgedDocumentList);
        return response;
    }
}
