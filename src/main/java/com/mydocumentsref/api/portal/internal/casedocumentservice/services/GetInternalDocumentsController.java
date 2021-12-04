package com.mydocumentsref.api.portal.internal.casedocumentservice.services;

import com.mydocumentsref.api.portal.internal.casedocumentservice.delegate.GetInternalDocumentsDelegate;
import com.mydocumentsref.api.portal.internal.casedocumentservice.services.output.GetInternalDocumentsResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The type Get internal documents controller.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
@Validated
@Slf4j
public class GetInternalDocumentsController {

    private final GetInternalDocumentsDelegate delegate;

    /**
     * Gets internal documents.
     *
     * @param packetId the packet id
     * @return the internal documents
     */
    @GetMapping(path = "/internal-documents/{packetId}")
    @Tag(name = "Get the internal document categories which the user can select from")
    public ResponseEntity<GetInternalDocumentsResponse> getInternalDocuments(@PathVariable("packetId") final long packetId) {
        log.info("controller getInternalDocuments for packetId {}", packetId);
        return new ResponseEntity<>(delegate.getInternalDocuments(packetId), HttpStatus.OK);
    }

}
