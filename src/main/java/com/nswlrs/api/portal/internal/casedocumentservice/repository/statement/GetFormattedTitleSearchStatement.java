package com.nswlrs.api.portal.internal.casedocumentservice.repository.statement;

/**
 * The enum Get formatted title search statement.
 */
public enum GetFormattedTitleSearchStatement {
    ;
    /**
     * The constant GET_LTO_SECTION.
     */
    public static final String GET_LTO_SECTION = """
                 SELECT LTO_SECTION FROM USERS WHERE USER_CODE = ?
                """;

}
