package com.mydocumentsref.api.portal.internal.casedocumentservice.delegate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.mydocumentsref.api.common.commonservice.capi.facade.WebApiFacade;
import com.mydocumentsref.api.common.commonservice.capi.facade.WebApiRequest;
import com.mydocumentsref.api.common.commonservice.capi.model.ApiResultPages;
import com.mydocumentsref.api.portal.internal.casedocumentservice.model.ImageSubtype;
import com.mydocumentsref.api.portal.internal.casedocumentservice.model.Plan;
import com.mydocumentsref.api.portal.internal.casedocumentservice.model.PlanNumberDetail;
import com.mydocumentsref.api.portal.internal.casedocumentservice.repository.GetPlansUsedJDBCRepository;
import com.mydocumentsref.api.portal.internal.casedocumentservice.services.input.PlansUsedRequest;
import com.mydocumentsref.api.portal.internal.casedocumentservice.services.output.GetPlansUsedResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.ListUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * The type Get plans used delegate.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class GetPlansUsedDelegate extends CAPIConfig {

    private final GetPlansUsedJDBCRepository jdbcRepository;
    private final WebApiFacade webApiFacade;

    /**
     * Gets plans used.
     *
     * @param request the request
     * @return the plans used
     */
    public GetPlansUsedResponse getPlansUsed(final PlansUsedRequest request) {
        //TODO move constants to common lib and yml once all c-api done
        log.info("delegate getPlansUsed: {}", request);

        ImageSubtype imageSubtype = ImageSubtype.builder().build();
        List<ImageSubtype> imageSubtypes = new ArrayList<>();
        Plan plan = Plan.builder().imageSubtypes(imageSubtypes).build();
        List<Plan> plans = new ArrayList<>();
        GetPlansUsedResponse getPlansUsedResponse = GetPlansUsedResponse.builder().plans(plans).build();

        List<PlanNumberDetail> planNumberDetailsAll = jdbcRepository.getPlanNumberWithITSAndUserAddedSource(request);
        log.info("delegate getPlansUsed planNumberDetailsAll {}", planNumberDetailsAll);

        if (planNumberDetailsAll.isEmpty()) {
            return getPlansUsedResponse;
        }
        List<PlanNumberDetail> planNumberDetailsITS =
                    planNumberDetailsAll.stream().filter(planNumberDetail -> planNumberDetail.getSource().toUpperCase().contains("ITS")).collect(Collectors.toList());
        String[] splitPlanNumberAlphaNumeric;
        String apiRequest;
        if (ObjectUtils.isNotEmpty(planNumberDetailsITS)) {//step if start
            log.info("delegate getPlansUsed planNumbers   SOURCE = ITS planNumberDetails {}", planNumberDetailsITS);
            for (PlanNumberDetail planNumbers : planNumberDetailsITS) {//step for start

                try {
                    splitPlanNumberAlphaNumeric = planNumbers.getPlanNumber().split("(?<=\\D)(?=\\d)");
                    apiRequest = "DS|" + splitPlanNumberAlphaNumeric[0] + "|" + splitPlanNumberAlphaNumeric[1];
                } catch (ArrayIndexOutOfBoundsException exception) {
                    imageSubtype = imageSubtype.withResponseErrorCode("DOC_DATA_INPUT_SCH_PLN_USED").withResponseError("Please ensure plan number is alphanumeric");
                    plan = plan.withPlanNumber(planNumbers.getPlanNumber()).withImageSubtypes(List.of(imageSubtype));
                    plans.add(plan);
                    continue; // step 1 continue
                    /*   log.error("c-api error: {}",exception);*/

                }

                ApiResultPages data;
                WebApiRequest webApiRequest = new WebApiRequest(CLIENT_CODE, PORT_NUMBER, REMOTE_USER, apiRequest);
                try {
                    data = webApiFacade.runProcess(webApiRequest);//step 1 create the c-api request
                } catch (Exception exception) {
                    imageSubtype = imageSubtype.withResponseErrorCode("DOC_DATA_ERR_SCH_PLN_USED_FROM_C_API")
                                .withResponseError(StringUtils.substringAfter(exception.getMessage(), ":").trim());
                    plan = plan.withPlanNumber(planNumbers.getPlanNumber()).withImageSubtypes(List.of(imageSubtype));
                    plans.add(plan);
                    continue; //step 2 continue
                    /*log.error("c-api error: {}",exception);*/

                }

                String[] subTypes;
                int totalRows;
                log.info("delegate data: {}", data);
                if (ObjectUtils.isNotEmpty(data
                            .getPages())) {//step 2 extract the subTypes from c-api the structure crudely explained in http://lpi-confluence:8090/display/DSP/Search+plans+used
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
                            subTypeCurrentPosition =
                                        subTypeCurrentPosition + subTypeFixedSubsequentPosition;//subsequent rows the  subTypes position is as 9+7=16, 16+7=23, 23+7=30........
                            subTypes[row] = data.getPages().get(0).getLines().get(subTypeCurrentPosition);
                        }
                    }
                } else {
                    boolean apended = false;
                    imageSubtype = imageSubtype.withResponseError("Plan not found").withResponseErrorCode("DOC_DATA_ERR_SCH_PLN_USED_C_API_PLN_!_FOUND");
                    for (Plan plan1 : plans) {
                        if (plan1 != null && plan1.getPlanNumber().equals(planNumbers.getPlanNumber())) {
                            log.info("delegate getPlansUsed  adding imageSubTypes to same  plan for planNumber {} ", planNumbers.getPlanNumber());
                            imageSubtypes = ListUtils.union(plan1.getImageSubtypes(), (List.of(imageSubtype)));
                            plan = plan.withPlanNumber(planNumbers.getPlanNumber()).withImageSubtypes(imageSubtypes);
                            apended = true;
                            //break;
                        } else {

                        }
                    }
                    if (!apended) {
                        plan = plan.withPlanNumber(planNumbers.getPlanNumber()).withImageSubtypes(List.of(imageSubtype));
                        plans.add(plan);
                    }
                    continue;//step 3 continue

                }

                if (ObjectUtils.isNotEmpty(subTypes)) {//step 2....one planNumber can have more than 1 subType the structure of subType confluence above
                    List<String> subTypesSkipListFiltered =
                                Arrays.stream(subTypes).filter(subType -> !skipSubTypes.contains(subType)).collect(Collectors.toList());//step 3 filter subtypes

                    String subTypeName;
                    for (String subType : subTypesSkipListFiltered) {
                        //step 4 ..
                        subTypeName = jdbcRepository.getSubTypeName(subType);
                        if (StringUtils.isNotEmpty(subTypeName)) {
                            imageSubtype = imageSubtype.withSubtypeName(subType).withImageSubtypeDesc(subTypeName);
                        }

                        if ("P".equalsIgnoreCase(subType)) {
                            imageSubtype = imageSubtype.withImageCategory("Plan");
                        } else if ("B".equalsIgnoreCase(subType)) {
                            imageSubtype = imageSubtype.withImageCategory("88b");
                        } else {
                            imageSubtype = imageSubtype.withImageCategory("Other");
                        }

                        //  Check the PACKET_IMAGE table for a matching record
                        List<ImageSubtype> imageIdPaths = jdbcRepository.getImageIdPath(planNumbers, subType, request);
                        if (ObjectUtils.isNotEmpty(imageIdPaths)) {
                            imageSubtype = imageSubtype.withPacketImageId(imageIdPaths.get(0).getPacketImageId());
                            imageSubtype = imageSubtype.withPath(imageIdPaths.get(0).getPath());
                        } else {
                            imageSubtype = imageSubtype.withPacketImageId(null);
                            imageSubtype = imageSubtype.withPath(null);
                            /*   planUsed = planUsed.withAlreadyInPacket("N");*/
                        }
                        /* planUsed = planUsed.withImageSubtype(subType);*/
                        log.info("delegate before adding to imageSubtypes : {}", List.of(imageSubtype));
                        boolean oneToMany = false;
                        for (Plan plan1 : plans) {//step to ensure the image_subtypes for a particular display_name are in one list or 1-->many mapping.Without it its 1-->1 mapping
                            if (plan1 != null && plan1.getPlanNumber().equals(planNumbers.getPlanNumber())) {
                                log.info("delegate getPlansUsed  adding imageSubTypes to same  plan for planNumber {} ", planNumbers.getPlanNumber());
                                imageSubtypes = ListUtils.union(plan1.getImageSubtypes(), (List.of(imageSubtype)));
                                log.info("delegate getPlansUsed  adding imageSubTypes to same  plans before remove {} ", plans.size());
                                plans.removeIf(plan2 -> plan2.getPlanNumber().equals(planNumbers.getPlanNumber()));//avoids concurrent modification error
                                log.info("delegate getPlansUsed  adding imageSubTypes to same  plans after remove {} ", plans.size());
                                plan = plan.withPlanNumber(planNumbers.getPlanNumber()).withImageSubtypes(imageSubtypes);
                                plans.add(plan);
                                oneToMany = true;

                            } else {

                            }
                        }
                        if (!oneToMany) {
                            plan = plan.withPlanNumber(planNumbers.getPlanNumber()).withImageSubtypes(List.of(imageSubtype));
                            plans.add(plan);
                        }
                    }
                } else {
            /*imageSubtype = imageSubtype.withImageAvailable("N");
            imageSubtypes.add(imageSubtype);*/

                }

            }//step for end
            //plans.add(plan);
        }//step if end

        //step 3. For each row, if <SOURCE>='USER_ADDED' , a) Append any records to the "image_subtypes" array for this <DISPLAY_NAME> for this Packet from the PACKET_IMAGE table
        List<PlanNumberDetail> planNumberDetailsUSERAdded =
                    planNumberDetailsAll.stream().filter(planNumberDetail -> planNumberDetail.getSource().toUpperCase().contains("USER_ADDED")).collect(Collectors.toList());

        //step 3..... filter user_added source further to ensure which is not present in its only gets reflected in user.
        planNumberDetailsUSERAdded = planNumberDetailsUSERAdded.stream()
                    .filter(its -> planNumberDetailsITS.stream().map(PlanNumberDetail::getPlanNumber).allMatch(userPlanNumber -> !userPlanNumber.equals(its.getPlanNumber())))
                    .collect(Collectors.toList());

        if (ObjectUtils.isNotEmpty(planNumberDetailsUSERAdded)) {
            imageSubtypes = new ArrayList<>();
            log.info("delegate planNumbers   SOURCE = USER_ADDED planNumberDetails {}", planNumberDetailsUSERAdded);

            for (PlanNumberDetail planNumbers : planNumberDetailsUSERAdded) {//step for start
                List<ImageSubtype> imageSubTypes = jdbcRepository.getImageSubTypes(planNumbers, request);
                if (ObjectUtils.isNotEmpty(imageSubTypes) && ObjectUtils.isNotEmpty(imageSubTypes.get(0))) {
                    for (ImageSubtype imageSubtype1 : imageSubTypes) {
                        imageSubtype = imageSubtype.withImageCategory(imageSubtype1.getImageCategory()).withSubtypeName(imageSubtype1.getSubtypeName())
                                    .withImageSubtypeDesc(imageSubtype1.getImageSubtypeDesc())
                                    .withPacketImageId(imageSubtype1.getPacketImageId())
                                    .withPath(imageSubtype1.getPath());
                        imageSubtypes.add(imageSubtype);

                    }
                    plan = plan.withPlanNumber(planNumbers.getPlanNumber()).withImageSubtypes(imageSubtypes);
                    plans.add(plan);
                }

            }
        }

        //STEP 6 return response

        return getPlansUsedResponse;
    }

}
