package com.nswlrs.api.portal.internal.casedocumentservice.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import com.nswlrs.api.portal.internal.casedocumentservice.delegate.GetDeedsUsedDelegate;
import com.nswlrs.api.portal.internal.casedocumentservice.model.DeedUsed;
import com.nswlrs.api.portal.internal.casedocumentservice.services.output.GetDeedsUsedResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

class GetDeedsUsedControllerTest {

    @Mock private GetDeedsUsedDelegate delegate;
    @InjectMocks private GetDeedsUsedController underTest;

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
        DeedUsed deedUsed =
                    DeedUsed.builder().displayName("AB1206786").path("\\nas-folder\\path\\image_name.pdf")
                                .packetImageId(1L).build();

        //When
        when(delegate.getDeedsUsed(1))
                    .thenReturn(GetDeedsUsedResponse.builder().deedsUsed(List.of(deedUsed)).build());

        //then
        ResponseEntity<GetDeedsUsedResponse> responseEntity = underTest.getDeedsUsed(1);

        assertEquals(200, responseEntity.getStatusCodeValue());
        assertEquals(1, responseEntity.getBody().getDeedsUsed().size());
        assertEquals("AB1206786", responseEntity.getBody().getDeedsUsed().get(0).getDisplayName());
        verify(delegate, times(1)).getDeedsUsed(anyLong());
    }

}
