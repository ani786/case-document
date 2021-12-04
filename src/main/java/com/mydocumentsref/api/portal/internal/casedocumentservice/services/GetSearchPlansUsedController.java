package com.mydocumentsref.api.portal.internal.casedocumentservice.services;

import javax.validation.Valid;

import com.mydocumentsref.api.portal.internal.casedocumentservice.delegate.GetSearchPlansUsedDelegate;
import com.mydocumentsref.api.portal.internal.casedocumentservice.services.input.SearchPlansUsedRequest;
import com.mydocumentsref.api.portal.internal.casedocumentservice.services.output.GetSearchPlansUsedResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The type Get search plans used controller.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
@Validated
@Slf4j
public class GetSearchPlansUsedController {

    private final GetSearchPlansUsedDelegate delegate;

    /**
     * Gets search plans used.
     *
     * @param request the request
     * @return the search plans used
     */
    @PutMapping(path = "/search-plans-used")
    @Tag(name = "Search plans to show the documents associated with them, and allow the documents to be linked to a packet from C-API")
    public ResponseEntity<GetSearchPlansUsedResponse> getSearchPlansUsed(@Valid @RequestBody final SearchPlansUsedRequest request) {
        log.info("controller Get Search PlansUsed  request: {}", request);
        GetSearchPlansUsedResponse response = delegate.getSearchPlansUsed(request);
        if (StringUtils.isNoneEmpty(response.getSearchPlansUsed().get(0).getResponseErrorCode())) {
            return new ResponseEntity<>(response, HttpStatus.UNPROCESSABLE_ENTITY);
        } else {
            return new ResponseEntity<>(response, HttpStatus.OK);

        }
    }

}
