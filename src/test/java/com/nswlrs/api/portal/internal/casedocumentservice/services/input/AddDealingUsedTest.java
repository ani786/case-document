package com.nswlrs.api.portal.internal.casedocumentservice.services.input;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

class AddDealingUsedTest {

    private static final long PACKET_ID = 55;
    private static final String FMT_DLG_NUM1 = "AD123456";
    private static final String FMT_DLG_NUM2 = "A123456";
    private static final String FMT_DLG_NUM3 = "1234567";
    private static final String FMT_DLG_NUM4 = "12";
    private static final String FMT_DLG_NUM5 = "A12";
    private static final String FMT_DLG_NUM1_FAIL = "AAA123456";
    private static final String FMT_DLG_NUM2_FAIL = "A1234566666";
    private static final String FMT_DLG_NUM3_FAIL = "12345678";
    private static final String FMT_DLG_NUM4_FAIL = "AB12";
    private static final String regex = "^([A-Z]{2}[0-9]{6}|^[A-Z][0-9]{1,6}|^[0-9]{1,7}$)";

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
    void getFmtDlgNum_scenario_1() {

        Boolean matches = FMT_DLG_NUM1.matches(regex);
        assertEquals(Boolean.TRUE, matches);
    }

    @Test
    void getFmtDlgNum_scenario_2() {
        Boolean matches = FMT_DLG_NUM2.matches(regex);
        assertEquals(Boolean.TRUE, matches);
    }

    @Test
    void getFmtDlgNum_scenario_3() {
        Boolean matches = FMT_DLG_NUM3.matches(regex);
        assertEquals(Boolean.TRUE, matches);
    }

    @Test
    void getFmtDlgNum_scenario_4() {
        Boolean matches = FMT_DLG_NUM4.matches(regex);
        assertEquals(Boolean.TRUE, matches);
    }

    @Test
    void getFmtDlgNum_scenario_5() {
        Boolean matches = FMT_DLG_NUM5.matches(regex);
        assertEquals(Boolean.TRUE, matches);
    }

    @Test
    void getFmtDlgNum_scenario_fail_1() {
        Boolean matches = FMT_DLG_NUM1_FAIL.matches(regex);
        assertEquals(Boolean.FALSE, matches);
    }

    @Test
    void getFmtDlgNum_scenario_fail_2() {
        Boolean matches = FMT_DLG_NUM2_FAIL.matches(regex);
        assertEquals(Boolean.FALSE, matches);
    }

    @Test
    void getFmtDlgNum_scenario_fail_3() {
        Boolean matches = FMT_DLG_NUM3_FAIL.matches(regex);
        assertEquals(Boolean.FALSE, matches);
    }

    @Test
    void getFmtDlgNum_scenario_fail_4() {
        Boolean matches = FMT_DLG_NUM4_FAIL.matches(regex);
        assertEquals(Boolean.FALSE, matches);
    }

}
