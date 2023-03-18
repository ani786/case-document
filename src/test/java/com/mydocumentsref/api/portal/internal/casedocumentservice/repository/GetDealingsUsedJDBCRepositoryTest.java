package com.mydocumentsref.api.portal.internal.casedocumentservice.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.mydocumentsref.api.portal.internal.casedocumentservice.model.DealingUsed;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

class GetDealingsUsedJDBCRepositoryTest {

    @Mock private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @InjectMocks private GetDealingsUsedJDBCRepository underTest;

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
        List<DealingUsed> manualTitles = new ArrayList<>();
        //Given
        List<DealingUsed> finalDealingsUsed = manualTitles;
        Mockito.doAnswer(invocationOnMock -> {
            ResultSet resultSet = Mockito.mock(ResultSet.class);
            Mockito.when((resultSet.next())).thenReturn(true).thenReturn(false);
            Mockito.when(resultSet.getString("display_name")).thenReturn("12345-123");
            Mockito.when(resultSet.getLong("packet_image_id")).thenReturn(2L);
            Mockito.when(resultSet.getString("path")).thenReturn("\\nas-folder\\path\\image_name.pdf");
            RowMapper<DealingUsed> rowMapper =
                        invocationOnMock.getArgument(2, RowMapper.class);
            finalDealingsUsed.add(rowMapper.mapRow(resultSet, 1));
            return finalDealingsUsed;
            //When
        }).when(namedParameterJdbcTemplate).query(
                    Mockito.anyString(),
                    Mockito.any(MapSqlParameterSource.class),
                    Mockito.any(RowMapper.class)
        );

        //Then
        manualTitles = underTest.getDealingsUsed(1);

        assertEquals("12345-123", manualTitles.get(0).getDisplayName());
    }

}
