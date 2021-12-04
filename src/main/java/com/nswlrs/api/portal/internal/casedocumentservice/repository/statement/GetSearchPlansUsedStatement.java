package com.nswlrs.api.portal.internal.casedocumentservice.repository.statement;

/**
 * The enum Get search plans used statement.
 */
public enum GetSearchPlansUsedStatement {
    ;

    /**
     * The constant GET_SUB_TYPE_NAME.
     */
    public static final String GET_SUB_TYPE_NAME = """
                 SELECT CODE_DESC AS IMAGE_SUBTYPE FROM CODE_TYPE_MEMBER WHERE CODE_TYPE_NUM = 42 AND CODE_VALUE = :subType
                """;
    /**
     * The constant CHECK_RECORD_EXISTS.
     */
    public static final String CHECK_RECORD_EXISTS = """
                SELECT pim.PACKET_IMAGE_ID
                FROM packet_image pim
                WHERE pim.ACTIVE_SIG = 'Y'
                AND pim.PACKET_IMAGE_GROUP = 'PLAN'
                AND pim.IMAGE_NAME = :planNumber
                AND pim.IMAGE_SUBTYPE = :subType
                AND pim.PACKET_ID = :packetId
                """;
}
