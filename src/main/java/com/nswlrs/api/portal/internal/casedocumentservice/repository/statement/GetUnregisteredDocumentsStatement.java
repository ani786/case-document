package com.nswlrs.api.portal.internal.casedocumentservice.repository.statement;

/**
 * The enum Get unregistered documents statement.
 */
public enum GetUnregisteredDocumentsStatement {
    ;
    /**
     * The constant GET_UNREGISTERED_DOCUMENTS.
     */
    public static final String GET_UNREGISTERED_DOCUMENTS = """

                SELECT DISTINCT otherpacket.PACKET_ID,
                dlg.DLG_ID,
                dlg.FMT_DLG_NUM,
                CASE WHEN (dlg.DLG_TYPE = 'PA' AND dlg.PA_CAT IS NOT NULL) THEN pacat.CODE_DESC ELSE dlgcode.DESCRIPTION END AS DOCUMENT_NAME
                FROM DEALING dlg
                INNER JOIN FOLIO_DEALING fd ON fd.DLG_ID = dlg.DLG_ID
                INNER JOIN DEALING_CODE dlgcode ON dlgcode.DLG_TYPE = dlg.DLG_TYPE
                LEFT JOIN PACKET otherpacket ON otherpacket.DLG_ID = dlg.REGN_CASE_DLG_ID
                LEFT JOIN CODE_TYPE_MEMBER pacat ON pacat.CODE_TYPE_NUM = 10 AND pacat.CODE_VALUE = dlg.PA_CAT
                WHERE dlg.DLG_STATUS IN (SELECT GENERIC_CODE FROM LIST_MEMBER WHERE LIST_NUM = 417)
                AND dlg.DLG_TYPE NOT IN (SELECT GENERIC_CODE FROM LIST_MEMBER WHERE LIST_NUM IN (560, 762)) -- Is not in: [Unregistered plans] - DP, SP, PE, PP
                AND fd.FOL_REC_ID IN (SELECT DISTINCT FOL_REC_ID FROM FOLIO_DEALING, DEALING WHERE DEALING.DLG_ID = FOLIO_DEALING.DLG_ID AND
                DEALING.REGN_CASE_DLG_ID = (SELECT DLG_ID FROM PACKET p WHERE p.PACKET_ID = :packetId AND p.PACKET_ACTIVE_SIG = 'Y') )
                AND dlg.REGN_CASE_DLG_ID != (SELECT DLG_ID FROM PACKET p WHERE p.PACKET_ID = :packetId AND p.PACKET_ACTIVE_SIG = 'Y')
                AND (otherpacket.PACKET_ID IS NULL OR otherpacket.PACKET_ACTIVE_SIG = 'Y')

                """;

}
