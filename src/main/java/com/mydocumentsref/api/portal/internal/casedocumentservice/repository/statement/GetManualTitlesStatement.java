package com.mydocumentsref.api.portal.internal.casedocumentservice.repository.statement;

/**
 * The enum Get manual titles statement.
 */
public enum GetManualTitlesStatement {
    ;
    /**
     * The constant GET_MANUAL_TITLES.
     */
    public static final String GET_MANUAL_TITLES = """

                select distinct TITLE_REF AS DISPLAY_NAME, pim.PACKET_IMAGE_ID, pim.PATH
                from folio_history fh, folio_Dealing fd, dealing d, packet_image pim
                where fh.fol_Rec_id = fd.fol_rec_id
                and d.dlg_id = fd.dlg_id
                and d.REGN_CASE_DLG_ID = (SELECT pkt.DLG_ID FROM PACKET pkt WHERE pkt.PACKET_ID = :packetId)
                and fh.transn_type in (5, 6)
                and title_ref like '%-%'
                and pim.image_name(+) = TITLE_REF
                and pim.PACKET_IMAGE_GROUP(+) = 'TITLE'
                and pim.ACTIVE_SIG(+) = 'Y'
                union
                select vf_vol_num || '-' || vf_fol_num || vf_suffix AS DISPLAY_NAME, pim.PACKET_IMAGE_ID, pim.PATH
                from folio f, folio_Dealing fd, dealing d, packet_image pim
                where f.fol_Rec_id = fd.fol_rec_id
                and d.dlg_id = fd.dlg_id
                and d.REGN_CASE_DLG_ID = (SELECT pkt.DLG_ID FROM PACKET pkt WHERE pkt.PACKET_ID = :packetId)
                and vf_vol_num is not NULL
                and pim.image_name(+) = vf_vol_num || '-' || vf_fol_num || vf_suffix
                and pim.PACKET_IMAGE_GROUP(+) = 'TITLE'
                and pim.ACTIVE_SIG(+) = 'Y'
                union
                select pim.IMAGE_NAME AS DISPLAY_NAME, pim.PACKET_IMAGE_ID, pim.PATH
                from packet_image pim
                where pim.PACKET_IMAGE_GROUP = 'TITLE'
                and pim.PACKET_ID = :packetId
                ORDER BY DISPLAY_NAME ASC

                """;

}
