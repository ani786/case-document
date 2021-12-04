package com.nswlrs.api.portal.internal.casedocumentservice.repository.statement;

/**
 * The enum Add internal document statement.
 */
public enum AddInternalDocumentStatement {
    ;

    /**
     * The constant UPDATE_PACKET_MODIFIED_DATE.
     */
    public static final String UPDATE_PACKET_MODIFIED_DATE = """
                UPDATE PACKET SET MODIFIED_DATE = SYSDATE WHERE PACKET_ID = :packetId  
                """;
    /**
     * The constant UPDATE_PACKET_COUNT.
     */
    public static final String UPDATE_PACKET_COUNT = """
                UPDATE PACKET SET PACKET_UPDATE_COUNT = PACKET_UPDATE_COUNT + 1
                WHERE DLG_ID = :dealingId
                AND PACKET.OWNER != :userCode  
                """;

    /**
     * The constant INSERT_RECORD_INTO_PACKET_IMAGE_TBL.
     */
    public static final String INSERT_RECORD_INTO_PACKET_IMAGE_TBL = """
                INSERT INTO PACKET_IMAGE (PACKET_IMAGE_ID, PACKET_ID, PACKET_IMAGE_GROUP, IMAGE_TYPE, IMAGE_SUBTYPE, IMAGE_CATEGORY, PATH, NOTE, ADDED_DATE, ADDED_BY_USER, ACTIVE_SIG, MODIFIED_DATE, IMAGE_NAME)
                 VALUES (PACKET_IMAGE_ID_SEQ.NEXTVAL, :packetId, 'INTRNL', '.', null, :imageCategory, :pathValue, :note, SYSTIMESTAMP, :userCode, 'Y', SYSTIMESTAMP, :fileName)
                 """;
}
