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
import com.nswlrs.api.portal.internal.casedocumentservice.repository.AddManualTitleJDBCRepository;
import com.nswlrs.api.portal.internal.casedocumentservice.repository.GetFormattedTitleSearchJDBCRepository;
import com.nswlrs.api.portal.internal.casedocumentservice.services.input.AddManualTitleRequest;
import com.nswlrs.api.portal.internal.casedocumentservice.services.output.AddManualTitleResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * The type Add manual title delegate.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AddManualTitleDelegate extends CAPIConfig {

    private final GetFormattedTitleSearchJDBCRepository getFormattedTitleSearchJDBCRepository;
    private final AddManualTitleJDBCRepository jdbcRepository;
    private final WebApiFacade webApiFacade;

    /**
     * Add manual title add manual title response.
     *
     * @param request the request
     * @return the add manual title response
     */
    public AddManualTitleResponse addManualTitle(final AddManualTitleRequest request) {
        //TODO move constants to common lib and yml once all c-api done
        log.info("delegate addManualTitle: {}", request);

        AddManualTitleResponse addManualTitleResponse =
                    AddManualTitleResponse.builder().userCode(request.getUserCode())
                                .timestamp(DateUtils
                                            .convertTimestampToString(Timestamp.valueOf(LocalDateTime.now()))).build();

        List<PacketImage> recordExists =
                    jdbcRepository.checkRecordExists(request);  // STEP 1 check record exists

        log.info("delegate addManualTitle recordExists: {}", recordExists);

        if (ObjectUtils.isNotEmpty(recordExists)) {
            addManualTitleResponse = addManualTitleResponse.withResponseMessageDescription("Record Exists")
                        .withResponseMessage(Constants.SUCCESS).withPacketImages(recordExists);
            return addManualTitleResponse;

        } else {
            addManualTitleResponse = addManualTitleResponse.withResponseMessageDescription("Record Does not Exist")
                        .withResponseMessage(Constants.SUCCESS);
        }

        //Call DIMS
        //STEP 2 C-API Request
        String apiRequest = "DS|CT|" + request.getAddManualTitle().getTitleRef();
        WebApiRequest webApiRequest = new WebApiRequest(CLIENT_CODE, PORT_NUMBER, REMOTE_USER, apiRequest);
        ApiResultPages data;
        try {
            data = webApiFacade.runProcess(webApiRequest);
        } catch (Exception exception) {
            addManualTitleResponse = addManualTitleResponse.withResponseErrorCode("DOC_DATA_ERR_ADD_MANUAL_TTL_FROM_C_API_01_DS")
                        .withResponseError(StringUtils.substringAfter(exception.getMessage(), ":").trim());
            return addManualTitleResponse;
        }

        //response
        addManualTitleResponse = cAPIResponse(data, addManualTitleResponse, "C_API_01_DS_CT");
        if (StringUtils.isNotEmpty(addManualTitleResponse.getResponseErrorCode())) {
            return addManualTitleResponse;
        }
        log.info("delegate Call the DIIMS API to apiResultPages DS|CT| :{}", data.getPages());

        String ltoSection = getFormattedTitleSearchJDBCRepository
                    .getLTOsection(request.getUserCode());  // STEP 3 get ltoSection

        log.info("delegate Get Formatted Title search ltoSection: {}", ltoSection);
        String clientRef = "lrs:" + ltoSection + "-" + request.getUserCode(); // STEP 2 construct clientRef

        //STEP 4 C-API Request
        apiRequest = "DR|" + clientRef + "|CT|" + request.getAddManualTitle().getTitleRef() + "|||F||";
        webApiRequest.setApiRequest(apiRequest);
        try {
            data = webApiFacade.runProcess(webApiRequest);
        } catch (Exception exception) {
            addManualTitleResponse = addManualTitleResponse.withResponseErrorCode("DOC_DATA_ERR_ADD_MANUAL_TTL_FROM_C_API_02_DR")
                        .withResponseError(StringUtils.substringAfter(exception.getMessage(), ":").trim());
            return addManualTitleResponse;
        }

        //response
        addManualTitleResponse = cAPIResponse(data, addManualTitleResponse, "C_API_02_DR_CT");
        if (StringUtils.isNotEmpty(addManualTitleResponse.getResponseErrorCode())) {
            return addManualTitleResponse;
        }

        log.info("delegate Call the DIIMS API to apiResultPages DR|CT :{}", data.getPages());

        int updateResultSet;
        if (!(data.getPages().isEmpty())) {
            updateResultSet = jdbcRepository
                        .insertRecordIntoPacketImageTbl(request, data.getPages().get(0).getLines().get(1));  // STEP 5
        } else {

            addManualTitleResponse = cAPIResponse(data, addManualTitleResponse, "C_API_03_DR_CT_PATH_NOT_FOUND");
            return addManualTitleResponse;
        }

        addManualTitleResponse = addManualTitleResponse.withRecordsInserted(updateResultSet); //STEP 6

        updateResultSet = jdbcRepository.updateTheLasModifiedDate(request);  // STEP 7
        addManualTitleResponse.withRecordUpdated(updateResultSet);

        long numberOfCalls = 1L;
        while (numberOfCalls <= 18L && ObjectUtils.isEmpty(addManualTitleResponse.getPacketImages())) {
            addManualTitleResponse =
                        addManualTitleResponse.withPacketImages(jdbcRepository.pollDetailsOfRecordsInserted(request));
            try {
                TimeUnit.SECONDS.sleep(05);
                log.info("delegate polling the details of records inserted: numberOfCalls {}", numberOfCalls);
                numberOfCalls++;
            } catch (InterruptedException e) {
                log.error("delegate polling the details of records inserted: Interrupted Exception {}", e);
            }
        }

        if (ObjectUtils.isEmpty(addManualTitleResponse.getPacketImages())) {
            addManualTitleResponse = addManualTitleResponse
                        .withResponseError("A timeout occurred while retrieving the document. Please" +
                                    " refresh the packet in a few minutes or try downloading the document again.")
                        .withResponseErrorCode("DOC_DATA_ERR_ADD_MANUAL_TTL_DB_IMG_POLL");
        } else {
            addManualTitleResponse = addManualTitleResponse.withResponseMessageDescription("Record Created")
                        .withResponseMessage(Constants.SUCCESS).withPacketImages(recordExists).withPacketImages(addManualTitleResponse.getPacketImages());
        }

        return addManualTitleResponse;
    }

    private AddManualTitleResponse cAPIResponse(ApiResultPages data, AddManualTitleResponse addManualTitleResponse,
                                                String errorCodeSuffix) {
        log.info("delegate Call the DIIMS API to check the file exists and bring the Type & Subtype back :{}", data);
        if ((data.getPages().isEmpty())) {
            return addManualTitleResponse.withResponseError("Manual title not found")
                        .withResponseErrorCode("DOC_DATA_ERR_ADD_MANUAL_TTL_" + errorCodeSuffix);

        }
        return addManualTitleResponse;

    }

}
