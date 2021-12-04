package com.nswlrs.api.portal.internal.casedocumentservice.repository.statement;

/**
 * The enum Get unregistered plans statement.
 */
public enum GetUnregisteredPlansStatement {
    ;
    /**
     * The constant GET_UNREGISTERED_PLANS.
     */
    public static final String GET_UNREGISTERED_PLANS = """
                                
                SELECT DISTINCT otherpacket.PACKET_ID,
                dlg.DLG_ID,
                dlg.FMT_DLG_NUM,
                ppurpose.CODE_DESC AS DOCUMENT_NAME
                FROM DEALING dlg
                INNER JOIN FOLIO_DEALING fd ON fd.DLG_ID = dlg.DLG_ID
                INNER JOIN DEALING_CODE dlgcode ON dlgcode.DLG_TYPE = dlg.DLG_TYPE
                LEFT JOIN REGPLAN rp ON rp.REGPLAN_ID = dlg.REGPLAN_ID
                LEFT JOIN CODE_TYPE_MEMBER ppurpose ON ppurpose.CODE_TYPE_NUM = 8 
                AND ppurpose.CODE_VALUE = rp.plan_purpose
                LEFT JOIN PACKET otherpacket ON otherpacket.DLG_ID = dlg.REGN_CASE_DLG_ID
                WHERE dlg.DLG_STATUS IN (SELECT GENERIC_CODE FROM LIST_MEMBER WHERE LIST_NUM = 417)
                AND dlg.DLG_TYPE IN (SELECT GENERIC_CODE FROM LIST_MEMBER WHERE LIST_NUM 
                IN (560, 762)) -- = [Unregistered plans] - DP, SP, PE, PP
                AND fd.FOL_REC_ID IN (SELECT DISTINCT FOL_REC_ID FROM FOLIO_DEALING, DEALING 
                WHERE DEALING.DLG_ID = FOLIO_DEALING.DLG_ID
                AND DEALING.REGN_CASE_DLG_ID = (SELECT DLG_ID FROM PACKET p WHERE p.PACKET_ID = :packetId) )
                AND dlg.REGN_CASE_DLG_ID != (SELECT DLG_ID FROM PACKET p WHERE p.PACKET_ID = :packetId)
                AND (otherpacket.PACKET_ID IS NULL OR otherpacket.PACKET_ACTIVE_SIG = 'Y')

                """;

}
