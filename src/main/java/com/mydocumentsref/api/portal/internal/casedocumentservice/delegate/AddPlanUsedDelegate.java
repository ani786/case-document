package com.mydocumentsref.api.portal.internal.casedocumentservice.delegate;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.mydocumentsref.api.common.commonservice.capi.facade.WebApiFacade;
import com.mydocumentsref.api.common.commonservice.capi.facade.WebApiRequest;
import com.mydocumentsref.api.common.commonservice.capi.model.ApiResultPages;
import com.mydocumentsref.api.common.commonservice.util.Constants;
import com.mydocumentsref.api.common.commonservice.util.DateUtils;
import com.mydocumentsref.api.portal.internal.casedocumentservice.model.AddPlansUsed;
import com.mydocumentsref.api.portal.internal.casedocumentservice.model.AddPlansUsedPacketImage;
import com.mydocumentsref.api.portal.internal.casedocumentservice.model.PacketImage;
import com.mydocumentsref.api.portal.internal.casedocumentservice.repository.AddPlanUsedJDBCRepository;
import com.mydocumentsref.api.portal.internal.casedocumentservice.repository.GetFormattedTitleSearchJDBCRepository;
import com.mydocumentsref.api.portal.internal.casedocumentservice.services.input.AddPlanUsedRequest;
import com.mydocumentsref.api.portal.internal.casedocumentservice.services.input.PlanImages;
import com.mydocumentsref.api.portal.internal.casedocumentservice.services.output.AddPlanUsedResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * The type Add plan used delegate.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AddPlanUsedDelegate extends CAPIConfig {

    private final GetFormattedTitleSearchJDBCRepository getFormattedTitleSearchJDBCRepository;
    private final AddPlanUsedJDBCRepository jdbcRepository;
    private final WebApiFacade webApiFacade;

    /**
     * Add plan used add plan used response.
     *
     * @param request the request
     * @return the add plan used response
     */
    public AddPlanUsedResponse addPlanUsed(final AddPlanUsedRequest request) {
        //TODO move constants to common lib and yml once all c-api done
        log.info("delegate addPlanUsed: {}", request);

        List<AddPlansUsed> plansUsedList = new ArrayList<>();
        AddPlanUsedResponse addPlanUsedResponse = AddPlanUsedResponse.builder().plans(plansUsedList).userCode(request.getUserCode()).timestamp(DateUtils
                    .convertTimestampToString(Timestamp.valueOf(LocalDateTime.now()))).build();

        for (PlanImages planImages : request.getPlanImages()) {// step 1 for each planImages loop through
            AddPlansUsedPacketImage addPlansUsedPacketImage = AddPlansUsedPacketImage.builder().build();
            AddPlansUsed addPlansUsed = AddPlansUsed.builder().build();
            List<PacketImage> recordExists =
                        jdbcRepository.checkRecordExists(request, planImages);  // STEP 1 check record exists

            log.info("delegate addPlanUsed recordExists: {}", recordExists);

            if (ObjectUtils.isNotEmpty(recordExists)) {
                addPlansUsedPacketImage =
                            addPlansUsedPacketImage.withResponseMessageDescription("Record Exists").withResponseMessage(Constants.SUCCESS).withPacketImages(recordExists);
                addPlansUsed = addPlansUsed.withPlan(List.of(addPlansUsedPacketImage));
                plansUsedList.add(addPlansUsed);
                continue;
                // return addPlanUsedResponse;

            } else {
                addPlansUsedPacketImage = addPlansUsedPacketImage.withResponseMessageDescription("Record Does not Exist").withResponseMessage(Constants.SUCCESS);
                addPlansUsed = addPlansUsed.withPlan(List.of(addPlansUsedPacketImage));
                // plansUsedList.add(addPlansUsed);
            }

            //Call DIMS STEP 2 C-API Request 1

            String[] splitPlanNumberAlphaNumeric;
            String apiRequest;
            try {
                splitPlanNumberAlphaNumeric = request.getPlanNumber().split("(?<=\\D)(?=\\d)");
                apiRequest = "DS|" + splitPlanNumberAlphaNumeric[0] + "|" + splitPlanNumberAlphaNumeric[1];
            } catch (ArrayIndexOutOfBoundsException exception) {
                addPlansUsedPacketImage =
                            addPlansUsedPacketImage.withResponseErrorCode("DOC_DATA_INPUT_ADD_PLN_USED").withResponseError("Please ensure plan number is alphanumeric");
                addPlansUsed = addPlansUsed.withPlan(List.of(addPlansUsedPacketImage));
                plansUsedList.add(addPlansUsed);
                continue;
                //return addPlanUsedResponse;

            }

            ApiResultPages data;
            WebApiRequest webApiRequest = new WebApiRequest(CLIENT_CODE, PORT_NUMBER, REMOTE_USER, apiRequest);
            try {
                data = webApiFacade.runProcess(webApiRequest);//step 1 create the c-api request
            } catch (Exception exception) {
                addPlansUsedPacketImage = addPlansUsedPacketImage.withResponseErrorCode("DOC_DATA_ERR_ADD_PLN_USED_FROM_C_API_01_DS")
                            .withResponseError(StringUtils.substringAfter(exception.getMessage(), ":").trim());
                addPlansUsed = addPlansUsed.withPlan(List.of(addPlansUsedPacketImage));
                plansUsedList.add(addPlansUsed);
                continue;
                // return addPlanUsedResponse;
            }

            //response

            if ((data.getPages().isEmpty())) {
                addPlansUsedPacketImage = addPlansUsedPacketImage.withResponseError("Plan not found").withResponseErrorCode("DOC_DATA_ERR_ADD_PLAN_USED_C_API_01_DS");
                addPlansUsed = addPlansUsed.withPlan(List.of(addPlansUsedPacketImage));
                plansUsedList.add(addPlansUsed);
                continue;
            }

            String subType = null;
            if (ObjectUtils.isNotEmpty(data.getPages().get(0))) {
                subType = data.getPages().get(0).getLines().get(2);
            } else {
                addPlansUsedPacketImage =
                            addPlansUsedPacketImage.withResponseError("Plan not found").withResponseErrorCode("DOC_DATA_ERR_ADD_PLAN_USED_C_API_01_DS_PATH_NOT_FOUND");
                addPlansUsed = addPlansUsed.withPlan(List.of(addPlansUsedPacketImage));
                plansUsedList.add(addPlansUsed);
                continue;
            }
            log.info("delegate Call the DIIMS API to apiResultPages DS|DL| subType:{}", subType);

            String ltoSection = getFormattedTitleSearchJDBCRepository
                        .getLTOsection(request.getUserCode());  // STEP 3 get ltoSection

            log.info("delegate Get Formatted Title search ltoSection: {}", ltoSection);
            String clientRef = "lrs:" + ltoSection + "-" + request.getUserCode(); // STEP 2 construct clientRef

            //STEP 4 C-API Request 2
            apiRequest = "DR|" + clientRef + "|" + splitPlanNumberAlphaNumeric[0] + "|" + splitPlanNumberAlphaNumeric[1] + "|" + planImages.getImageSubtype() + "||F||";
            webApiRequest.setApiRequest(apiRequest);
            try {
                data = webApiFacade.runProcess(webApiRequest);//step 1 create the c-api request
            } catch (Exception exception) {
                addPlansUsedPacketImage = addPlansUsedPacketImage.withResponseErrorCode("DOC_DATA_ERR_ADD_PLN_USED_FROM_C_API_02_DR")
                            .withResponseError(StringUtils.substringAfter(exception.getMessage(), ":").trim());
                addPlansUsed = addPlansUsed.withPlan(List.of(addPlansUsedPacketImage));
                plansUsedList.add(addPlansUsed);
                continue;
                // return addPlanUsedResponse;
            }

            if ((data.getPages().isEmpty())) {
                addPlansUsedPacketImage = addPlansUsedPacketImage.withResponseError("Plan not found").withResponseErrorCode("DOC_DATA_ERR_ADD_PLAN_USED_C_API_02_DR");
                addPlansUsed = addPlansUsed.withPlan(List.of(addPlansUsedPacketImage));
                plansUsedList.add(addPlansUsed);
                continue;
            }

            log.info("delegate Call the DIIMS API to apiResultPages DR|DL :{}", data.getPages());

            int updateResultSet = 0;
            if (ObjectUtils.isNotEmpty(data.getPages().get(0))) {
                updateResultSet = jdbcRepository
                            .insertRecordIntoPacketImageTbl(request, planImages, splitPlanNumberAlphaNumeric[0], data.getPages().get(0).getLines().get(1),
                                        planImages.getImageSubtype());  // STEP 5

                addPlansUsedPacketImage = addPlansUsedPacketImage.withRecordsInserted(updateResultSet); //STEP 6

                updateResultSet = jdbcRepository.updateTheLasModifiedDate(request);  // STEP 7
                addPlansUsedPacketImage = addPlansUsedPacketImage.withRecordUpdated(updateResultSet);

                long numberOfCalls = 1L;
                while (numberOfCalls <= 18L && ObjectUtils.isEmpty(addPlansUsedPacketImage.getPacketImages())) {
                    addPlansUsedPacketImage =
                                addPlansUsedPacketImage.withPacketImages(jdbcRepository.pollDetailsOfRecordsInserted(request, planImages));
                    try {
                        TimeUnit.SECONDS.sleep(05);
                        log.info("delegate addPlanUsed polling records, call # {}", numberOfCalls);
                        numberOfCalls++;
                    } catch (InterruptedException e) {
                        log.error("delegate addPlanUsed polling records, Interrupted Exception {}", e);
                        addPlansUsedPacketImage = addPlansUsedPacketImage.withResponseError("Plan not found").withResponseErrorCode("DOC_DATA_ERR_ADD_PLAN_USED_POLL_ERR");
                        addPlansUsed = addPlansUsed.withPlan(List.of(addPlansUsedPacketImage));
                        plansUsedList.add(addPlansUsed);
                        continue;
                    }
                }

                if (ObjectUtils.isEmpty(addPlansUsedPacketImage.getPacketImages())) {
                    addPlansUsedPacketImage = addPlansUsedPacketImage
                                .withResponseError(
                                            "A timeout occurred while retrieving the document. Please refresh the packet in a few minutes or try downloading the document again.")
                                .withResponseErrorCode("DOC_DATA_ERR_ADD_DEAL_USED_DB_IMG_POLL");
                    addPlansUsed = addPlansUsed.withPlan(List.of(addPlansUsedPacketImage));
                    plansUsedList.add(addPlansUsed);

                } else {
                    addPlansUsedPacketImage = addPlansUsedPacketImage.withResponseMessageDescription("Record Created").withResponseMessage(Constants.SUCCESS)
                                .withPacketImages(addPlansUsedPacketImage.getPacketImages());
                    addPlansUsed = addPlansUsed.withPlan(List.of(addPlansUsedPacketImage));
                    plansUsedList.add(addPlansUsed);
                }

            } else {//(data.getPages().get(0)
                addPlansUsedPacketImage =
                            addPlansUsedPacketImage.withResponseError("Plan not found").withResponseErrorCode("DOC_DATA_ERR_ADD_PLAN_USED_C_API_03_DR_DL_PATH_NOT_FOUND");
                addPlansUsed = addPlansUsed.withPlan(List.of(addPlansUsedPacketImage));
                plansUsedList.add(addPlansUsed);
                continue;
            }

        }// step 1 for completed

        return addPlanUsedResponse;
    }

}
