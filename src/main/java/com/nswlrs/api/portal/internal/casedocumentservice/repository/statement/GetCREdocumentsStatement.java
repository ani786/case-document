package com.nswlrs.api.portal.internal.casedocumentservice.repository.statement;

/**
 * The enum Get cr edocuments statement.
 */
public enum GetCREdocumentsStatement {
    ;
    /**
     * The constant GET_CRE.
     */
    public static final String GET_CRE = """

                SELECT DISTINCT decode(consol_sig, 'Y', decode(fol_id_suffix, null, vf_vol_num || '-' || vf_fol_num,
                'AC'||substr(p.plan_num, 1, length(p.plan_num)-3) ||
                '-' ||
                ltrim(substr(p.plan_num, length(p.plan_num)-2, 3),'0')),
                decode(p.plan_type, 'AL', 'WAL' || p.plan_num,
                replace(replace(p.lot_Num||'/'||p.section_Num||'/'||p.plan_type||p.plan_num,'/ '),'DP') ))
                || fol_id_suffix "TITLE_REF",
                f.fol_rec_id,
                p.plan_type, p.plan_num, p.section_num, p.lot_num, fol_id_suffix, vf_vol_num, vf_fol_num, pim.PACKET_IMAGE_ID, pim.PATH
                from parcel p, folio_parcel fp, folio f,
                folio_dealing fd, dealing d, packet_image pim
                where fd.dlg_id = d.dlg_id
                and f.fol_rec_id = fd.fol_rec_id
                and fp.fol_rec_id(+) = f.fol_rec_id
                and p.parcel_id(+) = fp.parcel_id
                and f.fol_status != '5'
                and pim.image_name(+) = decode(consol_sig, 'Y', decode(fol_id_suffix, null, vf_vol_num || '-' || vf_fol_num,
                'AC'||substr(p.plan_num, 1, length(p.plan_num)-3) ||
                '-' ||
                ltrim(substr(p.plan_num, length(p.plan_num)-2, 3),'0')),
                decode(p.plan_type, 'AL', 'WAL' || p.plan_num,
                replace(replace(p.lot_Num||'/'||p.section_Num||'/'||p.plan_type||p.plan_num,'/ '),'DP') ))
                || fol_id_suffix
                and pim.PACKET_IMAGE_GROUP(+) = 'CRE'
                and pim.ACTIVE_SIG(+) = 'Y'
                and decode(auto_manual_sig, 'M', 'N', 'Y') = 'Y'
                and d.REGN_CASE_DLG_ID = (SELECT pkt.DLG_ID FROM PACKET pkt WHERE pkt.PACKET_ID =:packetId)
                UNION
                select pim.IMAGE_NAME AS TITLE_REF, NULL AS fol_rec_id,
                NULL AS plan_type, NULL AS plan_num, NULL AS section_num, NULL AS lot_num, NULL AS fol_id_suffix, NULL AS vf_vol_num, NULL AS vf_fol_num, pim.PACKET_IMAGE_ID, pim.PATH
                from packet_image pim
                where pim.PACKET_IMAGE_GROUP = 'CRE'
                and pim.PACKET_ID =:packetId
                ORDER BY plan_type, plan_num, section_num, lot_num, fol_id_suffix, vf_vol_num, vf_fol_num



                """;

}
