package com.nswlrs.api.portal.internal.casedocumentservice.delegate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import com.nswlrs.api.portal.internal.casedocumentservice.model.UnregisteredDocument;
import com.nswlrs.api.portal.internal.casedocumentservice.repository.GetUnregisteredDocumentsJDBCRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class GetUnregisteredDocumentsDelegateTest {

    @Mock private GetUnregisteredDocumentsJDBCRepository jdbcRepository;
    @InjectMocks private GetUnregisteredDocumentsDelegate underTest;

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

        List<UnregisteredDocument> unregisteredDocuments = List.of(unregisteredDocument);

        //When
        when(jdbcRepository.getUnregisteredDocuments(1))
                    .thenReturn(unregisteredDocuments);

        //Then
        assertEquals("SUBDIVISION",
                    underTest.getUnregisteredDocuments(1).getUnregisteredDocuments().get(0).getDocumentName());
        verify(jdbcRepository, times(1)).getUnregisteredDocuments(anyLong());
        verify(jdbcRepository, times(1)).getUnregisteredDocuments(anyLong());
    }

}
