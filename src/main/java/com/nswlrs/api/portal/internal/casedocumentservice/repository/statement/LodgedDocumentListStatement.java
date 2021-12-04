package com.nswlrs.api.portal.internal.casedocumentservice.repository.statement;

/**
 * The enum Lodged document list statement.
 */
public enum LodgedDocumentListStatement {
    ;

    /**
     * The constant GET_LODGED_DOCUMENT_LIST.
     */
    public static final String GET_LODGED_DOCUMENT_LIST = """
                SELECT dlg.FMT_DLG_NUM, 
                CASE WHEN (dlg.DLG_TYPE = 'PA' AND dlg.PA_CAT IS NOT NULL) THEN pacat.CODE_DESC ELSE dlgcode.DESCRIPTION END AS DEALING_TYPE, 
                di.DEALING_IMAGE_ID, 
                di.IMAGE_TYPE, 
                doc_image_type.CODE_DESC AS IMAGE_TYPE_DESC,
                di.ADDED_DATE, 
                di.VERSION, 
                di.SUPERSEDED, 
                dlg.REGN_SEQ_NUM, 
                doc_image_type.CODE_VALUE, 
                di.PATH 
                FROM DEALING_IMAGE di 
                INNER JOIN DEALING dlg ON dlg.DLG_ID = di.DLG_ID 
                LEFT JOIN DEALING_CODE dlgcode ON dlgcode.DLG_TYPE = dlg.DLG_TYPE 
                LEFT JOIN CODE_TYPE_MEMBER doc_image_type ON doc_image_type.CODE_VALUE = di.IMAGE_TYPE AND doc_image_type.CODE_TYPE_NUM = 117
                LEFT JOIN CODE_TYPE_MEMBER pacat ON pacat.CODE_TYPE_NUM = 10 AND pacat.CODE_VALUE = dlg.PA_CAT
                WHERE dlg.REGN_CASE_DLG_ID = (SELECT DLG_ID FROM PACKET p WHERE p.PACKET_ID = :packetId) 
                AND di.ACTIVE_SIG = 'Y' 
                ORDER BY di.SUPERSEDED DESC, dlg.REGN_SEQ_NUM ASC, to_number(doc_image_type.CODE_VALUE) ASC 
                """;
}
