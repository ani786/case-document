package com.mydocumentsref.api.portal.internal.casedocumentservice.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.mydocumentsref.api.portal.internal.casedocumentservice.model.PacketImage;
import com.mydocumentsref.api.portal.internal.casedocumentservice.repository.statement.GetManualTitlesStatement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

/**
 * The type Get manual titles jdbc repository.
 */
@Repository
@RequiredArgsConstructor
@Slf4j
public class GetManualTitlesJDBCRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    /**
     * Gets manual titles.
     *
     * @param packetId the packet id
     * @return the manual titles
     */
    public List<PacketImage> getManualTitles(final long packetId) {
        log.info("repository Get  Manual titles affected  for packetId {} ", packetId);
        final SqlParameterSource parameterSource = new MapSqlParameterSource().addValue("packetId", packetId);
        return namedParameterJdbcTemplate
                    .query(GetManualTitlesStatement.GET_MANUAL_TITLES, parameterSource,
                                new RowMapper<PacketImage>() {
                                    public PacketImage mapRow(ResultSet rs, int rowNum)
                                                throws SQLException {
                                        PacketImage manualTitle = PacketImage.builder()
                                                    .displayName(rs.getString("display_name"))
                                                    .packetImageId(rs.getLong("packet_image_id"))
                                                    .path(rs.getString("path"))
                                                    .build();
                                        return manualTitle;
                                    }

                                });
    }

}
