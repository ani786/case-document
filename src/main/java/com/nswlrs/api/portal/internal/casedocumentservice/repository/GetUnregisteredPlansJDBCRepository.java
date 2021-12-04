package com.nswlrs.api.portal.internal.casedocumentservice.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.nswlrs.api.portal.internal.casedocumentservice.model.UnregisteredPlan;
import com.nswlrs.api.portal.internal.casedocumentservice.repository.statement.GetUnregisteredPlansStatement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

/**
 * The type Get unregistered plans jdbc repository.
 */
@Repository
@RequiredArgsConstructor
@Slf4j
public class GetUnregisteredPlansJDBCRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    /**
     * Gets unregistered plans.
     *
     * @param packetId the packet id
     * @return the unregistered plans
     */
    public List<UnregisteredPlan> getUnregisteredPlans(final long packetId) {
        log.info("repository Get  Document titles unregistered plan for packetId {} ", packetId);
        final SqlParameterSource parameterSource = new MapSqlParameterSource().addValue("packetId", packetId);
        return namedParameterJdbcTemplate
                    .query(GetUnregisteredPlansStatement.GET_UNREGISTERED_PLANS, parameterSource,
                                new RowMapper<UnregisteredPlan>() {
                                    public UnregisteredPlan mapRow(ResultSet rs, int rowNum)
                                                throws SQLException {
                                        UnregisteredPlan unregisteredPlans = UnregisteredPlan.builder()
                                                    .packetId(rs.getLong("packet_id"))
                                                    .dlgId(rs.getLong("dlg_id"))
                                                    .fmtDlgNum(rs.getString("fmt_dlg_num"))
                                                    .documentName(rs.getString("document_name"))
                                                    .build();
                                        return unregisteredPlans;
                                    }

                                });
    }

}
