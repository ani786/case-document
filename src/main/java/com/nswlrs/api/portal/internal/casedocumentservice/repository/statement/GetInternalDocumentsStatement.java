package com.nswlrs.api.portal.internal.casedocumentservice.repository.statement;

/**
 * The enum Get internal documents statement.
 */
public enum GetInternalDocumentsStatement {
    ;

    /**
     * The constant GET_INTERNAL_DOCUMENTS.
     */
    public static final String GET_INTERNAL_DOCUMENTS = """
                SELECT pi.PACKET_IMAGE_ID,
                pi.PACKET_IMAGE_GROUP,
                pi.IMAGE_TYPE,
                pi.IMAGE_SUBTYPE,
                pi.IMAGE_CATEGORY,
                pi.IMAGE_NAME,
                pi.PATH,
                pi.NOTE,
                pi.ADDED_DATE,
                pi.ADDED_BY_USER,
                pi.MODIFIED_DATE
                FROM
                PACKET_IMAGE pi
                WHERE pi.PACKET_IMAGE_GROUP = 'INTRNL'
                AND pi.PACKET_ID = :packetId
                AND pi.ACTIVE_SIG = 'Y'
                """;
}
