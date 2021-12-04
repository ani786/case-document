package com.nswlrs.api.portal.internal.casedocumentservice.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.nswlrs.api.portal.internal.casedocumentservice.model.CREdocument;
import com.nswlrs.api.portal.internal.casedocumentservice.repository.statement.GetCREdocumentsStatement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

/**
 * The type Get cr edocuments jdbc repository.
 */
@Repository
@RequiredArgsConstructor
@Slf4j
public class GetCREdocumentsJDBCRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    /**
     * Gets cr edocuments.
     *
     * @param packetId the packet id
     * @return the cr edocuments
     */
    public List<CREdocument> getCREdocuments(final long packetId) {
        log.info("repository Get CRE Used for packetId {} ", packetId);
        final SqlParameterSource parameterSource = new MapSqlParameterSource().addValue("packetId", packetId);
        return namedParameterJdbcTemplate
                    .query(GetCREdocumentsStatement.GET_CRE, parameterSource,
                                new RowMapper<CREdocument>() {
                                    public CREdocument mapRow(ResultSet rs, int rowNum)
                                                throws SQLException {
                                        CREdocument crEdocument = CREdocument.builder()
                                                    .titleRef(rs.getString("title_ref"))
                                                    .folRecId(rs.getLong("fol_rec_id"))
                                                    .packetImageId(rs.getLong("packet_image_id"))
                                                    .path(rs.getString("path"))
                                                    .build();
                                        return crEdocument;
                                    }

                                });
    }

}
