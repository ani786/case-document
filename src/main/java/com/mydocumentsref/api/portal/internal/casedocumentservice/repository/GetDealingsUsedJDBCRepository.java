package com.mydocumentsref.api.portal.internal.casedocumentservice.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.mydocumentsref.api.portal.internal.casedocumentservice.model.DealingUsed;
import com.mydocumentsref.api.portal.internal.casedocumentservice.repository.statement.GetDealingsUsedStatement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

/**
 * The type Get dealings used jdbc repository.
 */
@Repository
@RequiredArgsConstructor
@Slf4j
public class GetDealingsUsedJDBCRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    /**
     * Gets dealings used.
     *
     * @param packetId the packet id
     * @return the dealings used
     */
    public List<DealingUsed> getDealingsUsed(final long packetId) {
        log.info("repository Get  Dealings Used for packetId {} ", packetId);
        final SqlParameterSource parameterSource = new MapSqlParameterSource().addValue("packetId", packetId);
        return namedParameterJdbcTemplate
                    .query(GetDealingsUsedStatement.GET_DEALINGS_USED, parameterSource,
                                new RowMapper<DealingUsed>() {
                                    public DealingUsed mapRow(ResultSet rs, int rowNum)
                                                throws SQLException {
                                        DealingUsed dealingUsed = DealingUsed.builder()
                                                    .displayName(rs.getString("display_name"))
                                                    .packetImageId(rs.getLong("packet_image_id"))
                                                    .path(rs.getString("path"))
                                                    .build();
                                        return dealingUsed;
                                    }

                                });
    }

}
