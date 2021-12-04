package com.mydocumentsref.api.portal.internal.casedocumentservice.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.mydocumentsref.api.portal.internal.casedocumentservice.model.PacketImage;
import com.mydocumentsref.api.portal.internal.casedocumentservice.repository.statement.AddPlanUsedStatement;
import com.mydocumentsref.api.portal.internal.casedocumentservice.services.input.AddPlanUsedRequest;
import com.mydocumentsref.api.portal.internal.casedocumentservice.services.input.PlanImages;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

/**
 * The type Add plan used jdbc repository.
 */
@Repository
@RequiredArgsConstructor
@Slf4j
public class AddPlanUsedJDBCRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    /**
     * Check record exists list.
     *
     * @param request    the request
     * @param planImages the plan images
     * @return the list
     */
    public List<PacketImage> checkRecordExists(final AddPlanUsedRequest request, PlanImages planImages) {
        log.info("repository addPlanUsed checkRecordExists for request planNumber, imageSubtype, packetId {} ", request);
        final SqlParameterSource parameterSource =
                    new MapSqlParameterSource().addValue("planNumber", request.getPlanNumber()).addValue("imageSubtype", planImages.getImageSubtype())
                                .addValue("packetId", request.getPacketId());
        return namedParameterJdbcTemplate
                    .query(AddPlanUsedStatement.RECORD_ALREADY_EXISTS, parameterSource,
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
     * @param request          the request
     * @param planImages       the plan images
     * @param planNumberPrefix the plan number prefix
     * @param path             the path
     * @param subType          the sub type
     * @return the int
     */
    public int insertRecordIntoPacketImageTbl(AddPlanUsedRequest request, PlanImages planImages, String planNumberPrefix, String path, String subType) {
        log.info("repository addPlanUsed insertRecordIntoPacketImageTbl for request {} ", request);
        SqlParameterSource sqlParameterSource =
                    new MapSqlParameterSource().addValue("packetId", request.getPacketId()).addValue("planNumberPrefix", planNumberPrefix)
                                .addValue("imageCategory", planImages.getImageCategory())
                                .addValue("subType", subType).addValue("pathValue", path).addValue("userCode", request.getUserCode())
                                .addValue("planNumber", request.getPlanNumber());
        return namedParameterJdbcTemplate
                    .update(AddPlanUsedStatement.INSERT_INTO_PACKET_IMAGE, sqlParameterSource);

    }

    /**
     * Poll details of records inserted list.
     *
     * @param request    the request
     * @param planImages the plan images
     * @return the list
     */
    public List<PacketImage> pollDetailsOfRecordsInserted(AddPlanUsedRequest request, PlanImages planImages) {
        log.info("repository addPlanUsed pollDetailsOfRecordsInserted for planImages {} ", planImages);
        final SqlParameterSource parameterSource =
                    new MapSqlParameterSource().addValue("planNumber", request.getPlanNumber()).addValue("imageSubtype", planImages.getImageSubtype())
                                .addValue("packetId", request.getPacketId());
        return namedParameterJdbcTemplate
                    .query(AddPlanUsedStatement.POLL_PACKET_IMAGE,
                                parameterSource,
                                new RowMapper<PacketImage>() {
                                    public PacketImage mapRow(ResultSet rs, int rowNum)
                                                throws SQLException {
                                        PacketImage packetImage = PacketImage.builder()
                                                    .displayName(rs.getString("display_name"))
                                                    .packetImageId(rs.getLong("packet_image_id"))
                                                    .path(rs.getString("path"))
                                                    .build();
                                        return packetImage;
                                    }

                                });
    }

    /**
     * Update the las modified date int.
     *
     * @param request the request
     * @return the int
     */
    public int updateTheLasModifiedDate(AddPlanUsedRequest request) {
        SqlParameterSource sqlParameterSource =
                    new MapSqlParameterSource().addValue("packetId", request.getPacketId());
        return namedParameterJdbcTemplate
                    .update(AddPlanUsedStatement.UPDATE_PACKET, sqlParameterSource);
    }
}
