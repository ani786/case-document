package com.nswlrs.api.portal.internal.casedocumentservice.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import com.nswlrs.api.portal.internal.casedocumentservice.delegate.GetAffectedPacketTitlesDelegate;
import com.nswlrs.api.portal.internal.casedocumentservice.model.AffectedPacketTitle;
import com.nswlrs.api.portal.internal.casedocumentservice.services.output.GetAffectedPacketTitlesResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

class GetAffectedPacketTitlesControllerTest {

    @Mock
    private GetAffectedPacketTitlesDelegate delegate;
    @InjectMocks
    private GetAffectedPacketTitlesController underTest;

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
    void shouldTest_getPacketGetAffectedPacketTitles() {

        //Given
        AffectedPacketTitle affectedPacketTitles =
                    AffectedPacketTitle.builder().titleRef("A/182902").folRecId("1212806").build();

        //When
        when(delegate.getPacketGetAffectedPacketTitles(1))
                    .thenReturn(
                                GetAffectedPacketTitlesResponse.builder()
                                            .affectedPacketTitles(List.of(affectedPacketTitles))
                                            .build());

        //then
        ResponseEntity<GetAffectedPacketTitlesResponse> responseEntity = underTest.getPacketGetAffectedPacketTitles(1);

        assertEquals(200, responseEntity.getStatusCodeValue());
        assertEquals(1, responseEntity.getBody().getAffectedPacketTitles().size());
        assertEquals("A/182902", responseEntity.getBody().getAffectedPacketTitles().get(0).getTitleRef());
        verify(delegate, times(1)).getPacketGetAffectedPacketTitles(anyLong());

    }

}
