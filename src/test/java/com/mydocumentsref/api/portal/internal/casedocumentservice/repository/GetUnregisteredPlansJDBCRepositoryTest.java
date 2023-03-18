package com.mydocumentsref.api.portal.internal.casedocumentservice.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.mydocumentsref.api.portal.internal.casedocumentservice.model.UnregisteredPlan;
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

class GetUnregisteredPlansJDBCRepositoryTest {

    @Mock private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @InjectMocks private GetUnregisteredPlansJDBCRepository underTest;

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

        List<UnregisteredPlan> unregisteredPlans = new ArrayList<>();
        //Given
        List<UnregisteredPlan> finalUnregisteredPlans = unregisteredPlans;
        Mockito.doAnswer(invocationOnMock -> {
            ResultSet resultSet = Mockito.mock(ResultSet.class);
            Mockito.when((resultSet.next())).thenReturn(true).thenReturn(false);
            Mockito.when(resultSet.getString("packet_id")).thenReturn("0");
            Mockito.when(resultSet.getString("dlg_id")).thenReturn("1234567");
            Mockito.when(resultSet.getString("fmt_dlg_num")).thenReturn("DP2342342");
            RowMapper<UnregisteredPlan> rowMapper =
                        invocationOnMock.getArgument(2, RowMapper.class);
            finalUnregisteredPlans.add(rowMapper.mapRow(resultSet, 1));
            return finalUnregisteredPlans;
            //When
        }).when(namedParameterJdbcTemplate).query(
                    Mockito.anyString(),
                    Mockito.any(MapSqlParameterSource.class),
                    Mockito.any(RowMapper.class)
        );

        //Then
        unregisteredPlans = underTest.getUnregisteredPlans(1);

        assertEquals("DP2342342", unregisteredPlans.get(0).getFmtDlgNum());
    }

}
