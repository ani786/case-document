package com.mydocumentsref.api.portal.internal.casedocumentservice.delegate;

import java.util.ArrayList;
import java.util.List;

import com.mydocumentsref.api.common.commonservice.capi.facade.WebApiFacade;
import com.mydocumentsref.api.common.commonservice.capi.facade.WebApiRequest;
import com.mydocumentsref.api.common.commonservice.capi.model.ApiResultPages;
import com.mydocumentsref.api.portal.internal.casedocumentservice.model.PacketImage;
import com.mydocumentsref.api.portal.internal.casedocumentservice.model.ViewImage;
import com.mydocumentsref.api.portal.internal.casedocumentservice.repository.GetViewImageJDBCRepository;
import com.mydocumentsref.api.portal.internal.casedocumentservice.services.input.ViewImageRequest;
import com.mydocumentsref.api.portal.internal.casedocumentservice.services.output.GetViewImageResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * The type Get view image delegate.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class GetViewImageDelegate extends CAPIConfig {

    private final GetViewImageJDBCRepository jdbcRepository;
    private final WebApiFacade webApiFacade;

    /**
     * Gets view image.
     *
     * @param request the request
     * @return the view image
     */
    public GetViewImageResponse getViewImage(final ViewImageRequest request) {
        //TODO move constants to common lib and yml once all c-api done
        log.info("delegate getViewImage request : {}", request);

        ViewImage packetImage = ViewImage.builder().build();
        List<ViewImage> packetImages = new ArrayList<>();
        GetViewImageResponse getViewImageResponse =
                    GetViewImageResponse.builder().packetImages(packetImages).build();


        if (dlgStatus.contains(request.getViewImage().getDlgStatus().toUpperCase())) { //step 1   statusType = "Registered";
            log.info("delegate getViewImage Registered");
            String apiRequest = "DS|DL|" + request.getViewImage().getFmtDlgNum(); //step 2 c- api call 1
            WebApiRequest webApiRequest = new WebApiRequest(CLIENT_CODE, PORT_NUMBER, REMOTE_USER, apiRequest);
            ApiResultPages data = webApiFacade.runProcess(webApiRequest);

            //response
            if ((data.getPages().isEmpty() && ObjectUtils.isEmpty(data.getPages().get(0)))) {
                packetImage = packetImage.withResponseError("Document not yet loaded to DIIMS").withResponseErrorCode("DOC_DATA_ERR_VIEW_IMG_C_API_01_DS_DL");
                packetImages.add(packetImage);
                return getViewImageResponse;

            }

            log.info("delegate Call the DIIMS API to apiResultPages DS|DL| :{}", data.getPages());

            String ltoSection = jdbcRepository.getLTOsection(request.getUserCode());  // STEP 3 get ltoSection

            log.info("delegate Get Formatted Title search ltoSection: {}", ltoSection);
            String clientRef = "lrs:" + ltoSection + "-" + request.getUserCode(); // STEP 2 construct clientRef

            //STEP 4 C-API call 2
            apiRequest = "DR|" + clientRef + "|DL|" + request.getViewImage().getFmtDlgNum() + "|||F||";
            webApiRequest.setApiRequest(apiRequest);
            data = webApiFacade.runProcess(webApiRequest);

            //response
            if ((data.getPages().isEmpty())) {
                packetImage = packetImage.withResponseError("Document not found").withResponseErrorCode("DOC_DATA_ERR_VIEW_IMG_C_API_02_DR_DL");//convention ms_DATA_ERR_API_XXXX
                packetImages.add(packetImage);
                return getViewImageResponse;
            } else if (ObjectUtils.isNotEmpty(data.getPages().get(0))) {
                packetImage = packetImage.withPath(data.getPages().get(0).getLines().get(1) + ".pdf");
                packetImages.add(packetImage);
                return getViewImageResponse;
            } else {
                packetImage = packetImage.withResponseError("Document not found")
                            .withResponseErrorCode("DOC_DATA_ERR_VIEW_IMG_C_API_02_DR_DL_PATH_NOT_FOUND");//convention ms_DATA_ERR_API_XXXX
                packetImages.add(packetImage);
                return getViewImageResponse;

            }

        }// step 1 statusType = "Registered" completed
        else { //step 2  statusType = "Unregistered";
            List<ViewImage> paketImages;
            log.info("delegate getViewImage Unregistered");
            paketImages = jdbcRepository.getUnregisteredImage(request);
            if (ObjectUtils.isNotEmpty(paketImages)) {
                packetImage = packetImage.withPath(paketImages.get(0).getPath() + ".pdf").withDealingImageId(paketImages.get(0).getDealingImageId());
            }
            packetImages.add(packetImage);

            return getViewImageResponse;

        }

    }

}
