package com.nswlrs.api.portal.internal.casedocumentservice.services;

import com.nswlrs.api.portal.internal.casedocumentservice.delegate.GetManualTitlesDelegate;
import com.nswlrs.api.portal.internal.casedocumentservice.services.output.GetManualTitlesResponse;
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
 * The type Get manual titles controller.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
@Validated
@Slf4j
public class GetManualTitlesController {

    private final GetManualTitlesDelegate delegate;

    /**
     * Gets manual titles.
     *
     * @param packetId the packet id
     * @return the manual titles
     */
    @GetMapping(path = "/manual-titles/{packetId}")
    @Tag(name = "Get manual titles which have been uploaded/added to the packet")
    public ResponseEntity<GetManualTitlesResponse> getManualTitles(
                @PathVariable("packetId") final long packetId) {
        log.info("controller Get  Manual titles   for packetId {}", packetId);
        return new ResponseEntity<>(delegate.getManualTitles(packetId), HttpStatus.OK);
    }

}
