package com.nswlrs.api.portal.internal.casedocumentservice.services;

import javax.validation.Valid;

import com.nswlrs.api.portal.internal.casedocumentservice.delegate.AddInternalDocumentDelegate;
import com.nswlrs.api.portal.internal.casedocumentservice.services.input.AddInternalDocumentRequest;
import com.nswlrs.api.portal.internal.casedocumentservice.services.output.AddInternalDocumentResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The type Add internal document controller.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
@Validated
@Slf4j
public class AddInternalDocumentController {

    private final AddInternalDocumentDelegate delegate;

    /**
     * Add internal document response entity.
     *
     * @param request the request
     * @return the response entity
     */
    @PostMapping(path = "/internal-document")
    @Tag(name = "Add internal document to the packet")
    public ResponseEntity<AddInternalDocumentResponse> addInternalDocument(
                @Valid @RequestBody final AddInternalDocumentRequest request) {
        log.info("controller addInternalDocument: {}", request);
        AddInternalDocumentResponse response = delegate.addInternalDocument(request);
        if (response.getRecordsInserted() > 0 || response.getRecordUpdated() > 0) {
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } else if (StringUtils.isNoneEmpty(response.getResponseErrorCode())) {
            return new ResponseEntity<>(response, HttpStatus.UNPROCESSABLE_ENTITY);
        } else {
            return new ResponseEntity<>(response, HttpStatus.OK);

        }
    }

}
