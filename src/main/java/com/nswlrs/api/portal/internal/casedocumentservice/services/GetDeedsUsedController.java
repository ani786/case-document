package com.nswlrs.api.portal.internal.casedocumentservice.services;

import com.nswlrs.api.portal.internal.casedocumentservice.delegate.GetDeedsUsedDelegate;
import com.nswlrs.api.portal.internal.casedocumentservice.services.output.GetDeedsUsedResponse;
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
 * The type Get deeds used controller.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
@Validated
@Slf4j
public class GetDeedsUsedController {

    private final GetDeedsUsedDelegate delegate;

    /**
     * Gets deeds used.
     *
     * @param packetId the packet id
     * @return the deeds used
     */
    @GetMapping(path = "/deeds-used/{packetId}")
    @Tag(name = "Get Deeds which are related to the packet")
    public ResponseEntity<GetDeedsUsedResponse> getDeedsUsed(
                @PathVariable("packetId") final long packetId) {
        log.info("controller Get  Deeds Used  for packetId {}", packetId);
        return new ResponseEntity<>(delegate.getDeedsUsed(packetId), HttpStatus.OK);
    }

}
