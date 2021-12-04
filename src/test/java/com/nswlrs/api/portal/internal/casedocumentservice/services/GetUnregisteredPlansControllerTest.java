package com.mydocumentsref.api.portal.internal.casedocumentservice.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import com.mydocumentsref.api.portal.internal.casedocumentservice.delegate.GetUnregisteredPlansDelegate;
import com.mydocumentsref.api.portal.internal.casedocumentservice.model.UnregisteredPlan;
import com.mydocumentsref.api.portal.internal.casedocumentservice.services.output.GetUnregisteredPlansResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

class GetUnregisteredPlansControllerTest {

    @Mock private GetUnregisteredPlansDelegate delegate;
    @InjectMocks private GetUnregisteredPlansController underTest;

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
    void shouldTest_getUnregisteredPlans() {

        //Given
        UnregisteredPlan unregisteredPlan =
                    UnregisteredPlan.builder().packetId(0).dlgId(12087253).fmtDlgNum("DP1123956")
                                .documentName("SUBDIVISION").build();

        //When
        when(delegate.getUnregisteredPlans(1))
                    .thenReturn(
                                GetUnregisteredPlansResponse.builder().unregisteredPlans(List.of(unregisteredPlan))
                                            .build());

        //then
        ResponseEntity<GetUnregisteredPlansResponse> responseEntity = underTest.getUnregisteredPlans(1);

        assertEquals(200, responseEntity.getStatusCodeValue());
        assertEquals(1, responseEntity.getBody().getUnregisteredPlans().size());
        assertEquals("DP1123956", responseEntity.getBody().getUnregisteredPlans().get(0).getFmtDlgNum());
        verify(delegate, times(1)).getUnregisteredPlans(anyLong());
    }

}
