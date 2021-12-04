package com.nswlrs.api.portal.internal.casedocumentservice.delegate;

import com.nswlrs.api.portal.internal.casedocumentservice.repository.DeleteInternalDocumentJDBCRepository;
import com.nswlrs.api.portal.internal.casedocumentservice.services.input.DeleteInternalDocumentRequest;
import com.nswlrs.api.portal.internal.casedocumentservice.services.output.DeleteInternalDocumentResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * The type Delete internal document delegate.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class DeleteInternalDocumentDelegate {

    private final DeleteInternalDocumentJDBCRepository deleteInternalDocumentJDBCRepository;

    /**
     * Delete internal document delete internal document response.
     *
     * @param request the request
     * @return the delete internal document response
     */
    @Transactional
    public DeleteInternalDocumentResponse deleteInternalDocument(final DeleteInternalDocumentRequest request) {
        log.info("delegate deleteInternalDocument {} ", request);
        DeleteInternalDocumentResponse deleteInternalDocumentResponse =
                    DeleteInternalDocumentResponse.builder().userCode(request.getUserCode()).responseMessage(com.nswlrs.api.common.commonservice.util.Constants.SUCCESS)
                                .timestamp(request.getActionedByDate()).build();
        String user = deleteInternalDocumentJDBCRepository.getPacketAddedBy(request);
        if (StringUtils.isNoneEmpty(user) && user.equalsIgnoreCase(request.getUserCode())) {
            int updateDocument = deleteInternalDocumentJDBCRepository.updateDocument(request);
            deleteInternalDocumentResponse = deleteInternalDocumentResponse.withRecordUpdated(updateDocument);
        } else {
            deleteInternalDocumentResponse = deleteInternalDocumentResponse.withResponseError("Document can not be deleted. You are not the user who added it to the packet")
                        .withResponseErrorCode("DOC_DATA_ERR_DEL_INT_DOC_!_USR_WHO_ADD");
        }

        log.info("delegate deleteInternalDocumentResponse {} ", deleteInternalDocumentResponse);

        return deleteInternalDocumentResponse;

    }

}
