package com.nswlrs.api.portal.internal.casedocumentservice.repository.statement;

/**
 * The enum Get deeds used statement.
 */
public enum GetDeedsUsedStatement {
    ;
    /**
     * The constant GET_DEEDS_USED.
     */
    public static final String GET_DEEDS_USED = """

                SELECT distinct n.encum_num AS DISPLAY_NAME, pim.PACKET_IMAGE_ID, pim.PATH
                FROM notification n, folio_nfn fn, folio_dealing fd, dealing d, packet_image pim
                WHERE n.nfn_id = fn.nfn_id
                and fn.fol_rec_id = fd.fol_ReC_id
                and fd.dlg_id = d.dlg_id
                and (encum_num like 'BK%') 
                and n.encum_num != ' '
                and pim.image_name(+) = n.encum_num
                and pim.PACKET_IMAGE_GROUP(+) = 'DEED'
                and pim.ACTIVE_SIG(+) = 'Y'
                and d.REGN_CASE_DLG_ID = (SELECT pkt.DLG_ID FROM PACKET pkt WHERE pkt.PACKET_ID = :packetId)
                UNION
                SELECT distinct subn.encum_num AS DISPLAY_NAME, pim.PACKET_IMAGE_ID, pim.PATH
                FROM notification n,
                notification subn,
                sub_nfn sn,
                folio_nfn fn, folio_dealing fd, dealing d, packet_image pim
                WHERE sn.fol_rec_id = fn.fol_rec_id
                AND sn.nfn_id = fn.nfn_id
                AND subn.nfn_id = sn.snfn_id
                AND n.nfn_id = fn.nfn_id
                and fn.fol_rec_id = fd.fol_ReC_id
                and fd.dlg_id = d.dlg_id
                and (subn.encum_num like 'BK%') 
                and subn.encum_num != ' ' 
                and pim.image_name(+) = subn.encum_num
                and pim.PACKET_IMAGE_GROUP(+) = 'DEED'
                and pim.ACTIVE_SIG(+) = 'Y'
                and d.REGN_CASE_DLG_ID = (SELECT pkt.DLG_ID FROM PACKET pkt WHERE pkt.PACKET_ID = :packetId)
                UNION
                select pim.IMAGE_NAME AS DISPLAY_NAME, pim.PACKET_IMAGE_ID, pim.PATH
                from packet_image pim
                where pim.PACKET_IMAGE_GROUP = 'DEED'
                and pim.PACKET_ID = :packetId
                ORDER BY DISPLAY_NAME ASC



                """;

}
