package com.nswlrs.api.portal.internal.casedocumentservice.services;

import com.nswlrs.api.portal.internal.casedocumentservice.delegate.GetUnregisteredDocumentsDelegate;
import com.nswlrs.api.portal.internal.casedocumentservice.services.output.GetUnregisteredDocumentsResponse;
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
 * The type Get unregistered documents controller.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
@Validated
@Slf4j
public class GetUnregisteredDocumentsController {

    private final GetUnregisteredDocumentsDelegate delegate;

    /**
     * Gets unregistered documents.
     *
     * @param packetId the packet id
     * @return the unregistered documents
     */
    @GetMapping(path = "/unregistered-documents-no-plans/{packetId}")
    @Tag(name = " Get any documents (excluding plans which will be shown separately) on the titles which are still " +
                "classified as unregistered for this packetId")
    public ResponseEntity<GetUnregisteredDocumentsResponse> getUnregisteredDocuments(
                @PathVariable("packetId") final long packetId) {
        log.info("controller Get  Document titles unregistered for packetId {}", packetId);
        return new ResponseEntity<>(delegate.getUnregisteredDocuments(packetId), HttpStatus.OK);
    }

}
