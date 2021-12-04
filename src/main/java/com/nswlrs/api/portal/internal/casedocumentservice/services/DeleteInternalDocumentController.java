package com.nswlrs.api.portal.internal.casedocumentservice.services;

import javax.validation.Valid;

import com.nswlrs.api.portal.internal.casedocumentservice.delegate.DeleteInternalDocumentDelegate;
import com.nswlrs.api.portal.internal.casedocumentservice.services.input.DeleteInternalDocumentRequest;
import com.nswlrs.api.portal.internal.casedocumentservice.services.output.DeleteInternalDocumentResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The type Delete internal document controller.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
@Validated
@Slf4j
public class DeleteInternalDocumentController {

    private final DeleteInternalDocumentDelegate delegate;

    /**
     * Delete internal document response entity.
     *
     * @param request the request
     * @return the response entity
     */
    @DeleteMapping(path = "/internal-document")
    @Tag(name = "Delete internal document from the packet")
    public ResponseEntity<DeleteInternalDocumentResponse> deleteInternalDocument(
                @Valid @RequestBody final DeleteInternalDocumentRequest request) {
        log.info("controller deleteInternalDocument: {}", request);
        DeleteInternalDocumentResponse response = delegate.deleteInternalDocument(request);
        if (response.getRecordsInserted() > 0 || response.getRecordUpdated() > 0) {
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } else if (StringUtils.isNoneEmpty(response.getResponseErrorCode())) {
            return new ResponseEntity<>(response, HttpStatus.UNPROCESSABLE_ENTITY);
        } else {
            return new ResponseEntity<>(response, HttpStatus.OK);

        }
    }

}
