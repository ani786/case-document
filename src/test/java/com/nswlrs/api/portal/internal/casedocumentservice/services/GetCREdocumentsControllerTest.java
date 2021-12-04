package com.nswlrs.api.portal.internal.casedocumentservice.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import com.nswlrs.api.portal.internal.casedocumentservice.delegate.GetCREdocumentsDelegate;
import com.nswlrs.api.portal.internal.casedocumentservice.model.CREdocument;
import com.nswlrs.api.portal.internal.casedocumentservice.services.output.GetCREdocumentsResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

class GetCREdocumentsControllerTest {

    @Mock private GetCREdocumentsDelegate delegate;
    @InjectMocks private GetCREdocumentsController underTest;

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
        //When
        when(delegate.getCREdocuments(1))
                    .thenReturn(GetCREdocumentsResponse.builder().creDocuments(List.of(crEdocument)).build());

        //then
        ResponseEntity<GetCREdocumentsResponse> responseEntity = underTest.getCREdocuments(1);

        assertEquals(200, responseEntity.getStatusCodeValue());
        assertEquals(1, responseEntity.getBody().getCreDocuments().size());
        assertEquals(1L, responseEntity.getBody().getCreDocuments().get(0).getPacketImageId());
        verify(delegate, times(1)).getCREdocuments(anyLong());
    }

}
