package com.mydocumentsref.api.portal.internal.casedocumentservice.services;

import javax.validation.Valid;

import com.mydocumentsref.api.portal.internal.casedocumentservice.delegate.AddDeedUsedDelegate;
import com.mydocumentsref.api.portal.internal.casedocumentservice.services.input.AddDeedUsedRequest;
import com.mydocumentsref.api.portal.internal.casedocumentservice.services.output.AddDeedUsedResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The type Add deed used controller.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
@Validated
@Slf4j
public class AddDeedUsedController {

    private final AddDeedUsedDelegate delegate;

    /**
     * Add deed used response entity.
     *
     * @param request the request
     * @return the response entity
     */
    @PostMapping(path = "/deed-used")
    @Tag(name = "Add a deed used (scanned image in DIIMS) via C-API")
    public ResponseEntity<AddDeedUsedResponse> addDeedUsed(
                @Valid @RequestBody final AddDeedUsedRequest request) {
        log.info("controller addDeedUsed: {}", request);
        AddDeedUsedResponse response = delegate.addDeedUsed(request);
        if (response.getRecordsInserted() > 0 || response.getRecordUpdated() > 0) {
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } else if (StringUtils.isNoneEmpty(response.getResponseErrorCode())) {
            return new ResponseEntity<>(response, HttpStatus.UNPROCESSABLE_ENTITY);
        } else {
            return new ResponseEntity<>(response, HttpStatus.OK);

        }
    }

}
