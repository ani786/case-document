package com.mydocumentsref.api.portal.internal.casedocumentservice.repository.statement;

/**
 * The enum Add deed used statement.
 */
public enum AddDeedUsedStatement {
    ;
    /**
     * The constant RECORD_ALREADY_EXISTS.
     */
    public static final String RECORD_ALREADY_EXISTS = """
                select pim.IMAGE_NAME AS DISPLAY_NAME, pim.PACKET_IMAGE_ID, pim.PATH
                from packet_image pim
                where pim.PACKET_IMAGE_GROUP = 'DEED'                
                and pim.IMAGE_NAME = :encumNum
                and pim.PACKET_ID = :packetId
                                """;

    /**
     * The constant INSERT_INTO_PACKET_IMAGE.
     */
    public static final String INSERT_INTO_PACKET_IMAGE = """
                 INSERT INTO PACKET_IMAGE (PACKET_IMAGE_ID, PACKET_ID, PACKET_IMAGE_GROUP, IMAGE_TYPE, IMAGE_SUBTYPE, IMAGE_CATEGORY, PATH, NOTE, ADDED_DATE, ADDED_BY_USER, ACTIVE_SIG, MODIFIED_DATE, IMAGE_NAME)
                VALUES (PACKET_IMAGE_ID_SEQ.NEXTVAL, :packetId, 'DEED', 'BK', :subType, null, :pathValue, null, SYSTIMESTAMP, :userCode, 'P', SYSTIMESTAMP, :encumNum)
                 """;

    /**
     * The constant POLL_PACKET_IMAGE.
     */
    public static final String POLL_PACKET_IMAGE = """
                select pim.IMAGE_NAME AS DISPLAY_NAME, pim.PACKET_IMAGE_ID, pim.PATH
                from packet_image pim
                where pim.PACKET_IMAGE_GROUP = 'DEED'
                and pim.IMAGE_NAME = :encumNum
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
