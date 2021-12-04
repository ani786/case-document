package com.nswlrs.api.portal.internal.casedocumentservice.repository;

import com.nswlrs.api.portal.internal.casedocumentservice.repository.statement.GetSearchPlansUsedStatement;
import com.nswlrs.api.portal.internal.casedocumentservice.services.input.SearchPlansUsedRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * The type Get search plans used jdbc repository.
 */
@Repository
@RequiredArgsConstructor
@Slf4j
public class GetSearchPlansUsedJDBCRepository {

    private final JdbcTemplate jdbcTemplate;

    /**
     * Gets sub type name.
     *
     * @param subType the sub type
     * @return the sub type name
     */
    public String getSubTypeName(String subType) {
        log.info("repository getSubTypeName subType {}", subType);
        try {
            return jdbcTemplate.queryForObject(GetSearchPlansUsedStatement.GET_SUB_TYPE_NAME, String.class, subType);
        } catch (EmptyResultDataAccessException exception) {
            return null;
        }
    }

    /**
     * Check record exists string.
     *
     * @param request the request
     * @param subType the sub type
     * @return the string
     */
    public String checkRecordExists(SearchPlansUsedRequest request, String subType) {
        log.info("repository checkRecordExists request {} subType {}", request, subType);
        try {
            return jdbcTemplate.queryForObject(GetSearchPlansUsedStatement.CHECK_RECORD_EXISTS, String.class, request.getSearchPlanUsed().getPlanNumber(), subType,
                        request.getSearchPlanUsed().getPacketId());
        } catch (EmptyResultDataAccessException exception) {
            return null;
        }
    }
}
