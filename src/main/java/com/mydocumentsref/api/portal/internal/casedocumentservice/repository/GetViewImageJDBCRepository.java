package com.mydocumentsref.api.portal.internal.casedocumentservice.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.mydocumentsref.api.portal.internal.casedocumentservice.model.ViewImage;
import com.mydocumentsref.api.portal.internal.casedocumentservice.model.ViewImage;
import com.mydocumentsref.api.portal.internal.casedocumentservice.model.ViewImage;
import com.mydocumentsref.api.portal.internal.casedocumentservice.repository.statement.GetUnregisteredPlansStatement;
import com.mydocumentsref.api.portal.internal.casedocumentservice.repository.statement.GetViewImageStatement;
import com.mydocumentsref.api.portal.internal.casedocumentservice.services.input.ViewImageRequest;
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
 * The type Get view image jdbc repository.
 */
@Repository
@RequiredArgsConstructor
@Slf4j
public class GetViewImageJDBCRepository {

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    /**
     * Gets lt osection.
     *
     * @param userCode the user code
     * @return the lt osection
     */
    public String getLTOsection(final String userCode) {

        try {
            return jdbcTemplate
                        .queryForObject(GetViewImageStatement.GET_LTO_SECTION, String.class,
                                    userCode);
        } catch (EmptyResultDataAccessException exception) {
            return null;
        }

    }




    public List<ViewImage> getUnregisteredImage(ViewImageRequest request) {
        log.info("repository getUnregisteredImage for fmtDlgNum {} ", request.getViewImage().getFmtDlgNum());
        final SqlParameterSource parameterSource = new MapSqlParameterSource().addValue("fmtDlgNum", request.getViewImage().getFmtDlgNum());
        return namedParameterJdbcTemplate
                    .query(GetViewImageStatement.GET_DEALING_TD_PATH_FOR_UNREGISTERED, parameterSource,
                                new RowMapper<ViewImage>() {
                                    public ViewImage mapRow(ResultSet rs, int rowNum)
                                                throws SQLException {
                                        ViewImage packetImage = ViewImage.builder()
                                                    .dealingImageId(rs.getLong("DEALING_IMAGE_ID"))
                                                    .path(rs.getString("PATH"))
                                                    .build();
                                        return packetImage;
                                    }

                                });
    }
}
