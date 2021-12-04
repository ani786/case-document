package com.nswlrs.api.portal.internal.casedocumentservice.services;

import com.nswlrs.api.portal.internal.casedocumentservice.delegate.GetInternalDocumentCategoriesDelegate;
import com.nswlrs.api.portal.internal.casedocumentservice.services.output.GetInternalDocumentCategoriesResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The type Get internal document categories controller.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
@Validated
@Slf4j
public class GetInternalDocumentCategoriesController {

    private final GetInternalDocumentCategoriesDelegate delegate;

    /**
     * Gets internal document categories.
     *
     * @return the internal document categories
     */
    @GetMapping(path = "/internal-document-categories")
    @Tag(name = "Get the internal document categories which the user can select from")
    public ResponseEntity<GetInternalDocumentCategoriesResponse> getInternalDocumentCategories() {
        log.info("controller getInternalDocumentCategories no input params required");
        return new ResponseEntity<>(delegate.getInternalDocumentCategories(), HttpStatus.OK);
    }

}
