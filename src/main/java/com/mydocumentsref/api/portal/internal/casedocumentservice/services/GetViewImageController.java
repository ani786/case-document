package com.mydocumentsref.api.portal.internal.casedocumentservice.services;

import javax.validation.Valid;

import com.mydocumentsref.api.portal.internal.casedocumentservice.delegate.GetViewImageDelegate;
import com.mydocumentsref.api.portal.internal.casedocumentservice.services.input.ViewImageRequest;
import com.mydocumentsref.api.portal.internal.casedocumentservice.services.output.GetViewImageResponse;
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
 * The type Get view image controller.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
@Validated
@Slf4j
public class GetViewImageController {

    private final GetViewImageDelegate delegate;

    /**
     * Gets formatted title search.
     *
     * @param request the request
     * @return the formatted title search
     */
    @PutMapping(path = "/view-image")
    @Tag(name = "Get View document image from DIIMS via the Search feature from C-API")
    public ResponseEntity<GetViewImageResponse> getViewImage( @Valid @RequestBody final ViewImageRequest request) {
        log.info("controller Get ViewImageRequest: {}", request);
        return new ResponseEntity<>(delegate.getViewImage(request), HttpStatus.OK);
    }

}
