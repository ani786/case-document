package com.nswlrs.api.portal.internal.casedocumentservice.delegate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import com.nswlrs.api.portal.internal.casedocumentservice.model.UnregisteredPlan;
import com.nswlrs.api.portal.internal.casedocumentservice.repository.GetUnregisteredPlansJDBCRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class GetUnregisteredPlansDelegateTest {

    @Mock private GetUnregisteredPlansJDBCRepository jdbcRepository;
    @InjectMocks private GetUnregisteredPlansDelegate underTest;
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

        List<UnregisteredPlan> unregisteredPlans = List.of(unregisteredPlan);

        //When
        when(jdbcRepository.getUnregisteredPlans(1))
                    .thenReturn(unregisteredPlans);

        //Then
        assertEquals("SUBDIVISION",
                    underTest.getUnregisteredPlans(1).getUnregisteredPlans().get(0).getDocumentName());
        verify(jdbcRepository, times(1)).getUnregisteredPlans(anyLong());
        verify(jdbcRepository, times(1)).getUnregisteredPlans(anyLong());
    }

}
