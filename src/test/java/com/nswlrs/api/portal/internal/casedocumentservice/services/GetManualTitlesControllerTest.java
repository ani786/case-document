package com.nswlrs.api.portal.internal.casedocumentservice.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import com.nswlrs.api.portal.internal.casedocumentservice.delegate.GetManualTitlesDelegate;
import com.nswlrs.api.portal.internal.casedocumentservice.model.PacketImage;
import com.nswlrs.api.portal.internal.casedocumentservice.services.output.GetManualTitlesResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

class GetManualTitlesControllerTest {

    @Mock private GetManualTitlesDelegate delegate;
    @InjectMocks private GetManualTitlesController underTest;

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
    void shouldTest_getManualTitles() {

        //Given
        PacketImage manualTitle =
                    PacketImage.builder().displayName("AB1206786").path("\\nas-folder\\path\\image_name.pdf")
                                .packetImageId(1L).build();

        //When
        when(delegate.getManualTitles(1))
                    .thenReturn(GetManualTitlesResponse.builder().manualTitles(List.of(manualTitle)).build());

        //then
        ResponseEntity<GetManualTitlesResponse> responseEntity = underTest.getManualTitles(1);

        assertEquals(200, responseEntity.getStatusCodeValue());
        assertEquals(1, responseEntity.getBody().getManualTitles().size());
        assertEquals("AB1206786", responseEntity.getBody().getManualTitles().get(0).getDisplayName());
        verify(delegate, times(1)).getManualTitles(anyLong());
    }

}
