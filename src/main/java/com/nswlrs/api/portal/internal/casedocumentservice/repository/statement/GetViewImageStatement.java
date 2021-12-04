package com.nswlrs.api.portal.internal.casedocumentservice.repository.statement;

/**
 * The enum Get view image statement.
 */
public enum GetViewImageStatement {
    ;
    /**
     * The constant GET_LTO_SECTION.
     */
    public static final String GET_LTO_SECTION = """
                 SELECT LTO_SECTION FROM USERS WHERE USER_CODE = ?
                """;

    /**
     * The constant GET_PATH_FOR_UNREGISTERED.
     */
    public static final String GET_DEALING_TD_PATH_FOR_UNREGISTERED = """
                SELECT di.DEALING_IMAGE_ID, di.PATH
                FROM DEALING_IMAGE di
                INNER JOIN DEALING dlg ON dlg.DLG_ID = di.DLG_ID
                WHERE dlg.FMT_DLG_NUM = :fmtDlgNum
                AND di.ACTIVE_SIG = 'Y'
                AND di.SUPERSEDED = 'N'
                AND di.IMAGE_TYPE = 20
                """;
}
