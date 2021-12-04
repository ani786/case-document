package com.nswlrs.api.portal.internal.casedocumentservice.services;

import javax.validation.Valid;

import com.nswlrs.api.portal.internal.casedocumentservice.delegate.GetFormattedTitleSearchDelegate;
import com.nswlrs.api.portal.internal.casedocumentservice.services.input.FormattedTitleSearchRequest;
import com.nswlrs.api.portal.internal.casedocumentservice.services.output.GetFormattedTitleSearchResponse;
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
 * The type Get formatted title search controller.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
@Validated
@Slf4j
public class GetFormattedTitleSearchController {

    private final GetFormattedTitleSearchDelegate delegate;

    /**
     * Gets formatted title search.
     *
     * @param request the request
     * @return the formatted title search
     */
    @PutMapping(path = "/search-fmtd-title")
    @Tag(name = "Get Title Search, Historical Search and CT Inquiry details for a title from C-API")
    public ResponseEntity<GetFormattedTitleSearchResponse> getFormattedTitleSearch(
                @Valid @RequestBody final FormattedTitleSearchRequest request) {
        log.info("controller Get Formatted Title search: {}", request);
        return new ResponseEntity<>(delegate.getFormattedTitleSearch(request), HttpStatus.OK);
    }

}
