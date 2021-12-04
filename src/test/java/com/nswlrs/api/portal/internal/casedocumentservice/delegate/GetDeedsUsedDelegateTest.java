package com.mydocumentsref.api.portal.internal.casedocumentservice.delegate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import com.mydocumentsref.api.portal.internal.casedocumentservice.model.DeedUsed;
import com.mydocumentsref.api.portal.internal.casedocumentservice.repository.GetDeedsUsedJDBCRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class GetDeedsUsedDelegateTest {

    @Mock private GetDeedsUsedJDBCRepository jdbcRepository;
    @InjectMocks private GetDeedsUsedDelegate underTest;

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

        List<DeedUsed> deedsUsed = List.of(deedUsed);

        //When
        when(jdbcRepository.getDeedsUsed(1))
                    .thenReturn(deedsUsed);

        //Then
        assertEquals("AB1206786",
                    underTest.getDeedsUsed(1).getDeedsUsed().get(0).getDisplayName());
        verify(jdbcRepository, times(1)).getDeedsUsed(anyLong());
        verify(jdbcRepository, times(1)).getDeedsUsed(anyLong());
    }

}
