package com.mydocumentsref.api.portal.internal.casedocumentservice.delegate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import com.mydocumentsref.api.portal.internal.casedocumentservice.model.CREdocument;
import com.mydocumentsref.api.portal.internal.casedocumentservice.repository.GetCREdocumentsJDBCRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class GetCREdocumentsDelegateTest {

    @Mock private GetCREdocumentsJDBCRepository jdbcRepository;
    @InjectMocks private GetCREdocumentsDelegate underTest;

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
    void shouldTest_getCREdocuments() {
        //Given
        CREdocument crEdocument =
                    CREdocument.builder().folRecId(1L).path("\\nas-folder\\path\\image_name.pdf")
                                .packetImageId(1L).build();

        List<CREdocument> crEdocuments = List.of(crEdocument);

        //When
        when(jdbcRepository.getCREdocuments(1))
                    .thenReturn(crEdocuments);

        //Then
        assertEquals(1L,
                    underTest.getCREdocuments(1).getCreDocuments().get(0).getPacketImageId());
        verify(jdbcRepository, times(1)).getCREdocuments(anyLong());
        verify(jdbcRepository, times(1)).getCREdocuments(anyLong());
    }

}
