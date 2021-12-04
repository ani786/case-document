package com.nswlrs.api.portal.internal.casedocumentservice.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import com.nswlrs.api.portal.internal.casedocumentservice.delegate.AddDealingUsedDelegate;
import com.nswlrs.api.portal.internal.casedocumentservice.model.PacketImage;
import com.nswlrs.api.portal.internal.casedocumentservice.services.input.AddDealingUsed;
import com.nswlrs.api.portal.internal.casedocumentservice.services.input.AddDealingUsedRequest;
import com.nswlrs.api.portal.internal.casedocumentservice.services.output.AddDealingUsedResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

class AddDealingUsedControllerTest {

    @Mock private AddDealingUsedDelegate delegate;
    @InjectMocks private AddDealingUsedController underTest;

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
    void shouldTest_addDealingUsed() {
        //Given
        AddDealingUsed addDealingUsed =
                    AddDealingUsed.builder().fmtDlgNum("A/182902").packetId(1212806).build();
        AddDealingUsedRequest addDealingUsedRequest = AddDealingUsedRequest.builder().addDealingUsed(addDealingUsed).build();

        //When
        when(delegate.addDealingUsed(addDealingUsedRequest))
                    .thenReturn(AddDealingUsedResponse.builder().packetImages(List.of(PacketImage.builder().packetImageId(2).path("image_name.pdf")
                                .displayName("12345-123").build())).build());

        //then
        ResponseEntity<AddDealingUsedResponse> responseEntity = underTest.addDealingUsed(addDealingUsedRequest);

        if (responseEntity.getBody().getRecordsInserted() > 0 || responseEntity.getBody().getRecordUpdated() > 0) {
            assertEquals(201, responseEntity.getStatusCodeValue());
        }
        assertEquals(200, responseEntity.getStatusCodeValue());
        assertEquals(1, responseEntity.getBody().getPacketImages().size());
        assertEquals("12345-123", responseEntity.getBody().getPacketImages().get(0).getDisplayName());
        verify(delegate, times(1)).addDealingUsed(any());
    }

}
