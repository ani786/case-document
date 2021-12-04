package com.nswlrs.api.portal.internal.casedocumentservice.services;

import com.nswlrs.api.portal.internal.casedocumentservice.delegate.GetDealingsUsedDelegate;
import com.nswlrs.api.portal.internal.casedocumentservice.services.output.GetDealingsUsedResponse;
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
 * The type Get dealings used controller.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
@Validated
@Slf4j
public class GetDealingsUsedController {

    private final GetDealingsUsedDelegate delegate;

    /**
     * Gets dealings used.
     *
     * @param packetId the packet id
     * @return the dealings used
     */
    @GetMapping(path = "/dealings-used/{packetId}")
    @Tag(name = "Get dealings which are related to the packet")
    public ResponseEntity<GetDealingsUsedResponse> getDealingsUsed(
                @PathVariable("packetId") final long packetId) {
        log.info("controller Get  Dealings Used  for packetId {}", packetId);
        return new ResponseEntity<>(delegate.getDealingsUsed(packetId), HttpStatus.OK);
    }

}
