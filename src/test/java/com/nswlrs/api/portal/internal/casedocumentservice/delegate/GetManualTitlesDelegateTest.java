package com.nswlrs.api.portal.internal.casedocumentservice.delegate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import com.nswlrs.api.portal.internal.casedocumentservice.model.PacketImage;
import com.nswlrs.api.portal.internal.casedocumentservice.repository.GetManualTitlesJDBCRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class GetManualTitlesDelegateTest {

    @Mock private GetManualTitlesJDBCRepository jdbcRepository;
    @InjectMocks private GetManualTitlesDelegate underTest;

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

        List<PacketImage> manualTitles = List.of(manualTitle);

        //When
        when(jdbcRepository.getManualTitles(1))
                    .thenReturn(manualTitles);

        //Then
        assertEquals("AB1206786",
                    underTest.getManualTitles(1).getManualTitles().get(0).getDisplayName());
        verify(jdbcRepository, times(1)).getManualTitles(anyLong());
        verify(jdbcRepository, times(1)).getManualTitles(anyLong());
    }

}
