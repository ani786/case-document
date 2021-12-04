package com.nswlrs.api.portal.internal.casedocumentservice.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.nswlrs.api.portal.internal.casedocumentservice.model.AffectedPacketTitle;
import com.nswlrs.api.portal.internal.casedocumentservice.repository.statement.GetAffectedPacketTitlesStatement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

/**
 * The type Get affected packet titles jdbc repository.
 */
@Repository
@RequiredArgsConstructor
@Slf4j
public class GetAffectedPacketTitlesJDBCRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    /**
     * Gets previous version lodged doc from folio parcel.
     *
     * @param packetId the packet id
     * @return the previous version lodged doc from folio parcel
     */
    public List<AffectedPacketTitle> getPreviousVersionLodgedDocFromFolioParcel(final long packetId) {
        log.info("repository Get  Document titles affected  for packetId {} ", packetId);
        final SqlParameterSource parameterSource = new MapSqlParameterSource().addValue("packetId", packetId);
        return namedParameterJdbcTemplate
                    .query(GetAffectedPacketTitlesStatement.GET_PREVIOUS_VER_LODGED_DOC_FROM_FOLIO_PARCEL,
                                parameterSource,
                                new RowMapper<AffectedPacketTitle>() {
                                    public AffectedPacketTitle mapRow(ResultSet rs, int rowNum)
                                                throws SQLException {
                                        AffectedPacketTitle affectedPacketTitles = AffectedPacketTitle.builder()
                                                    .folRecId(rs.getString("fol_rec_id"))
                                                    .titleRef(rs.getString("title_ref"))
                                                    .searchAvailable(rs.getString("search_available"))
                                                    .build();
                                        return affectedPacketTitles;
                                    }

                                });
    }

    /**
     * Gets recordset from dealingosref.
     *
     * @param packetId the packet id
     * @return the recordset from dealingosref
     */
    public List<AffectedPacketTitle> getRecordsetFromDEALINGOSREF(final long packetId) {
        log.info("repository Get  Document titles affected  for packetId {} ", packetId);
        final SqlParameterSource parameterSource = new MapSqlParameterSource().addValue("packetId", packetId);
        return namedParameterJdbcTemplate
                    .query(GetAffectedPacketTitlesStatement.GET_DEALING_FROM_OSREF_DOC, parameterSource,
                                new RowMapper<AffectedPacketTitle>() {
                                    public AffectedPacketTitle mapRow(ResultSet rs, int rowNum)
                                                throws SQLException {
                                        AffectedPacketTitle affectedPacketTitles = AffectedPacketTitle.builder()
                                                    .folRecId(rs.getString("fol_rec_id"))
                                                    .titleRef(rs.getString("title_ref"))
                                                    .searchAvailable(rs.getString("search_available"))
                                                    .build();
                                        return affectedPacketTitles;
                                    }

                                });
    }
}
