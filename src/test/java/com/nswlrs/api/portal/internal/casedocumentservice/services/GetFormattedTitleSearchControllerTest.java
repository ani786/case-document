package com.mydocumentsref.api.portal.internal.casedocumentservice.services;

import javax.validation.Valid;

import com.mydocumentsref.api.portal.internal.casedocumentservice.delegate.GetFormattedTitleSearchDelegate;
import com.mydocumentsref.api.portal.internal.casedocumentservice.services.input.FormattedTitleSearchRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class GetFormattedTitleSearchControllerTest {

    @Mock private GetFormattedTitleSearchDelegate delegate;
    @InjectMocks private GetFormattedTitleSearchController underTest;

    private AutoCloseable closeable;

    @BeforeEach
    public void setup() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void closeService() throws Exception {
        closeable.close();
    }

    @Test
    void getFormattedTitleSearch() {
    }

    @Nested
    class WhenGettingFormattedTitleSearch {
        @Mock
        private @Valid FormattedTitleSearchRequest request;

        @BeforeEach
        void setup() {
        }
    }
}
