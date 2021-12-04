package com.nswlrs.api.portal.internal.casedocumentservice.delegate;

import com.nswlrs.api.portal.internal.casedocumentservice.repository.AddInternalDocumentJDBCRepository;
import com.nswlrs.api.portal.internal.casedocumentservice.services.input.AddInternalDocumentRequest;
import com.nswlrs.api.portal.internal.casedocumentservice.services.output.AddInternalDocumentResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * The type Add internal document delegate.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AddInternalDocumentDelegate {

    private final AddInternalDocumentJDBCRepository addInternalDocumentJDBCRepository;

    /**
     * Add internal document add internal document response.
     *
     * @param request the request
     * @return the add internal document response
     */
    @Transactional
    public AddInternalDocumentResponse addInternalDocument(final AddInternalDocumentRequest request) {
        log.info("delegate AddInternalDocumentRequest {} ", request);
        int insertInternalDocument = addInternalDocumentJDBCRepository.insertRecordIntoPacketImageTbl(request);

        int updateLastModifiedDate = addInternalDocumentJDBCRepository.updatePacketModifiedDate(request);
        /* int updatePacketCount = addInternalDocumentJDBCRepository.updatePacketCount(request);*/

        log.info("# of Rows Affected for insertIntoDealingMinutes {}, updateLastModifiedDate {},updatePacketCount {} ",
                    insertInternalDocument, updateLastModifiedDate);

        return
                    AddInternalDocumentResponse.builder()
                                .userCode(request.getUserCode())
                                .recordUpdated(updateLastModifiedDate)
                                .recordsInserted(insertInternalDocument)
                                .responseMessage(com.nswlrs.api.common.commonservice.util.Constants.SUCCESS)
                                .timestamp(request.getActionedByDate())
                                .build();

    }

}
