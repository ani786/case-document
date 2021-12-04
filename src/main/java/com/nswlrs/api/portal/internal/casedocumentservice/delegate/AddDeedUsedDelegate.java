package com.nswlrs.api.portal.internal.casedocumentservice.delegate;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.nswlrs.api.common.commonservice.capi.facade.WebApiFacade;
import com.nswlrs.api.common.commonservice.capi.facade.WebApiRequest;
import com.nswlrs.api.common.commonservice.capi.model.ApiResultPages;
import com.nswlrs.api.common.commonservice.util.Constants;
import com.nswlrs.api.common.commonservice.util.DateUtils;
import com.nswlrs.api.portal.internal.casedocumentservice.model.PacketImage;
import com.nswlrs.api.portal.internal.casedocumentservice.repository.AddDeedUsedJDBCRepository;
import com.nswlrs.api.portal.internal.casedocumentservice.repository.GetFormattedTitleSearchJDBCRepository;
import com.nswlrs.api.portal.internal.casedocumentservice.services.input.AddDeedUsedRequest;
import com.nswlrs.api.portal.internal.casedocumentservice.services.output.AddDeedUsedResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * The type Add deed used delegate.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AddDeedUsedDelegate extends CAPIConfig {

    private final GetFormattedTitleSearchJDBCRepository getFormattedTitleSearchJDBCRepository;
    private final AddDeedUsedJDBCRepository jdbcRepository;
    private final WebApiFacade webApiFacade;

    /**
     * Add deed used add deed used response.
     *
     * @param request the request
     * @return the add deed used response
     */
    public AddDeedUsedResponse addDeedUsed(final AddDeedUsedRequest request) {
        //TODO move constants to common lib and yml once all c-api done
        log.info("delegate add DeedUsed: {}", request);

        AddDeedUsedResponse addDeedUsedResponse =
                    AddDeedUsedResponse.builder().userCode(request.getUserCode())
                                .timestamp(DateUtils
                                            .convertTimestampToString(Timestamp.valueOf(LocalDateTime.now()))).build();

        List<PacketImage> recordExists =
                    jdbcRepository.checkRecordExists(request);  // STEP 1 check record exists

        log.info("delegate add DeedUsed recordExists: {}", recordExists);

        if (ObjectUtils.isNotEmpty(recordExists)) {
            addDeedUsedResponse = addDeedUsedResponse.withResponseMessageDescription("Record Exists")
                        .withResponseMessage(Constants.SUCCESS).withPacketImages(recordExists);
            return addDeedUsedResponse;

        } else {
            addDeedUsedResponse = addDeedUsedResponse.withResponseMessageDescription("Record Does not Exist")
                        .withResponseMessage(Constants.SUCCESS);
        }

        //Call DIMS STEP 2 C-API Request

        String apiRequest = "DS|BK|" + StringUtils.substringBetween(request.getAddDeedUsed().getEncumNum(), "K", "N") + "-" +
                    StringUtils.substringAfter(request.getAddDeedUsed().getEncumNum(), "O");
        WebApiRequest webApiRequest = new WebApiRequest(CLIENT_CODE, PORT_NUMBER, REMOTE_USER, apiRequest);
        ApiResultPages data;
        try {
            data = webApiFacade.runProcess(webApiRequest);
        } catch (Exception exception) {
            addDeedUsedResponse = addDeedUsedResponse.withResponseErrorCode("DOC_DATA_ERR_ADD_DEED_USED_FROM_C_API_01_DS")
                        .withResponseError(StringUtils.substringAfter(exception.getMessage(), ":").trim());
            return addDeedUsedResponse;
        }

        //response
        addDeedUsedResponse = cAPIResponse(data, addDeedUsedResponse, "C_API_01_DS_BK");
        if (StringUtils.isNotEmpty(addDeedUsedResponse.getResponseErrorCode())) {
            return addDeedUsedResponse;
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
        apiRequest = "DR|" + clientRef + "|CT|" + StringUtils.substringBetween(request.getAddDeedUsed().getEncumNum(), "K", "N") + "-" +
                    StringUtils.substringAfter(request.getAddDeedUsed().getEncumNum(), "O") + "|||F||";
        webApiRequest.setApiRequest(apiRequest);
        try {
            data = webApiFacade.runProcess(webApiRequest);
        } catch (Exception exception) {
            addDeedUsedResponse = addDeedUsedResponse.withResponseErrorCode("DOC_DATA_ERR_ADD_DEED_USED_FROM_C_API_02_DR")
                        .withResponseError(StringUtils.substringAfter(exception.getMessage(), ":").trim());
            return addDeedUsedResponse;
        }

        //response
        addDeedUsedResponse = cAPIResponse(data, addDeedUsedResponse, "C_API_02_DR_CT");
        if (StringUtils.isNotEmpty(addDeedUsedResponse.getResponseErrorCode())) {
            return addDeedUsedResponse;
        }

        log.info("delegate Call the DIIMS API to apiResultPages DR|DL :{}", data.getPages());

        int updateResultSet;
        if (!(data.getPages().isEmpty())) {
            updateResultSet = jdbcRepository
                        .insertRecordIntoPacketImageTbl(request, data.getPages().get(0).getLines().get(1), subType);  // STEP 5
            //TransactionAspectSupport.currentTransactionStatus().createSavepoint();
        } else {
            addDeedUsedResponse = cAPIResponse(data, addDeedUsedResponse, "C_API_03_DR_CT_PATH_NOT_FOUND");
            return addDeedUsedResponse;
        }

        addDeedUsedResponse = addDeedUsedResponse.withRecordsInserted(updateResultSet); //STEP 6

        updateResultSet = jdbcRepository.updateTheLasModifiedDate(request);  // STEP 7
        addDeedUsedResponse.withRecordUpdated(updateResultSet);

        long numberOfCalls = 1L;
        while (numberOfCalls <= 18L && ObjectUtils.isEmpty(addDeedUsedResponse.getPacketImages())) {
            addDeedUsedResponse =
                        addDeedUsedResponse.withPacketImages(jdbcRepository.pollDetailsOfRecordsInserted(request));
            try {
                TimeUnit.SECONDS.sleep(05);
                log.info("delegate polling the details of records inserted: numberOfCalls {}", numberOfCalls);
                numberOfCalls++;
            } catch (InterruptedException e) {
                log.error("delegate polling the details of records inserted: Interrupted Exception {}", e);
            }
        }

        if (ObjectUtils.isEmpty(addDeedUsedResponse.getPacketImages())) {
            addDeedUsedResponse = addDeedUsedResponse
                        .withResponseError("A timeout occurred while retrieving the document. Please" +
                                    " refresh the packet in a few minutes or try downloading the document again.")
                        .withResponseErrorCode("DOC_DATA_ERR_ADD_DEED_USED_DB_IMG_POLL");
        } else {
            addDeedUsedResponse = addDeedUsedResponse.withResponseMessageDescription("Record Created")
                        .withResponseMessage(Constants.SUCCESS).withPacketImages(recordExists).withPacketImages(addDeedUsedResponse.getPacketImages());
        }

        return addDeedUsedResponse;
    }

    private AddDeedUsedResponse cAPIResponse(ApiResultPages data, AddDeedUsedResponse addDeedUsedResponse,
                                             String errorCodeSuffix) {
        log.info("delegate Call the DIIMS API to check the file exists and bring the Type & Subtype back :{}", data);
        if ((data.getPages().isEmpty())) {
            return addDeedUsedResponse.withResponseError("Deed not found")
                        .withResponseErrorCode("DOC_DATA_ERR_ADD_DEED_USED_" + errorCodeSuffix);

        }
        return addDeedUsedResponse;

    }

}
