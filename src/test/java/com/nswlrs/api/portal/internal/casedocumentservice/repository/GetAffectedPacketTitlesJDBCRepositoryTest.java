package com.nswlrs.api.portal.internal.casedocumentservice.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.nswlrs.api.portal.internal.casedocumentservice.model.AffectedPacketTitle;
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

class GetAffectedPacketTitlesJDBCRepositoryTest {

    @Mock private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @InjectMocks private GetAffectedPacketTitlesJDBCRepository underTest;

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
    void shouldTest_getPreviousVersionLodgedDocFromFolioParcel() {
        List<AffectedPacketTitle> affectedPacketTitlesList = new ArrayList<>();
        //Given
        List<AffectedPacketTitle> finalAffectedPacketTitlesList = affectedPacketTitlesList;
        Mockito.doAnswer(invocationOnMock -> {
            ResultSet resultSet = Mockito.mock(ResultSet.class);
            Mockito.when((resultSet.next())).thenReturn(true).thenReturn(false);
            Mockito.when(resultSet.getString("fol_rec_id")).thenReturn("50110");
            Mockito.when(resultSet.getString("title_ref")).thenReturn("CP/SP3122");
            Mockito.when(resultSet.getString("search_available")).thenReturn("Y");
            RowMapper<AffectedPacketTitle> rowMapper =
                        invocationOnMock.getArgument(2, RowMapper.class);
            finalAffectedPacketTitlesList.add(rowMapper.mapRow(resultSet, 1));
            return finalAffectedPacketTitlesList;
            //When
        }).when(namedParameterJdbcTemplate).query(
                    Mockito.anyString(),
                    Mockito.any(MapSqlParameterSource.class),
                    Mockito.any(RowMapper.class)
        );

        //Then
        affectedPacketTitlesList = underTest.getPreviousVersionLodgedDocFromFolioParcel(1);

        assertEquals("CP/SP3122", affectedPacketTitlesList.get(0).getTitleRef());
    }

    @Test
    void shouldTest_appendRecordsetFromDEALINGOSREF() {

        List<AffectedPacketTitle> affectedPacketTitlesList = new ArrayList<>();
        //Given
        List<AffectedPacketTitle> finalAffectedPacketTitlesList = affectedPacketTitlesList;
        Mockito.doAnswer(invocationOnMock -> {
            ResultSet resultSet = Mockito.mock(ResultSet.class);
            Mockito.when((resultSet.next())).thenReturn(true).thenReturn(false);
            Mockito.when(resultSet.getString("fol_rec_id")).thenReturn("50110");
            Mockito.when(resultSet.getString("title_ref")).thenReturn("CP/SP3122");
            Mockito.when(resultSet.getString("search_available")).thenReturn("Y");
            RowMapper<AffectedPacketTitle> rowMapper =
                        invocationOnMock.getArgument(2, RowMapper.class);
            finalAffectedPacketTitlesList.add(rowMapper.mapRow(resultSet, 1));
            return finalAffectedPacketTitlesList;
            //When
        }).when(namedParameterJdbcTemplate).query(
                    Mockito.anyString(),
                    Mockito.any(MapSqlParameterSource.class),
                    Mockito.any(RowMapper.class)
        );

        //Then
        affectedPacketTitlesList = underTest.getRecordsetFromDEALINGOSREF(1);

        assertEquals("CP/SP3122", affectedPacketTitlesList.get(0).getTitleRef());
    }

}
