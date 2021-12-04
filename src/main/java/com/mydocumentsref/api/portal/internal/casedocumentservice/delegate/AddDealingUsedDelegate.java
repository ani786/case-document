package com.mydocumentsref.api.portal.internal.casedocumentservice.delegate;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.mydocumentsref.api.common.commonservice.capi.facade.WebApiFacade;
import com.mydocumentsref.api.common.commonservice.capi.facade.WebApiRequest;
import com.mydocumentsref.api.common.commonservice.capi.model.ApiResultPages;
import com.mydocumentsref.api.common.commonservice.util.Constants;
import com.mydocumentsref.api.common.commonservice.util.DateUtils;
import com.mydocumentsref.api.portal.internal.casedocumentservice.model.PacketImage;
import com.mydocumentsref.api.portal.internal.casedocumentservice.repository.AddDealingUsedJDBCRepository;
import com.mydocumentsref.api.portal.internal.casedocumentservice.repository.GetFormattedTitleSearchJDBCRepository;
import com.mydocumentsref.api.portal.internal.casedocumentservice.services.input.AddDealingUsedRequest;
import com.mydocumentsref.api.portal.internal.casedocumentservice.services.output.AddDealingUsedResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * The type Add dealing used delegate.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AddDealingUsedDelegate extends CAPIConfig {

    private final GetFormattedTitleSearchJDBCRepository getFormattedTitleSearchJDBCRepository;
    private final AddDealingUsedJDBCRepository jdbcRepository;
    private final WebApiFacade webApiFacade;

    /**
     * Add dealing used add dealing used response.
     *
     * @param request the request
     * @return the add dealing used response
     */
    public AddDealingUsedResponse addDealingUsed(final AddDealingUsedRequest request) {
        //TODO move constants to common lib and yml once all c-api done
        log.info("delegate addDealingUsed: {}", request);

        AddDealingUsedResponse addDealingUsedResponse =
                    AddDealingUsedResponse.builder().userCode(request.getUserCode())
                                .timestamp(DateUtils
                                            .convertTimestampToString(Timestamp.valueOf(LocalDateTime.now()))).build();

        List<PacketImage> recordExists =
                    jdbcRepository.checkRecordExists(request);  // STEP 1 check record exists

        log.info("delegate addDealingUsed recordExists: {}", recordExists);

        if (ObjectUtils.isNotEmpty(recordExists)) {
            addDealingUsedResponse = addDealingUsedResponse.withResponseMessageDescription("Record Exists")
                        .withResponseMessage(Constants.SUCCESS).withPacketImages(recordExists);
            return addDealingUsedResponse;

        } else {
            addDealingUsedResponse = addDealingUsedResponse.withResponseMessageDescription("Record Does not Exist")
                        .withResponseMessage(Constants.SUCCESS);
        }

        //STEP 2 C-API DIMS call 1

        String apiRequest = "DS|DL|" + request.getAddDealingUsed().getFmtDlgNum();
        WebApiRequest webApiRequest = new WebApiRequest(CLIENT_CODE, PORT_NUMBER, REMOTE_USER, apiRequest);
        ApiResultPages data;
        try {
            data = webApiFacade.runProcess(webApiRequest);
        } catch (Exception exception) {
            addDealingUsedResponse = addDealingUsedResponse.withResponseErrorCode("DOC_DATA_ERR_ADD_DEAL_USED_FROM_C_API_01_DS")
                        .withResponseError(StringUtils.substringAfter(exception.getMessage(), ":").trim());
            return addDealingUsedResponse;
        }

        //response
        addDealingUsedResponse = cAPIResponse(data, addDealingUsedResponse, "C_API_01_DS_DL");
        if (StringUtils.isNotEmpty(addDealingUsedResponse.getResponseErrorCode())) {
            return addDealingUsedResponse;
        }

        String subType = null;
        if (ObjectUtils.isNotEmpty(data.getPages().get(0))) {
            subType = data.getPages().get(0).getLines().get(2);
        }
        log.info("delegate Call the DIIMS API to apiResultPages DS|DL| :{}", data.getPages());

        String ltoSection = getFormattedTitleSearchJDBCRepository
                    .getLTOsection(request.getUserCode());  // STEP 3 get ltoSection

        log.info("delegate Get Formatted Title search ltoSection: {}", ltoSection);
        String clientRef = "lrs:" + ltoSection + "-" + request.getUserCode(); // STEP 2 construct clientRef

        //STEP 4 C-API Request
        apiRequest = "DR|" + clientRef + "|DL|" + request.getAddDealingUsed().getFmtDlgNum() + "|||F||";
        webApiRequest.setApiRequest(apiRequest);
        try {
            data = webApiFacade.runProcess(webApiRequest);
        } catch (Exception exception) {
            addDealingUsedResponse = addDealingUsedResponse.withResponseErrorCode("DOC_DATA_ERR_ADD_DEAL_USED_FROM_C_API_02_DR")
                        .withResponseError(StringUtils.substringAfter(exception.getMessage(), ":").trim());
            return addDealingUsedResponse;
        }

        //response
        addDealingUsedResponse = cAPIResponse(data, addDealingUsedResponse, "C_API_02_DR_DL");
        if (StringUtils.isNotEmpty(addDealingUsedResponse.getResponseErrorCode())) {
            return addDealingUsedResponse;
        }

        log.info("delegate Call the DIIMS API to apiResultPages DR|DL :{}", data.getPages());

        int updateResultSet;
        if (!(data.getPages().isEmpty())) {
            updateResultSet = jdbcRepository
                        .insertRecordIntoPacketImageTbl(request, data.getPages().get(0).getLines().get(1), subType);  // STEP 5
        } else {

            addDealingUsedResponse = cAPIResponse(data, addDealingUsedResponse, "C_API_03_DR_DL_PATH_NOT_FOUND");
            return addDealingUsedResponse;
        }

        addDealingUsedResponse = addDealingUsedResponse.withRecordsInserted(updateResultSet); //STEP 6

        updateResultSet = jdbcRepository.updateTheLasModifiedDate(request);  // STEP 7
        addDealingUsedResponse.withRecordUpdated(updateResultSet);

        long numberOfCalls = 1L;
        while (numberOfCalls <= 18L && ObjectUtils.isEmpty(addDealingUsedResponse.getPacketImages())) {
            addDealingUsedResponse =
                        addDealingUsedResponse.withPacketImages(jdbcRepository.pollDetailsOfRecordsInserted(request));
            try {
                TimeUnit.SECONDS.sleep(05);
                log.info("delegate polling the details of records inserted: numberOfCalls {}", numberOfCalls);
                numberOfCalls++;
            } catch (InterruptedException e) {
                log.error("delegate polling the details of records inserted: Interrupted Exception {}", e);
            }
        }

        if (ObjectUtils.isEmpty(addDealingUsedResponse.getPacketImages())) {
            addDealingUsedResponse = addDealingUsedResponse
                        .withResponseError("A timeout occurred while retrieving the document. Please" +
                                    " refresh the packet in a few minutes or try downloading the document again.")
                        .withResponseErrorCode("DOC_DATA_ERR_ADD_DEAL_USED_DB_IMG_POLL");
        } else {
            addDealingUsedResponse = addDealingUsedResponse.withResponseMessageDescription("Record Created")
                        .withResponseMessage(Constants.SUCCESS).withPacketImages(recordExists).withPacketImages(addDealingUsedResponse.getPacketImages());
        }

        return addDealingUsedResponse;
    }

    private AddDealingUsedResponse cAPIResponse(ApiResultPages data, AddDealingUsedResponse addDealingUsedResponse,
                                                String errorCodeSuffix) {
        log.info("delegate Call the DIIMS API to check the file exists and bring the Type & Subtype back :{}", data);
        if ((data.getPages().isEmpty())) {
            return addDealingUsedResponse.withResponseError("Dealing not found")
                        .withResponseErrorCode("DOC_DATA_ERR_ADD_DEAL_USED_" + errorCodeSuffix);

        }
        return addDealingUsedResponse;

    }

}
