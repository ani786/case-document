package com.mydocumentsref.api.portal.internal.casedocumentservice.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.mydocumentsref.api.portal.internal.casedocumentservice.model.DeedUsed;
import com.mydocumentsref.api.portal.internal.casedocumentservice.repository.statement.GetDeedsUsedStatement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

/**
 * The type Get deeds used jdbc repository.
 */
@Repository
@RequiredArgsConstructor
@Slf4j
public class GetDeedsUsedJDBCRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    /**
     * Gets deeds used.
     *
     * @param packetId the packet id
     * @return the deeds used
     */
    public List<DeedUsed> getDeedsUsed(final long packetId) {
        log.info("repository Get  Deeds Used for packetId {} ", packetId);
        final SqlParameterSource parameterSource = new MapSqlParameterSource().addValue("packetId", packetId);
        return namedParameterJdbcTemplate
                    .query(GetDeedsUsedStatement.GET_DEEDS_USED, parameterSource,
                                new RowMapper<DeedUsed>() {
                                    public DeedUsed mapRow(ResultSet rs, int rowNum)
                                                throws SQLException {
                                        DeedUsed deedUsed = DeedUsed.builder()
                                                    .displayName(rs.getString("display_name"))
                                                    .packetImageId(rs.getLong("packet_image_id"))
                                                    .path(rs.getString("path"))
                                                    .build();
                                        return deedUsed;
                                    }

                                });
    }

}
