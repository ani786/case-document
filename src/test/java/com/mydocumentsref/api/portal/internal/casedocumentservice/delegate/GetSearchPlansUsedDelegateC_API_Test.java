package com.mydocumentsref.api.portal.internal.casedocumentservice.delegate;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.mydocumentsref.api.common.commonservice.capi.facade.WebApiFacade;
import com.mydocumentsref.api.portal.internal.casedocumentservice.model.SearchPlanUsed;
import com.mydocumentsref.api.portal.internal.casedocumentservice.repository.GetFormattedTitleSearchJDBCRepository;
import com.mydocumentsref.api.portal.internal.casedocumentservice.services.output.GetSearchPlansUsedResponse;
import org.apache.commons.lang3.ObjectUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class GetSearchPlansUsedDelegateC_API_Test {

    private static final String C_L_I_E_N_T__C_O_D_E = "C_L_I_E_N_T__C_O_D_E";
    private static final String P_O_R_T__N_U_M_B_E_R = "P_O_R_T__N_U_M_B_E_R";
    private static final String R_E_M_O_T_E__U_S_E_R = "R_E_M_O_T_E__U_S_E_R";
    @Mock private GetFormattedTitleSearchJDBCRepository jdbcRepository;
    @Mock private WebApiFacade webApiFacade;
    @InjectMocks private GetSearchPlansUsedDelegate underTest;

    private AutoCloseable closeable;

    @BeforeEach
    public void setup() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void closeService() throws Exception {
        closeable.close();
    }

    @Test
    void getSearchPlansUsed_validate_cAPI_0ROW_Response_for_subTypes() {

        String[] cAPItwoRowResponseArray = {};
        List<String> cAPItwoRowResponse = Arrays.asList(cAPItwoRowResponseArray);

        int totalRows = 0;
        String subTypes[] = null;
        if (ObjectUtils.isNotEmpty(cAPItwoRowResponse)) {
            totalRows = Integer.parseInt(cAPItwoRowResponse.get(1));
            subTypes = new String[totalRows];
            for (int row = 0, subtypeInitialPosition = 2, subTypesFixedSubsequentPosition = 7, subTypesCurrentPosition = 0; row < totalRows; row++) {
                if (row == 0) {
                    subTypesCurrentPosition = subtypeInitialPosition;
                    subTypes[row] = cAPItwoRowResponse.get(subTypesCurrentPosition);//0 th row  subTypes in 2nd position
                } else if (row == 1) {
                    subTypesCurrentPosition = subtypeInitialPosition + subTypesFixedSubsequentPosition;
                    subTypes[row] = cAPItwoRowResponse.get(subTypesCurrentPosition); //1 st row  subTypes in 9th  position
                } else {
                    subTypesCurrentPosition =
                                subTypesCurrentPosition + subTypesFixedSubsequentPosition;//subsequent rows the  subTypes position is as 9+7=16, 16+7=23, 23+7=30........
                    subTypes[row] = cAPItwoRowResponse.get(subTypesCurrentPosition);
                }
            }
        }

        if (ObjectUtils.isNotEmpty(subTypes)) {
            System.out.println("subtype : " + subTypes);
        }

        assertEquals(0, totalRows);
    }

    @Test
    void getSearchPlansUsed_validate_cAPI_1ROW_Response_for_subTypes() {

        String[] cAPItwoRowResponseArray = {"Q", "1", "P", "0", "", "1", "A2", "75", "199306291753"};
        List<String> cAPItwoRowResponse = Arrays.asList(cAPItwoRowResponseArray);

        int totalRows = 0;
        String subTypes[] = null;
        if (ObjectUtils.isNotEmpty(cAPItwoRowResponse)) {
            totalRows = Integer.parseInt(cAPItwoRowResponse.get(1));
            subTypes = new String[totalRows];
            for (int row = 0, subtypeInitialPosition = 2, subTypesFixedSubsequentPosition = 7, subTypesCurrentPosition = 0; row < totalRows; row++) {
                if (row == 0) {
                    subTypesCurrentPosition = subtypeInitialPosition;
                    subTypes[row] = cAPItwoRowResponse.get(subTypesCurrentPosition);//0 th row  subTypes in 2nd position
                } else if (row == 1) {
                    subTypesCurrentPosition = subtypeInitialPosition + subTypesFixedSubsequentPosition;
                    subTypes[row] = cAPItwoRowResponse.get(subTypesCurrentPosition); //1 st row  subTypes in 9th  position
                } else {
                    subTypesCurrentPosition =
                                subTypesCurrentPosition + subTypesFixedSubsequentPosition;//subsequent rows the  subTypes position is as 9+7=16, 16+7=23, 23+7=30........
                    subTypes[row] = cAPItwoRowResponse.get(subTypesCurrentPosition);
                }
            }
        }

        System.out.println("subtype : " + Arrays.toString(subTypes));

        assertEquals(1, totalRows);
        assertEquals("P", subTypes[0]);

    }

    @Test
    void getSearchPlansUsed_validate_cAPI_2ROW_Response_for_subTypes() {

        String[] cAPItwoRowResponseArray = {"Q", "2", "P", "0", "", "4", "A2", "197", "201504142202", "B", "0", "", "3", "A4", "70", "201504142202"};
        List<String> cAPItwoRowResponse = Arrays.asList(cAPItwoRowResponseArray);

        int totalRows = 0;
        String subTypes[] = null;
        if (ObjectUtils.isNotEmpty(cAPItwoRowResponse)) {
            totalRows = Integer.parseInt(cAPItwoRowResponse.get(1));
            subTypes = new String[totalRows];
            for (int row = 0, subtypeInitialPosition = 2, subTypesFixedSubsequentPosition = 7, subTypesCurrentPosition = 0; row < totalRows; row++) {
                if (row == 0) {
                    subTypesCurrentPosition = subtypeInitialPosition;
                    subTypes[row] = cAPItwoRowResponse.get(subTypesCurrentPosition);//0 th row  subTypes in 2nd position
                } else if (row == 1) {
                    subTypesCurrentPosition = subtypeInitialPosition + subTypesFixedSubsequentPosition;
                    subTypes[row] = cAPItwoRowResponse.get(subTypesCurrentPosition); //1 th row  subTypes in 9th  position
                } else {
                    subTypesCurrentPosition =
                                subTypesCurrentPosition + subTypesFixedSubsequentPosition;//subsequent rows the  subTypes position is as 9+7=16, 16+7=23, 23+7=30........
                    subTypes[row] = cAPItwoRowResponse.get(subTypesCurrentPosition);
                }
            }
        }

        System.out.println("subtype : " + Arrays.toString(subTypes));

        assertEquals(2, totalRows);
        assertEquals("P", subTypes[0]);
        assertEquals("B", subTypes[1]);

    }

    @Test
    void getSearchPlansUsed_validate_cAPI_3ROW_Response_for_subTypes() {

        String[] cAPItwoRowResponseArray =
                    {"Q", "3", "P", "0", "", "14", "A2", "1176", "200705091001", "B", "0", "", "14", "A4", "644", "200407122234", "M", "0", "", "30", "A4", "971", "201606302202"};
        List<String> cAPItwoRowResponse = Arrays.asList(cAPItwoRowResponseArray);

        int totalRows = 0;
        String subTypes[] = null;
        if (ObjectUtils.isNotEmpty(cAPItwoRowResponse)) {
            totalRows = Integer.parseInt(cAPItwoRowResponse.get(1));
            subTypes = new String[totalRows];
            for (int row = 0, subtypeInitialPosition = 2, subTypesFixedSubsequentPosition = 7, subTypesCurrentPosition = 0; row < totalRows; row++) {
                if (row == 0) {
                    subTypesCurrentPosition = subtypeInitialPosition;
                    subTypes[row] = cAPItwoRowResponse.get(subTypesCurrentPosition);//0 th row  subTypes in 2nd position
                } else if (row == 1) {
                    subTypesCurrentPosition = subtypeInitialPosition + subTypesFixedSubsequentPosition;
                    subTypes[row] = cAPItwoRowResponse.get(subTypesCurrentPosition); //1 th row  subTypes in 9th  position
                } else {
                    subTypesCurrentPosition =
                                subTypesCurrentPosition + subTypesFixedSubsequentPosition;//subsequent rows the  subTypes position is as 9+7=16, 16+7=23, 23+7=30........
                    subTypes[row] = cAPItwoRowResponse.get(subTypesCurrentPosition);
                }
            }
        }

        System.out.println("subtype : " + Arrays.toString(subTypes));

        assertEquals(3, totalRows);
        assertEquals("P", subTypes[0]);
        assertEquals("B", subTypes[1]);
        assertEquals("M", subTypes[2]);

    }

    @Test
    void getSearchPlansUsed_validate_cAPI_3ROW_Response_for_subTypes_withSkipSubTypes_NO_SKIP() {

        String[] cAPItwoRowResponseArray =
                    {"Q", "3", "P", "0", "", "14", "A2", "1176", "200705091001", "B", "0", "", "14", "A4", "644", "200407122234", "M", "0", "", "30", "A4", "971", "201606302202"};
        List<String> cAPItwoRowResponse = Arrays.asList(cAPItwoRowResponseArray);

        int totalRows = 0;
        String subTypes[] = null;
        if (ObjectUtils.isNotEmpty(cAPItwoRowResponse)) {
            totalRows = Integer.parseInt(cAPItwoRowResponse.get(1));
            subTypes = new String[totalRows];
            for (int row = 0, subtypeInitialPosition = 2, subTypesFixedSubsequentPosition = 7, subTypesCurrentPosition = 0; row < totalRows; row++) {
                if (row == 0) {
                    subTypesCurrentPosition = subtypeInitialPosition;
                    subTypes[row] = cAPItwoRowResponse.get(subTypesCurrentPosition);//0 th row  subTypes in 2nd position
                } else if (row == 1) {
                    subTypesCurrentPosition = subtypeInitialPosition + subTypesFixedSubsequentPosition;
                    subTypes[row] = cAPItwoRowResponse.get(subTypesCurrentPosition); //1 th row  subTypes in 9th  position
                } else {
                    subTypesCurrentPosition =
                                subTypesCurrentPosition + subTypesFixedSubsequentPosition;//subsequent rows the  subTypes position is as 9+7=16, 16+7=23, 23+7=30........
                    subTypes[row] = cAPItwoRowResponse.get(subTypesCurrentPosition);
                }
            }
        }
        assert subTypes != null;
        List<String> filteredList = Arrays.stream(subTypes).filter(sub -> !skipSubTypes.contains(sub)).collect(Collectors.toList());
        System.out.println("subtype : " + filteredList);
        assertEquals(3, totalRows);
        assertEquals("P", filteredList.get(0));
        assertEquals("B", filteredList.get(1));
        assertEquals("M", filteredList.get(2));

    }

    @Test
    void getSearchPlansUsed_validate_cAPI_3ROW_Response_for_subTypes_withSkipSubTypes_2_SKIP() {

        String[] cAPItwoRowResponseArray =
                    {"Q", "3", "P", "0", "", "14", "A2", "1176", "200705091001", "A", "0", "", "14", "A4", "644", "200407122234", "X", "0", "", "30", "A4", "971", "201606302202"};
        List<String> cAPItwoRowResponse = Arrays.asList(cAPItwoRowResponseArray);

        int totalRows = 0;
        String subTypes[] = null;
        if (ObjectUtils.isNotEmpty(cAPItwoRowResponse)) {
            totalRows = Integer.parseInt(cAPItwoRowResponse.get(1));
            subTypes = new String[totalRows];
            for (int row = 0, subtypeInitialPosition = 2, subTypesFixedSubsequentPosition = 7, subTypesCurrentPosition = 0; row < totalRows; row++) {
                if (row == 0) {
                    subTypesCurrentPosition = subtypeInitialPosition;
                    subTypes[row] = cAPItwoRowResponse.get(subTypesCurrentPosition);//0 th row  subTypes in 2nd position
                } else if (row == 1) {
                    subTypesCurrentPosition = subtypeInitialPosition + subTypesFixedSubsequentPosition;
                    subTypes[row] = cAPItwoRowResponse.get(subTypesCurrentPosition); //1 th row  subTypes in 9th  position
                } else {
                    subTypesCurrentPosition =
                                subTypesCurrentPosition + subTypesFixedSubsequentPosition;//subsequent rows the  subTypes position is as 9+7=16, 16+7=23, 23+7=30........
                    subTypes[row] = cAPItwoRowResponse.get(subTypesCurrentPosition);
                }
            }
        }
        assert subTypes != null;
        List<String> filteredList = Arrays.stream(subTypes).filter(subType -> !skipSubTypes.contains(subType)).collect(Collectors.toList());
        System.out.println("subtype : " + filteredList);
        assertEquals(3, totalRows);
        assertEquals("P", filteredList.get(0));

    }

    public static final List<String> skipSubTypes = List.of("A", "K", "L", "S", "W", "X");

    @Test
    void test_SubTypes_added_to_response() {
        List<String> subTypesSkipListFiltered = List.of("A", "K", "L", "S", "W", "X");
        SearchPlanUsed searchPlanUsed = SearchPlanUsed.builder().build();
        searchPlanUsed = searchPlanUsed.withPlanNumber("DP123456");
        List<SearchPlanUsed> plansUsed = new ArrayList<>();
        GetSearchPlansUsedResponse getSearchPlansUsedResponse = GetSearchPlansUsedResponse.builder().searchPlansUsed(plansUsed).build();
        for (String subType : subTypesSkipListFiltered) {
            //step 4 ..
            searchPlanUsed = searchPlanUsed.withImageSubtype(subType);
            plansUsed.add(searchPlanUsed);

        }

        System.out.println("list : " + getSearchPlansUsedResponse.getSearchPlansUsed());
    }

}
