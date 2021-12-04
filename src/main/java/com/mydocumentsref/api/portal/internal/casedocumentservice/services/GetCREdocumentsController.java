package com.mydocumentsref.api.portal.internal.casedocumentservice.services;

import com.mydocumentsref.api.portal.internal.casedocumentservice.delegate.GetCREdocumentsDelegate;
import com.mydocumentsref.api.portal.internal.casedocumentservice.services.output.GetCREdocumentsResponse;
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
 * The type Get cr edocuments controller.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
@Validated
@Slf4j
public class GetCREdocumentsController {

    private final GetCREdocumentsDelegate delegate;

    /**
     * Gets cr edocuments.
     *
     * @param packetId the packet id
     * @return the cr edocuments
     */
    @GetMapping(path = "/cre-documents/{packetId}")
    @Tag(name = "Get CRE documents which are related to the packet")
    public ResponseEntity<GetCREdocumentsResponse> getCREdocuments(
                @PathVariable("packetId") final long packetId) {
        log.info("controller Get  CRE Used  for packetId {}", packetId);
        return new ResponseEntity<>(delegate.getCREdocuments(packetId), HttpStatus.OK);
    }

}
