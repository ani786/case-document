package com.mydocumentsref.api.portal.internal.casedocumentservice.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.mydocumentsref.api.portal.internal.casedocumentservice.model.ImageSubtype;
import com.mydocumentsref.api.portal.internal.casedocumentservice.model.PlanNumberDetail;
import com.mydocumentsref.api.portal.internal.casedocumentservice.repository.statement.GetPlansUsedStatement;
import com.mydocumentsref.api.portal.internal.casedocumentservice.services.input.PlansUsedRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

/**
 * The type Get plans used jdbc repository.
 */
@Repository
@RequiredArgsConstructor
@Slf4j
public class GetPlansUsedJDBCRepository {

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    /**
     * Gets sub type name.
     *
     * @param subType the sub type
     * @return the sub type name
     */
    public String getSubTypeName(String subType) {
        log.info("repository getSubTypeName subType {}", subType);
        try {
            return jdbcTemplate.queryForObject(GetPlansUsedStatement.GET_SUB_TYPE_NAME, String.class, subType);
        } catch (EmptyResultDataAccessException exception) {
            return null;
        }
    }

    /**
     * Gets plan number with its and user added source.
     *
     * @param request the request
     * @return the plan number with its and user added source
     */
    public List<PlanNumberDetail> getPlanNumberWithITSAndUserAddedSource(PlansUsedRequest request) {
        log.info("repository getPlanNumberWithITSSource packetId {} ", request.getPlanUsed().getPacketId());
        final SqlParameterSource parameterSource = new MapSqlParameterSource().addValue("packetId", request.getPlanUsed().getPacketId());
        return namedParameterJdbcTemplate
                    .query(GetPlansUsedStatement.GET_PLAN_NUMBER_WITH_ITS_SOURCE, parameterSource,
                                new RowMapper<PlanNumberDetail>() {
                                    public PlanNumberDetail mapRow(ResultSet rs, int rowNum)
                                                throws SQLException {
                                        PlanNumberDetail planNumber = PlanNumberDetail.builder()
                                                    .planNumber(rs.getString("DISPLAY_NAME"))
                                                    .source(rs.getString("SOURCE"))
                                                    .build();
                                        return planNumber;
                                    }

                                });
    }

    /**
     * Gets image id path.
     *
     * @param planNumbers the plan numbers
     * @param subType     the sub type
     * @param request     the request
     * @return the image id path
     */
    public List<ImageSubtype> getImageIdPath(PlanNumberDetail planNumbers, String subType, PlansUsedRequest request) {
        log.info("repository getImageIdPath planNumber {} , subType {} , packetId {} ", planNumbers.getPlanNumber(), subType, request.getPlanUsed().getPacketId());
        final SqlParameterSource parameterSource = new MapSqlParameterSource().addValue("planNumber", planNumbers.getPlanNumber()).addValue("subType", subType)
                    .addValue("packetId", request.getPlanUsed().getPacketId());
        return namedParameterJdbcTemplate
                    .query(GetPlansUsedStatement.GET_IMAGE_ID_PATH, parameterSource,
                                new RowMapper<ImageSubtype>() {
                                    public ImageSubtype mapRow(ResultSet rs, int rowNum)
                                                throws SQLException {
                                        ImageSubtype imageSubtype = ImageSubtype.builder()
                                                    .packetImageId(rs.getString("packet_image_id"))
                                                    .path(rs.getString("path"))
                                                    .build();
                                        return imageSubtype;
                                    }

                                });
    }

    /**
     * Gets image sub types.
     *
     * @param planNumbers the plan numbers
     * @param request     the request
     * @return the image sub types
     */
    public List<ImageSubtype> getImageSubTypes(PlanNumberDetail planNumbers, PlansUsedRequest request) {
        log.info("repository getImageIdPath planNumber {} , subType {} , packetId {} ", planNumbers.getPlanNumber(), request.getPlanUsed().getPacketId());
        final SqlParameterSource parameterSource = new MapSqlParameterSource().addValue("planNumber", planNumbers.getPlanNumber())
                    .addValue("packetId", request.getPlanUsed().getPacketId());
        return namedParameterJdbcTemplate
                    .query(GetPlansUsedStatement.GET_IMAGE_SUB_TYPES, parameterSource,
                                new RowMapper<ImageSubtype>() {
                                    public ImageSubtype mapRow(ResultSet rs, int rowNum)
                                                throws SQLException {
                                        ImageSubtype imageSubtype = ImageSubtype.builder()
                                                    /*.p(rs.getString("DISPLAY_NAME"))*/
                                                    .imageCategory(rs.getString("IMAGE_CATEGORY"))
                                                    .subtypeName(rs.getString("IMAGE_SUBTYPE"))
                                                    .imageSubtypeDesc(rs.getString("image_subtype_desc"))
                                                    .packetImageId(rs.getString("PACKET_IMAGE_ID"))
                                                    .path(rs.getString("path"))
                                                    .build();
                                        return imageSubtype;
                                    }

                                });

    }
}
