package com.nswlrs.api.portal.internal.casedocumentservice.repository;

import com.nswlrs.api.portal.internal.casedocumentservice.repository.statement.GetFormattedTitleSearchStatement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * The type Get formatted title search jdbc repository.
 */
@Repository
@RequiredArgsConstructor
@Slf4j
public class GetFormattedTitleSearchJDBCRepository {

    private final JdbcTemplate jdbcTemplate;

    /**
     * Gets lt osection.
     *
     * @param userCode the user code
     * @return the lt osection
     */
    public String getLTOsection(final String userCode) {

        try {
            return jdbcTemplate
                        .queryForObject(GetFormattedTitleSearchStatement.GET_LTO_SECTION, String.class,
                                    userCode);
        } catch (EmptyResultDataAccessException exception) {
            return null;
        }

    }

}
