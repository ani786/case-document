package com.mydocumentsref.api.portal.internal.casedocumentservice.delegate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;

/**
 * The type Capi config.
 */
public class CAPIConfig {

    /**
     * The Client code.
     */
    @Value("${api.c-api.connection.clientCode}")
    protected String CLIENT_CODE;
    /**
     * The Port number.
     */
    @Value("${api.c-api.connection.port}")
    protected String PORT_NUMBER;
    /**
     * The Remote user.
     */
    @Value("${api.c-api.connection.remoteUser}")
    protected String REMOTE_USER;

    /**
     * The constant skipSubTypes.
     */
    public static final List<String> skipSubTypes = List.of("A", "K", "L", "S", "W", "X");

    /**
     * The constant dlgStatus.
     */
    public static final List<String> dlgStatus =
                List.of("REGISTERED", "REGISTERED - AWAITING FOLIO CREATION", "REGISTERED - AWAITING SUB FOLIO CREATION", "REGISTERED - FOLIOS CREATED",
                            "REGISTERED - NO FOLIOS ISSUED");

    /**
     * The Sub types document map.
     */
    public static Map<String, String> subTypesDocumentMap;

    static {
        subTypesDocumentMap = new HashMap<>();
        subTypesDocumentMap.put("P", "PLAN");
        subTypesDocumentMap.put("B", "SECTION 88B INSTRUMENT");
        subTypesDocumentMap.put("C", "DEVELOPMENT CONTRACT");
        subTypesDocumentMap.put("D", "STRATA PLAN DEVELOPERS BY-LAWS");
        subTypesDocumentMap.put("E", "CL42-3A FORM");
        subTypesDocumentMap.put("F", "PIPELINE FORM");
        subTypesDocumentMap.put("G", "GEOMETRY");
        subTypesDocumentMap.put("M", "MANAGEMENT STATEMENT");
        subTypesDocumentMap.put("R", "SURVEY REPORT");
    }
}
