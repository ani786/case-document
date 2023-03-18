package com.mydocumentsref.api.portal.internal.casedocumentservice.services.input;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

class AddDeedUsedTest {

    private static final long PACKET_ID = 55;
    private static final String ENCUM_NUM1_CAPS = "BK1 NO1";
    private static final String ENCUM_NUM1 = "bk1 no1";
    private static final String ENCUM_NUM2 = "BK11NO9";
    private static final String ENCUM_NUM3 = "BK12NO12";
    private static final String ENCUM_NUM4 = "BK123NO123";
    private static final String ENCUM_NUM5 = "BK1234NO1234";
    private static final String ENCUM_NUM1_FAIL = "B1NO1";
    private static final String ENCUM_NUM2_FAIL = "BK1N1";
    private static final String ENCUM_NUM3_FAIL = "BK1234567NO1";
    private static final String ENCUM_NUM4_FAIL = "B0NO0";
    private static final String regex = "^(^[Bb][Kk] ?([0-9]{1,4}) ?[Nn][Oo] ?([0-9]{1,4})$)";
    /* private static final String regex = "^([A-Z]{2}[0-9]{6}|^[A-Z][0-9]{1,6}|^[0-9]{1,7}$)";*/

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
    void stringUtilsTest1() {
        String output1 = StringUtils.substringBetween(ENCUM_NUM1.toUpperCase(), "K", "N");
        String output2 = StringUtils.substringAfter(ENCUM_NUM1.toUpperCase(), "O");
        assertEquals("1", output1.trim());
        assertEquals("1", output2.trim());
    }

    @Test
    void stringUtilsTest2() {
        String output1 = StringUtils.substringBetween(ENCUM_NUM2, "K", "N");
        String output2 = StringUtils.substringAfter(ENCUM_NUM2, "O");
        assertEquals("11", output1);
        assertEquals("9", output2);
    }

    @Test
    void stringUtilsTest3() {
        String output1 = StringUtils.substringBetween(ENCUM_NUM5, "K", "N");
        String output2 = StringUtils.substringAfter(ENCUM_NUM5, "O");
        assertEquals("1234", output1);
        assertEquals("1234", output2);
    }

    @Test
    void getEncumNum_scenario_1() {

        Boolean matches = ENCUM_NUM1.matches(regex);
        assertEquals(Boolean.TRUE, matches);
    }

    @Test
    void getEncumNum_scenario_2() {
        Boolean matches = ENCUM_NUM2.matches(regex);
        assertEquals(Boolean.TRUE, matches);
    }

    @Test
    void getEncumNum_scenario_3() {
        Boolean matches = ENCUM_NUM3.matches(regex);
        assertEquals(Boolean.TRUE, matches);
    }

    @Test
    void getEncumNum_scenario_4() {
        Boolean matches = ENCUM_NUM4.matches(regex);
        assertEquals(Boolean.TRUE, matches);
    }

    @Test
    void getEncumNum_scenario_5() {
        Boolean matches = ENCUM_NUM5.matches(regex);
        assertEquals(Boolean.TRUE, matches);
    }

    @Test
    void getEncumNum_scenario_fail_1() {
        Boolean matches = ENCUM_NUM1_FAIL.matches(regex);
        assertEquals(Boolean.FALSE, matches);
    }

    @Test
    void getEncumNum_scenario_fail_2() {
        Boolean matches = ENCUM_NUM2_FAIL.matches(regex);
        assertEquals(Boolean.FALSE, matches);
    }

    @Test
    void getEncumNum_scenario_fail_3() {
        Boolean matches = ENCUM_NUM3_FAIL.matches(regex);
        assertEquals(Boolean.FALSE, matches);
    }

    @Test
    void getEncumNum_scenario_fail_4() {
        Boolean matches = ENCUM_NUM4_FAIL.matches(regex);
        assertEquals(Boolean.FALSE, matches);
    }

}
