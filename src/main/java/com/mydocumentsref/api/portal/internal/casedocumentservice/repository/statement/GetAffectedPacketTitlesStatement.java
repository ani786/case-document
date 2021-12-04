package com.mydocumentsref.api.portal.internal.casedocumentservice.repository.statement;

/**
 * The enum Get affected packet titles statement.
 */
public enum GetAffectedPacketTitlesStatement {
    ;
    /**
     * The constant GET_PREVIOUS_VER_LODGED_DOC_FROM_FOLIO_PARCEL.
     */
    public static final String GET_PREVIOUS_VER_LODGED_DOC_FROM_FOLIO_PARCEL = """
                SELECT DISTINCT A."title_ref", A.fol_rec_id, A.SEARCH_AVAILABLE FROM
                (SELECT decode(auto_manual_sig, 'M', vf_vol_num || '-' || vf_fol_num,
                decode(consol_sig, 'Y', decode(fol_id_suffix, null, vf_vol_num || '-' || vf_fol_num,
                'AC'||substr(p.plan_num, 1, length(p.plan_num)-3) ||'-' ||ltrim(substr(p.plan_num, length(p.plan_num)-2, 3),'0')),
                decode(p.plan_type, 'AL', 'WAL' || p.plan_num,
                replace(replace(p.lot_Num||'/'||p.section_Num||'/'||p.plan_type||p.plan_num,'/ '),'DP') )))
                || fol_id_suffix "title_ref",
                f.fol_rec_id,
                p.plan_type, p.plan_num, p.section_num, p.lot_num, fol_id_suffix, vf_vol_num, vf_fol_num,
                decode(auto_manual_sig, 'M', 'N', 'Y') AS SEARCH_AVAILABLE
                from parcel p, folio_parcel fp, folio f,
                folio_dealing fd, dealing d
                where fd.dlg_id = d.dlg_id
                and f.fol_rec_id = fd.fol_rec_id
                and fp.fol_rec_id(+) = f.fol_rec_id
                and p.parcel_id(+) = fp.parcel_id
                AND f.fol_status != '5'
                and d.REGN_CASE_DLG_ID = (SELECT pkt.DLG_ID FROM PACKET pkt WHERE pkt.PACKET_ID = :packetId)
                ORDER BY p.plan_type, p.plan_num, p.section_num, p.lot_num, fol_id_suffix, vf_vol_num, vf_fol_num) A
                                """;

    /**
     * The constant GET_DEALING_FROM_OSREF_DOC.
     */
    public static final String GET_DEALING_FROM_OSREF_DOC = """
                SELECT DISTINCT OS_REF AS TITLE_REF, NULL AS FOL_REC_ID, 'N' AS SEARCH_AVAILABLE
                FROM DEALING_OSREF dos
                INNER JOIN DEALING d ON d.DLG_ID = dos.DLG_ID
                WHERE d.REGN_CASE_DLG_ID = (SELECT pkt.DLG_ID FROM PACKET pkt WHERE pkt.PACKET_ID = :packetId)
                ORDER BY OS_REF
                """;

}
