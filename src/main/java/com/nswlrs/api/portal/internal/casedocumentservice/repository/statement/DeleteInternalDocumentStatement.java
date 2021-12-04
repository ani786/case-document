package com.nswlrs.api.portal.internal.casedocumentservice.repository.statement;

/**
 * The enum Delete internal document statement.
 */
public enum DeleteInternalDocumentStatement {
    ;

    /**
     * The constant GET_PACKET_ADDED_BY.
     */
    public static final String GET_PACKET_ADDED_BY = """
                 SELECT pktimg.ADDED_BY_USER FROM PACKET_IMAGE pktimg WHERE pktimg.PACKET_IMAGE_ID = :packetImageId
                """;
    /**
     * The constant UPDATE_DOCUMENT.
     */
    public static final String UPDATE_DOCUMENT = """
                UPDATE PACKET_IMAGE SET ACTIVE_SIG = 'N' WHERE PACKET_IMAGE_ID = :packetImageId
                """;
}
