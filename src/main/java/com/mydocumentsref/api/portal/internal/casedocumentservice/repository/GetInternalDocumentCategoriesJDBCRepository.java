package com.mydocumentsref.api.portal.internal.casedocumentservice.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.mydocumentsref.api.portal.internal.casedocumentservice.model.InternalDocumentCategory;
import com.mydocumentsref.api.portal.internal.casedocumentservice.repository.statement.GetInternalDocumentCategoriesStatement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * The type Get internal document categories jdbc repository.
 */
@Repository
@RequiredArgsConstructor
@Slf4j
public class GetInternalDocumentCategoriesJDBCRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    /**
     * Gets internal document categories.
     *
     * @return the internal document categories
     */
    public List<InternalDocumentCategory> getInternalDocumentCategories() {
        log.info("repository getInternalDocumentCategories  no input params required");

        return namedParameterJdbcTemplate
                    .query(GetInternalDocumentCategoriesStatement.GET_INTERNAL_DOCUMENT_CATEGORIES,
                                new RowMapper<InternalDocumentCategory>() {
                                    public InternalDocumentCategory mapRow(ResultSet rs, int rowNum)
                                                throws SQLException {
                                        InternalDocumentCategory affectedPacketTitles = InternalDocumentCategory.builder()
                                                    .codeDesc(rs.getString("code_desc"))
                                                    .codeValue(rs.getString("code_value"))
                                                    .build();
                                        return affectedPacketTitles;
                                    }

                                });
    }

}
