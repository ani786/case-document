package com.nswlrs.api.portal.internal.casedocumentservice.repository.statement;

/**
 * The enum Get plans used statement.
 */
public enum GetPlansUsedStatement {
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
    /**
     * The constant GET_PLAN_NUMBER_WITH_ITS_SOURCE.
     */
    public static final String GET_PLAN_NUMBER_WITH_ITS_SOURCE = """
                SELECT distinct n.encum_num AS DISPLAY_NAME, 'ITS' AS SOURCE
                FROM notification n, folio_nfn fn, folio_dealing fd, dealing d
                WHERE n.nfn_id = fn.nfn_id
                and fn.fol_rec_id = fd.fol_ReC_id
                and fd.dlg_id = d.dlg_id
                and (encum_num like 'DP%' or encum_num like 'SP%')
                and d.REGN_CASE_DLG_ID = (SELECT pkt.DLG_ID FROM PACKET pkt WHERE pkt.PACKET_ID = :packetId)
                UNION
                SELECT distinct subn.encum_num AS DISPLAY_NAME, 'ITS' AS SOURCE
                FROM notification n,
                notification subn,
                sub_nfn sn,
                folio_nfn fn, folio_dealing fd, dealing d
                WHERE sn.fol_rec_id = fn.fol_rec_id
                AND sn.nfn_id = fn.nfn_id
                AND subn.nfn_id = sn.snfn_id
                AND n.nfn_id = fn.nfn_id
                and fn.fol_rec_id = fd.fol_ReC_id
                and fd.dlg_id = d.dlg_id
                and (subn.encum_num like 'DP%' or subn.encum_num like 'SP%')
                and d.REGN_CASE_DLG_ID = (SELECT pkt.DLG_ID FROM PACKET pkt WHERE pkt.PACKET_ID = :packetId)
                union
                select DISTINCT pim.IMAGE_NAME AS DISPLAY_NAME, 'USER_ADDED' AS SOURCE
                from packet_image pim
                where pim.PACKET_IMAGE_GROUP = 'PLAN'
                and pim.PACKET_ID = :packetId
                ORDER BY DISPLAY_NAME ASC
                """;
    /**
     * The constant GET_IMAGE_ID_PATH.
     */
    public static final String GET_IMAGE_ID_PATH = """
                SELECT pim.PACKET_IMAGE_ID, pim.PATH
                FROM packet_image pim
                WHERE pim.ACTIVE_SIG = 'Y'
                AND pim.PACKET_IMAGE_GROUP = 'PLAN'
                AND pim.IMAGE_NAME = :planNumber
                AND pim.IMAGE_SUBTYPE = :subType
                AND pim.PACKET_ID = :packetId
                """;
    /**
     * The constant GET_IMAGE_SUB_TYPES.
     */
    public static final String GET_IMAGE_SUB_TYPES = """
                SELECT pim.IMAGE_NAME AS DISPLAY_NAME, st.CODE_VALUE AS IMAGE_SUBTYPE, pim.IMAGE_CATEGORY, st.CODE_DESC AS IMAGE_SUBTYPE_DESC, pim.PACKET_IMAGE_ID, pim.PATH
                FROM PACKET_IMAGE pim
                INNER JOIN CODE_TYPE_MEMBER st ON st.CODE_TYPE_NUM = 42 AND st.CODE_VALUE = pim.IMAGE_SUBTYPE
                WHERE pim.PACKET_IMAGE_GROUP = 'PLAN'
                AND pim.PACKET_ID = :packetId
                AND pim.IMAGE_NAME = :planNumber
                ORDER BY DISPLAY_NAME, st.CODE_VALUE
                """;
}
