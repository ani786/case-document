package com.nswlrs.api.portal.internal.casedocumentservice.services;

import javax.validation.Valid;

import com.nswlrs.api.portal.internal.casedocumentservice.delegate.AddManualTitleDelegate;
import com.nswlrs.api.portal.internal.casedocumentservice.services.input.AddManualTitleRequest;
import com.nswlrs.api.portal.internal.casedocumentservice.services.output.AddManualTitleResponse;
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
 * The type Add manual title controller.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
@Validated
@Slf4j
public class AddManualTitleController {

    private final AddManualTitleDelegate delegate;

    /**
     * Add manual title response entity.
     *
     * @param request the request
     * @return the response entity
     */
    @PostMapping(path = "/manual-title")
    @Tag(name = "Add a manual title (scanned image in DIIMS) to the case based on a manual title reference from C-API")
    public ResponseEntity<AddManualTitleResponse> addManualTitle(
                @Valid @RequestBody final AddManualTitleRequest request) {
        log.info("controller addManualTitle: {}", request);
        AddManualTitleResponse response = delegate.addManualTitle(request);
        if (response.getRecordsInserted() > 0 || response.getRecordUpdated() > 0) {
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } else if (StringUtils.isNoneEmpty(response.getResponseErrorCode())) {
            return new ResponseEntity<>(response, HttpStatus.UNPROCESSABLE_ENTITY);
        } else {
            return new ResponseEntity<>(response, HttpStatus.OK);

        }
    }

}
