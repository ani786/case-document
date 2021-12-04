package com.nswlrs.api.portal.internal.casedocumentservice.services;

import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import com.nswlrs.api.portal.internal.casedocumentservice.delegate.GetLodgedDocumentDelegate;
import com.nswlrs.api.portal.internal.casedocumentservice.services.output.LodgedDocumentResponse;
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
 * The type Get lodged document controller.
 */
@Slf4j
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1")
public class GetLodgedDocumentController {

    private final GetLodgedDocumentDelegate lodgedDocumentDelegate;

    /**
     * Gets lodge documents by packet id.
     *
     * @param packetId the packet id
     * @return the lodge documents by packet id
     */
    @GetMapping(path = "/lodged-documents/{packetId}")
    @Tag(name = "Get lodged documents for a packet, including previous versions")
    public ResponseEntity<LodgedDocumentResponse> getLodgeDocumentsByPacketId(
                @PathVariable("packetId") @Positive @Size(max = 8) final String packetId) {
        log.info("get lodged document list by packet id: {} ", packetId);
        return new ResponseEntity<>(lodgedDocumentDelegate.getLodgedDocumentsByPacketId(Integer.parseInt(packetId)),
                    HttpStatus.OK);
    }
}
