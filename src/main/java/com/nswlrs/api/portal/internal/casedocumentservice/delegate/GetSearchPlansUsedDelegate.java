package com.nswlrs.api.portal.internal.casedocumentservice.delegate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.nswlrs.api.common.commonservice.capi.facade.WebApiFacade;
import com.nswlrs.api.common.commonservice.capi.facade.WebApiRequest;
import com.nswlrs.api.common.commonservice.capi.model.ApiResultPages;
import com.nswlrs.api.portal.internal.casedocumentservice.model.SearchPlanUsed;
import com.nswlrs.api.portal.internal.casedocumentservice.repository.GetSearchPlansUsedJDBCRepository;
import com.nswlrs.api.portal.internal.casedocumentservice.services.input.SearchPlansUsedRequest;
import com.nswlrs.api.portal.internal.casedocumentservice.services.output.GetSearchPlansUsedResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * The type Get search plans used delegate.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class GetSearchPlansUsedDelegate extends CAPIConfig {

    private final GetSearchPlansUsedJDBCRepository jdbcRepository;
    private final WebApiFacade webApiFacade;

    /**
     * Gets search plans used.
     *
     * @param request the request
     * @return the search plans used
     */
    public GetSearchPlansUsedResponse getSearchPlansUsed(final SearchPlansUsedRequest request) {
        //TODO move constants to common lib and yml once all c-api done
        log.info("delegate getSearchPlansUsed: {}", request);

        SearchPlanUsed planUsed = SearchPlanUsed.builder().build();
        planUsed = planUsed.withPlanNumber(request.getSearchPlanUsed().getPlanNumber());
        List<SearchPlanUsed> searchplansUsed = new ArrayList<>();
        GetSearchPlansUsedResponse getSearchPlansUsedResponse = GetSearchPlansUsedResponse.builder().searchPlansUsed(searchplansUsed).build();

        String[] splitPlanNumberAlphaNumeric;
        String apiRequest;
        try {
            splitPlanNumberAlphaNumeric = request.getSearchPlanUsed().getPlanNumber().split("(?<=\\D)(?=\\d)");
            apiRequest = "DS|" + splitPlanNumberAlphaNumeric[0] + "|" + splitPlanNumberAlphaNumeric[1];
        } catch (ArrayIndexOutOfBoundsException exception) {
            planUsed = planUsed.withImageAvailable("N").withResponseErrorCode("DOC_DATA_INPUT_SCH_PLN_USED")
                        .withResponseError("Please ensure plan number is alphanumeric");
            searchplansUsed.add(planUsed);
            log.error("c-api error: {}", exception);
            return getSearchPlansUsedResponse;
        }

        ApiResultPages data;
        WebApiRequest webApiRequest = new WebApiRequest(CLIENT_CODE, PORT_NUMBER, REMOTE_USER, apiRequest);
        try {
            data = webApiFacade.runProcess(webApiRequest);//step 1 create the c-api request
        } catch (Exception exception) {
            planUsed = planUsed.withImageAvailable("N").withResponseErrorCode("DOC_DATA_ERR_SCH_PLN_USED_FROM_C_API")
                        .withResponseError(StringUtils.substringAfter(exception.getMessage(), ":").trim());
            searchplansUsed.add(planUsed);
            log.error("c-api error: {}", exception);
            return getSearchPlansUsedResponse;
        }

        String[] subTypes;
        int totalRows;
        log.info("delegate data: {}", data);
        if (ObjectUtils.isNotEmpty(data.getPages())) {//step 2 extract the subTypes from c-api
            totalRows = Integer.parseInt(data.getPages().get(0).getLines().get(1));
            subTypes = new String[totalRows];
            for (int row = 0, subtypeInitialPosition = 2, subTypeFixedSubsequentPosition = 7, subTypeCurrentPosition = 0; row < totalRows; row++) {
                if (row == 0) {
                    subTypeCurrentPosition = subtypeInitialPosition;
                    subTypes[row] = data.getPages().get(0).getLines().get(subTypeCurrentPosition);//0 th row  subTypes in 2nd position
                } else if (row == 1) {
                    subTypeCurrentPosition = subtypeInitialPosition + subTypeFixedSubsequentPosition;
                    subTypes[row] = data.getPages().get(0).getLines().get(subTypeCurrentPosition); //1 th row  subTypes in 9th  position
                } else {
                    subTypeCurrentPosition = subTypeCurrentPosition + subTypeFixedSubsequentPosition;//subsequent rows the  subTypes position is as 9+7=16, 16+7=23, 23+7=30........
                    subTypes[row] = data.getPages().get(0).getLines().get(subTypeCurrentPosition);
                }
            }
        } else {
            planUsed = planUsed.withImageAvailable("N").withResponseError("Plan not found").withResponseErrorCode("DOC_DATA_ERR_SCH_PLN_USED_C_API_PLN_!_FOUND");
            searchplansUsed.add(planUsed);
            return getSearchPlansUsedResponse;
        }

        if (ObjectUtils.isNotEmpty(subTypes)) {
            log.info("delegate subTypes without skip: {}", Arrays.toString(subTypes));
            List<String> subTypesSkipListFiltered = Arrays.stream(subTypes).filter(subType -> !skipSubTypes.contains(subType)).collect(Collectors.toList());//step 3 filter subtypes
            log.info("delegate subTypes after Skip : {}", subTypesSkipListFiltered);

            String subTypeName;
            String packetImageId;
            for (String subType : subTypesSkipListFiltered) {
                //step 4 ..
                subTypeName = jdbcRepository.getSubTypeName(subType);
                if (StringUtils.isNotEmpty(subTypeName)) {
                    planUsed = planUsed.withSubtypeName(subTypeName);
                }

                if ("P".equalsIgnoreCase(subType)) {
                    planUsed = planUsed.withImageCategory("Plan");

                } else if ("B".equalsIgnoreCase(subType)) {
                    planUsed = planUsed.withImageCategory("88b");
                } else {
                    planUsed = planUsed.withImageCategory("Other");
                }

                // Check the PACKET_IMAGE table for a matching record
                packetImageId = jdbcRepository.checkRecordExists(request, subType);
                if (StringUtils.isNotEmpty(packetImageId)) {
                    planUsed = planUsed.withAlreadyInPacket("Y");
                } else {
                    planUsed = planUsed.withAlreadyInPacket("N");
                }
                planUsed = planUsed.withImageSubtype(subType);
                log.info("delegate before adding to searchplansUsed : {}", searchplansUsed);
                searchplansUsed.add(planUsed);
                log.info("delegate after adding to searchplansUsed : {}", searchplansUsed);

            }
        } else {
            planUsed = planUsed.withImageAvailable("N");
            searchplansUsed.add(planUsed);

        }

        //STEP 6 return response

        return getSearchPlansUsedResponse;
    }

    /**
     * The constant skipSubTypes.
     */
    public static final List<String> skipSubTypes = List.of("A", "K", "L", "S", "W", "X");

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
