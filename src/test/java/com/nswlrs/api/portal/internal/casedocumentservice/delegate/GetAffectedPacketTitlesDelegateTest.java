package com.mydocumentsref.api.portal.internal.casedocumentservice.delegate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import com.mydocumentsref.api.portal.internal.casedocumentservice.model.AffectedPacketTitle;
import com.mydocumentsref.api.portal.internal.casedocumentservice.repository.GetAffectedPacketTitlesJDBCRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class GetAffectedPacketTitlesDelegateTest {

    @Mock
    private GetAffectedPacketTitlesJDBCRepository jdbcRepository;
    @InjectMocks
    private GetAffectedPacketTitlesDelegate underTest;

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
        AffectedPacketTitle
                    affectedPacketTitle =
                    AffectedPacketTitle.builder().titleRef("A/182902").folRecId("1212806").build();

        List<AffectedPacketTitle> affectedPacketTitles = List.of(affectedPacketTitle);

        //When
        when(jdbcRepository.getPreviousVersionLodgedDocFromFolioParcel(1))
                    .thenReturn(affectedPacketTitles);
        when(jdbcRepository.getRecordsetFromDEALINGOSREF(1))
                    .thenReturn(affectedPacketTitles);
        //Then
        assertEquals("A/182902",
                    underTest.getPacketGetAffectedPacketTitles(1).getAffectedPacketTitles().get(0).getTitleRef());
        verify(jdbcRepository, times(1)).getPreviousVersionLodgedDocFromFolioParcel(anyLong());
        verify(jdbcRepository, times(1)).getRecordsetFromDEALINGOSREF(anyLong());

    }

}
