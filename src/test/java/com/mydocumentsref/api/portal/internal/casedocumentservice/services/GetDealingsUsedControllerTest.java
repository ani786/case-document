package com.mydocumentsref.api.portal.internal.casedocumentservice.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import com.mydocumentsref.api.portal.internal.casedocumentservice.delegate.GetDealingsUsedDelegate;
import com.mydocumentsref.api.portal.internal.casedocumentservice.model.DealingUsed;
import com.mydocumentsref.api.portal.internal.casedocumentservice.services.output.GetDealingsUsedResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

class GetDealingsUsedControllerTest {

    @Mock private GetDealingsUsedDelegate delegate;
    @InjectMocks private GetDealingsUsedController underTest;

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
    void shouldTest_getDealingsUsed() {
        //Given
        DealingUsed dealingUsed =
                    DealingUsed.builder().displayName("AB1206786").path("\\nas-folder\\path\\image_name.pdf")
                                .packetImageId(1L).build();

        //When
        when(delegate.getDealingsUsed(1))
                    .thenReturn(GetDealingsUsedResponse.builder().dealingsUsed(List.of(dealingUsed)).build());

        //then
        ResponseEntity<GetDealingsUsedResponse> responseEntity = underTest.getDealingsUsed(1);

        assertEquals(200, responseEntity.getStatusCodeValue());
        assertEquals(1, responseEntity.getBody().getDealingsUsed().size());
        assertEquals("AB1206786", responseEntity.getBody().getDealingsUsed().get(0).getDisplayName());
        verify(delegate, times(1)).getDealingsUsed(anyLong());
    }

}
