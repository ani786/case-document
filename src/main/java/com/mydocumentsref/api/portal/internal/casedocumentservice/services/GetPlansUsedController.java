package com.mydocumentsref.api.portal.internal.casedocumentservice.services;

import javax.validation.Valid;

import com.mydocumentsref.api.portal.internal.casedocumentservice.delegate.GetPlansUsedDelegate;
import com.mydocumentsref.api.portal.internal.casedocumentservice.services.input.PlansUsedRequest;
import com.mydocumentsref.api.portal.internal.casedocumentservice.services.output.GetPlansUsedResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The type Get plans used controller.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
@Validated
@Slf4j
public class GetPlansUsedController {

    private final GetPlansUsedDelegate delegate;

    /**
     * Gets plans used.
     *
     * @param request the request
     * @return the plans used
     */
    @PutMapping(path = "/plans-used")
    @Tag(name = "Get plans which are related to the packet from C-API")
    public ResponseEntity<GetPlansUsedResponse> getPlansUsed(@Valid @RequestBody final PlansUsedRequest request) {
        log.info("controller GetPlansUsed  request: {}", request);
        return new ResponseEntity<>(delegate.getPlansUsed(request), HttpStatus.OK);
    }

}
