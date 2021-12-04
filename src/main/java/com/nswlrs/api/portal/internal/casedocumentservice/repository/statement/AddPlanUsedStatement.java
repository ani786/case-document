package com.nswlrs.api.portal.internal.casedocumentservice.repository.statement;

/**
 * The enum Add plan used statement.
 */
public enum AddPlanUsedStatement {
    ;
    /**
     * The constant RECORD_ALREADY_EXISTS.
     */
    public static final String RECORD_ALREADY_EXISTS = """
                select pim.IMAGE_NAME AS DISPLAY_NAME, plan_subtype.CODE_DESC AS SUBTYPE_NAME, pim.PACKET_IMAGE_ID, pim.PATH
                from packet_image pim
                left join CODE_TYPE_MEMBER plan_subtype ON plan_subtype.CODE_VALUE = pim.IMAGE_SUBTYPE AND plan_subtype.CODE_TYPE_NUM = 42
                where pim.PACKET_IMAGE_GROUP = 'PLAN'
                and pim.IMAGE_NAME = :planNumber
                and pim.IMAGE_SUBTYPE = :imageSubtype
                and pim.PACKET_ID = :packetId
                """;

    /**
     * The constant INSERT_INTO_PACKET_IMAGE.
     */
    public static final String INSERT_INTO_PACKET_IMAGE = """
                INSERT INTO PACKET_IMAGE (PACKET_IMAGE_ID, PACKET_ID, PACKET_IMAGE_GROUP, IMAGE_TYPE, IMAGE_SUBTYPE, IMAGE_CATEGORY, PATH, NOTE, ADDED_DATE, ADDED_BY_USER, ACTIVE_SIG, MODIFIED_DATE, IMAGE_NAME)
                   VALUES (PACKET_IMAGE_ID_SEQ.NEXTVAL, :packetId, 'PLAN', :planNumberPrefix, :subType, :imageCategory, :pathValue, null, SYSTIMESTAMP, :userCode, 'P', SYSTIMESTAMP, :planNumber)
                """;

    /**
     * The constant POLL_PACKET_IMAGE.
     */
    public static final String POLL_PACKET_IMAGE = """
                select pim.IMAGE_NAME AS DISPLAY_NAME, plan_subtype.CODE_DESC AS SUBTYPE_NAME, pim.PACKET_IMAGE_ID, pim.PATH
                from packet_image pim
                left join CODE_TYPE_MEMBER plan_subtype ON plan_subtype.CODE_VALUE = pim.IMAGE_SUBTYPE AND plan_subtype.CODE_TYPE_NUM = 42
                where pim.PACKET_IMAGE_GROUP = 'PLAN'
                and pim.IMAGE_NAME = :planNumber
                and pim.IMAGE_SUBTYPE = :imageSubtype
                and pim.PACKET_ID = :packetId
                and pim.ACTIVE_SIG = 'Y'
                """;

    /**
     * The constant UPDATE_PACKET.
     */
    public static final String UPDATE_PACKET = """
                UPDATE PACKET SET MODIFIED_DATE = SYSTIMESTAMP WHERE PACKET_ID = :packetId
                """;
}
