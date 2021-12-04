package com.nswlrs.api.portal.internal.casedocumentservice.repository.statement;

/**
 * The enum Get internal document categories statement.
 */
public enum GetInternalDocumentCategoriesStatement {
    ;

    /**
     * The constant GET_INTERNAL_DOCUMENT_CATEGORIES.
     */
    public static final String GET_INTERNAL_DOCUMENT_CATEGORIES = """
                SELECT ctm.CODE_VALUE, ctm.CODE_DESC FROM CODE_TYPE_MEMBER ctm
                WHERE ctm.CODE_TYPE_NUM = 119
                AND ctm.ACTIVE_SIG = 'Y'
                ORDER BY ctm.CODE_DESC ASC
                """;
}
