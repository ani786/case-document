package com.mydocumentsref.api.portal.internal.casedocumentservice.delegate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import com.mydocumentsref.api.portal.internal.casedocumentservice.model.DealingUsed;
import com.mydocumentsref.api.portal.internal.casedocumentservice.repository.GetDealingsUsedJDBCRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class GetDealingsUsedDelegateTest {

    @Mock private GetDealingsUsedJDBCRepository jdbcRepository;
    @InjectMocks private GetDealingsUsedDelegate underTest;

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

        List<DealingUsed> dealingsUsed = List.of(dealingUsed);

        //When
        when(jdbcRepository.getDealingsUsed(1))
                    .thenReturn(dealingsUsed);

        //Then
        assertEquals("AB1206786",
                    underTest.getDealingsUsed(1).getDealingsUsed().get(0).getDisplayName());
        verify(jdbcRepository, times(1)).getDealingsUsed(anyLong());
        verify(jdbcRepository, times(1)).getDealingsUsed(anyLong());
    }

}
