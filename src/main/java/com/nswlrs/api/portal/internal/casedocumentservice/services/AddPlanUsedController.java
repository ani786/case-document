package com.nswlrs.api.portal.internal.casedocumentservice.services;

import javax.validation.Valid;

import com.nswlrs.api.portal.internal.casedocumentservice.delegate.AddPlanUsedDelegate;
import com.nswlrs.api.portal.internal.casedocumentservice.model.AddPlansUsedPacketImage;
import com.nswlrs.api.portal.internal.casedocumentservice.services.input.AddPlanUsedRequest;
import com.nswlrs.api.portal.internal.casedocumentservice.services.output.AddPlanUsedResponse;
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
 * The type Add plan used controller.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
@Validated
@Slf4j
public class AddPlanUsedController {

    private final AddPlanUsedDelegate delegate;

    /**
     * Add plan used response entity.
     *
     * @param request the request
     * @return the response entity
     */
    @PostMapping(path = "/plan-used")
    @Tag(name = "Add a Plan used (scanned image in DIIMS) via C-API")
    public ResponseEntity<AddPlanUsedResponse> addPlanUsed(
                @Valid @RequestBody final AddPlanUsedRequest request) {
        log.info("controller addPlanUsed: {}", request);
        AddPlanUsedResponse response = delegate.addPlanUsed(request);
        for (AddPlansUsedPacketImage addPlansUsedPacketImage : response.getPlans().get(0).getPlan()) {
            if (addPlansUsedPacketImage.getRecordUpdated() > 0 || addPlansUsedPacketImage.getRecordsInserted() > 0) {
                return new ResponseEntity<>(response, HttpStatus.CREATED);
            } else if (StringUtils.isNoneEmpty(addPlansUsedPacketImage.getResponseErrorCode())) {
                return new ResponseEntity<>(response, HttpStatus.UNPROCESSABLE_ENTITY);
            }
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
