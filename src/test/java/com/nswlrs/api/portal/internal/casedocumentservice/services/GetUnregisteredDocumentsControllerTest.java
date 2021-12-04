package com.nswlrs.api.portal.internal.casedocumentservice.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import com.nswlrs.api.portal.internal.casedocumentservice.delegate.GetUnregisteredDocumentsDelegate;
import com.nswlrs.api.portal.internal.casedocumentservice.model.UnregisteredDocument;
import com.nswlrs.api.portal.internal.casedocumentservice.services.output.GetUnregisteredDocumentsResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

class GetUnregisteredDocumentsControllerTest {

    @Mock private GetUnregisteredDocumentsDelegate delegate;
    @InjectMocks private GetUnregisteredDocumentsController underTest;

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
    void shouldTest_getUnregisteredDocuments() {

        //Given
        UnregisteredDocument
                    unregisteredDocument =
                    UnregisteredDocument.builder().packetId(0).dlgId(12087253).fmtDlgNum("DP1123956")
                                .documentName("SUBDIVISION").build();

        //When
        when(delegate.getUnregisteredDocuments(1))
                    .thenReturn(
                                GetUnregisteredDocumentsResponse.builder()
                                            .unregisteredDocuments(List.of(unregisteredDocument))
                                            .build());

        //then
        ResponseEntity<GetUnregisteredDocumentsResponse> responseEntity = underTest.getUnregisteredDocuments(1);

        assertEquals(200, responseEntity.getStatusCodeValue());
        assertEquals(1, responseEntity.getBody().getUnregisteredDocuments().size());
        assertEquals("DP1123956", responseEntity.getBody().getUnregisteredDocuments().get(0).getFmtDlgNum());
        verify(delegate, times(1)).getUnregisteredDocuments(anyLong());
    }
}
