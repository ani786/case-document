package com.mydocumentsref.api.portal.internal.casedocumentservice.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.mydocumentsref.api.portal.internal.casedocumentservice.model.PacketImage;
import com.mydocumentsref.api.portal.internal.casedocumentservice.repository.statement.AddDealingUsedStatement;
import com.mydocumentsref.api.portal.internal.casedocumentservice.services.input.AddDealingUsedRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

/**
 * The type Add dealing used jdbc repository.
 */
@Repository
@RequiredArgsConstructor
@Slf4j
public class AddDealingUsedJDBCRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    /**
     * Check record exists list.
     *
     * @param request the request
     * @return the list
     */
    public List<PacketImage> checkRecordExists(final AddDealingUsedRequest request) {
        log.info("repository addDealingUsed checkRecordExists for fmtDlgNum {} ", request.getAddDealingUsed().getFmtDlgNum());
        final SqlParameterSource parameterSource = new MapSqlParameterSource().addValue("fmtDlgNum", request.getAddDealingUsed().getFmtDlgNum())
                    .addValue("packetId", request.getAddDealingUsed().getPacketId());
        return namedParameterJdbcTemplate
                    .query(AddDealingUsedStatement.RECORD_ALREADY_EXISTS, parameterSource,
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
     * @param subType the sub type
     * @return the int
     */
    public int insertRecordIntoPacketImageTbl(AddDealingUsedRequest request, String path, String subType) {
        log.info("repository addDealingUsed insertRecordIntoPacketImageTbl for request {} ", request);
        SqlParameterSource sqlParameterSource =
                    new MapSqlParameterSource().addValue("packetId", request.getAddDealingUsed().getPacketId())
                                .addValue("pathValue", path).addValue("userCode", request.getUserCode())
                                .addValue("fmtDlgNum", request.getAddDealingUsed().getFmtDlgNum()).addValue("subType", subType);
        return namedParameterJdbcTemplate
                    .update(AddDealingUsedStatement.INSERT_INTO_PACKET_IMAGE, sqlParameterSource);

    }

    /**
     * Poll details of records inserted list.
     *
     * @param request the request
     * @return the list
     */
    public List<PacketImage> pollDetailsOfRecordsInserted(AddDealingUsedRequest request) {
        log.info("repository addDealingUsed pollDetailsOfRecordsInserted for request {} ", request);
        final SqlParameterSource parameterSource =
                    new MapSqlParameterSource().addValue("fmtDlgNum", request.getAddDealingUsed().getFmtDlgNum())
                                .addValue("packetId", request.getAddDealingUsed().getPacketId());
        return namedParameterJdbcTemplate
                    .query(AddDealingUsedStatement.POLL_PACKET_IMAGE,
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
    public int updateTheLasModifiedDate(AddDealingUsedRequest request) {
        SqlParameterSource sqlParameterSource =
                    new MapSqlParameterSource().addValue("packetId", request.getAddDealingUsed().getPacketId());
        return namedParameterJdbcTemplate
                    .update(AddDealingUsedStatement.UPDATE_PACKET, sqlParameterSource);
    }
}
