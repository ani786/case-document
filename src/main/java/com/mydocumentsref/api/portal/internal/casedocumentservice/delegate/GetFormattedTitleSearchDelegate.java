package com.mydocumentsref.api.portal.internal.casedocumentservice.delegate;

import java.util.List;

import com.mydocumentsref.api.common.commonservice.capi.facade.WebApiFacade;
import com.mydocumentsref.api.common.commonservice.capi.facade.WebApiRequest;
import com.mydocumentsref.api.common.commonservice.capi.model.ApiResultPages;
import com.mydocumentsref.api.portal.internal.casedocumentservice.repository.GetFormattedTitleSearchJDBCRepository;
import com.mydocumentsref.api.portal.internal.casedocumentservice.services.input.FormattedTitleSearchRequest;
import com.mydocumentsref.api.portal.internal.casedocumentservice.services.output.GetFormattedTitleSearchResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * The type Get formatted title search delegate.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class GetFormattedTitleSearchDelegate extends CAPIConfig {

    private final GetFormattedTitleSearchJDBCRepository jdbcRepository;
    private final WebApiFacade webApiFacade;

    /**
     * Gets formatted title search.
     *
     * @param request the request
     * @return the formatted title search
     */
    public GetFormattedTitleSearchResponse getFormattedTitleSearch(final FormattedTitleSearchRequest request) {
        //TODO move constants to common lib and yml once all c-api done
        log.info("delegate Get Formatted Title search: {}", request);

        String ltoSection = jdbcRepository.getLTOsection(request.getUserCode());  // STEP 1 get ltoSection

        log.info("delegate Get Formatted Title search ltoSection: {}", ltoSection);
        String clientRef = "lrs:" + ltoSection + "-" + request.getUserCode(); // STEP 2 construct clientRef

        List<String> searchTypes = List.of("TitleSearch", "HistoricalSearch", "CTInquiry"); //STEP 3 predefined

        String searchType = request.getFormattedTitleSearch().getSearchType(); //STEP 4 create request
        String cAPIsearchString = null;
        if (searchType.contains(searchTypes.get(0))) {
            cAPIsearchString = "ST|" + clientRef + "|" + request.getFormattedTitleSearch().getTitleRef() + "|Y";
        } else if (searchType.contains(searchTypes.get(1))) {
            cAPIsearchString = "RH|" + clientRef + "|" + request.getFormattedTitleSearch().getTitleRef();
        } else if (searchType.contains(searchTypes.get(2))) {
            cAPIsearchString = "RD|" + clientRef + "|" + request.getFormattedTitleSearch().getTitleRef();
        } else {
            log.error("delegate Get Formatted Title search invalid searchType: {}", request);
        }

        WebApiRequest webApiRequest = new WebApiRequest(CLIENT_CODE, PORT_NUMBER, REMOTE_USER, cAPIsearchString);
        //STEP 2 C-API Request

        ApiResultPages data = searchTypes.contains(searchType) ? webApiFacade.runProcess(webApiRequest) :
                    ApiResultPages.builder().build();

        //STEP 6 return response
        GetFormattedTitleSearchResponse getFormattedTitleSearchResponse =
                    GetFormattedTitleSearchResponse.builder().apiResultPages(data).build();

        return getFormattedTitleSearchResponse;
    }

}
