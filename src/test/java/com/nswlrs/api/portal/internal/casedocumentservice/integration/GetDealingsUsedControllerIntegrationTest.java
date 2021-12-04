package com.nswlrs.api.portal.internal.casedocumentservice.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Collections;

import com.nswlrs.api.common.commonservice.util.Constants;
import com.nswlrs.api.portal.internal.casedocumentservice.config.IntegrationTest;
import com.nswlrs.api.portal.internal.casedocumentservice.services.output.GetDealingsUsedResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@IntegrationTest
@ActiveProfiles("dev")
public class GetDealingsUsedControllerIntegrationTest {

    @Autowired
    TestRestTemplate restTemplate = new TestRestTemplate();

    @LocalServerPort
    private int port;

    @Test
    public void getDealingsUsed() {
        HttpHeaders headers = new HttpHeaders();
        headers.add(Constants.X_ACTIONED_BY, "loggedInUser");
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.add(Constants.X_CORRELATION_ID, "getDealingsUsed");

        ResponseEntity<GetDealingsUsedResponse> response = restTemplate.exchange(
                    createURLWithPort("/dealings-used/1"), HttpMethod.GET, new HttpEntity<>(headers),
                    GetDealingsUsedResponse.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + "/portal-internal/api/case-document/v1" + uri;
    }

}
