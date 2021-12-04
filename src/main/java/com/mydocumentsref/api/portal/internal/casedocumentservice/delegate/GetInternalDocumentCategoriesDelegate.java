package com.mydocumentsref.api.portal.internal.casedocumentservice.delegate;

import com.mydocumentsref.api.portal.internal.casedocumentservice.repository.GetInternalDocumentCategoriesJDBCRepository;
import com.mydocumentsref.api.portal.internal.casedocumentservice.services.output.GetInternalDocumentCategoriesResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * The type Get internal document categories delegate.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class GetInternalDocumentCategoriesDelegate {

    private final GetInternalDocumentCategoriesJDBCRepository jdbcRepository;

    /**
     * Gets internal document categories.
     *
     * @return the internal document categories
     */
    public GetInternalDocumentCategoriesResponse getInternalDocumentCategories() {
        log.info("delegate getInternalDocumentCategories no input params required");
        GetInternalDocumentCategoriesResponse getInternalDocumentCategoriesResponse =
                    GetInternalDocumentCategoriesResponse.builder().internalDocumentCategories(jdbcRepository.getInternalDocumentCategories()).build();

        return getInternalDocumentCategoriesResponse;

    }

}
