package com.mydocumentsref.api.portal.internal.casedocumentservice.services;

import com.mydocumentsref.api.portal.internal.casedocumentservice.delegate.GetUnregisteredPlansDelegate;
import com.mydocumentsref.api.portal.internal.casedocumentservice.services.output.GetUnregisteredPlansResponse;
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
 * The type Get unregistered plans controller.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
@Validated
@Slf4j
public class GetUnregisteredPlansController {

    private final GetUnregisteredPlansDelegate delegate;

    /**
     * Gets unregistered plans.
     *
     * @param packetId the packet id
     * @return the unregistered plans
     */
    @GetMapping(path = "/unregistered-plans/{packetId}")
    @Tag(name = " Get any plans on the titles which are still classified as unregistered for this packetId")
    public ResponseEntity<GetUnregisteredPlansResponse> getUnregisteredPlans(
                @PathVariable("packetId") final long packetId) {
        log.info("controller Get  Document titles unregistered plan  for packetId {}", packetId);
        return new ResponseEntity<>(delegate.getUnregisteredPlans(packetId), HttpStatus.OK);
    }

}
