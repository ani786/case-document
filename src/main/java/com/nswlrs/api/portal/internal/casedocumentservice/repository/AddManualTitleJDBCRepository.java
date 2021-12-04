package com.nswlrs.api.portal.internal.casedocumentservice.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.nswlrs.api.portal.internal.casedocumentservice.model.PacketImage;
import com.nswlrs.api.portal.internal.casedocumentservice.repository.statement.AddManualTitleStatement;
import com.nswlrs.api.portal.internal.casedocumentservice.services.input.AddManualTitleRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

/**
 * The type Add manual title jdbc repository.
 */
@Repository
@RequiredArgsConstructor
@Slf4j
public class AddManualTitleJDBCRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    /**
     * Check record exists list.
     *
     * @param request the request
     * @return the list
     */
    public List<PacketImage> checkRecordExists(final AddManualTitleRequest request) {
        log.info("repository addManualTitle checkRecordExists for fmtDlgNum {} ", request.getAddManualTitle().getTitleRef());
        final SqlParameterSource parameterSource = new MapSqlParameterSource().addValue("titleRef", request.getAddManualTitle().getTitleRef())
                    .addValue("packetId", request.getAddManualTitle().getPacketId());
        return namedParameterJdbcTemplate
                    .query(AddManualTitleStatement.RECORD_ALREADY_EXISTS, parameterSource,
                                new RowMapper<PacketImage>() {
                                    public PacketImage mapRow(ResultSet rs, int rowNum)
                                                throws SQLException {
                                        PacketImage affectedPacketTitles = PacketImage.builder()
                                                    .displayName(rs.getString("DISPLAY_NAME"))
                                                    .packetImageId(rs.getLong("PACKET_IMAGE_ID"))
                                                    .path(rs.getString("PATH"))
                                                    .build();
                                        return affectedPacketTitles;
                                    }

                                });
    }

    /**
     * Insert record into packet image tbl int.
     *
     * @param request the request
     * @param path    the path
     * @return the int
     */
    public int insertRecordIntoPacketImageTbl(AddManualTitleRequest request, String path) {
        log.info("repository addManualTitle insertRecordIntoPacketImageTbl for request {} ", request);
        SqlParameterSource sqlParameterSource =
                    new MapSqlParameterSource().addValue("packetId", request.getAddManualTitle().getPacketId())
                                .addValue("pathValue", path).addValue("userCode", request.getUserCode()).addValue("titleRef", request.getAddManualTitle().getTitleRef());
        return namedParameterJdbcTemplate
                    .update(AddManualTitleStatement.INSERT_INTO_PACKET_IMAGE, sqlParameterSource);

    }

    /**
     * Poll details of records inserted list.
     *
     * @param request the request
     * @return the list
     */
    public List<PacketImage> pollDetailsOfRecordsInserted(AddManualTitleRequest request) {
        log.info("repository addManualTitle pollDetailsOfRecordsInserted for request {} ", request);
        final SqlParameterSource parameterSource =
                    new MapSqlParameterSource().addValue("titleRef", request.getAddManualTitle().getTitleRef())
                                .addValue("packetId", request.getAddManualTitle().getPacketId());
        return namedParameterJdbcTemplate
                    .query(AddManualTitleStatement.POLL_PACKET_IMAGE,
                                parameterSource,
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

    /**
     * Update the las modified date int.
     *
     * @param request the request
     * @return the int
     */
    public int updateTheLasModifiedDate(AddManualTitleRequest request) {
        SqlParameterSource sqlParameterSource =
                    new MapSqlParameterSource().addValue("packetId", request.getAddManualTitle().getPacketId());
        return namedParameterJdbcTemplate
                    .update(AddManualTitleStatement.UPDATE_PACKET, sqlParameterSource);
    }
}
