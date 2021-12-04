package com.mydocumentsref.api.portal.internal.casedocumentservice.services;

import com.mydocumentsref.api.portal.internal.casedocumentservice.delegate.GetAffectedPacketTitlesDelegate;
import com.mydocumentsref.api.portal.internal.casedocumentservice.services.output.GetAffectedPacketTitlesResponse;
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
 * The type Get affected packet titles controller.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
@Validated
@Slf4j
public class GetAffectedPacketTitlesController {

    private final GetAffectedPacketTitlesDelegate delegate;

    /**
     * Gets packet get affected packet titles.
     *
     * @param packetId the packet id
     * @return the packet get affected packet titles
     */
    @GetMapping(path = "/titles-affected/{packetId}")
    @Tag(name = " Get  Document titles affected for this packetId")
    public ResponseEntity<GetAffectedPacketTitlesResponse> getPacketGetAffectedPacketTitles(
                @PathVariable("packetId") final long packetId) {
        log.info("controller Get  Document titles affected  for packetId {}", packetId);
        return new ResponseEntity<>(delegate.getPacketGetAffectedPacketTitles(packetId), HttpStatus.OK);
    }

}
