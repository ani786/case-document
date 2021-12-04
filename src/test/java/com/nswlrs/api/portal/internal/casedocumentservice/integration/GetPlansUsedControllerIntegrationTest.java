package com.mydocumentsref.api.portal.internal.casedocumentservice.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Collections;

import com.mydocumentsref.api.common.commonservice.util.Constants;
import com.mydocumentsref.api.portal.internal.casedocumentservice.config.IntegrationTest;
import com.mydocumentsref.api.portal.internal.casedocumentservice.services.output.GetPlansUsedResponse;
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
public class GetPlansUsedControllerIntegrationTest {

    @Autowired
    TestRestTemplate restTemplate = new TestRestTemplate();

    @LocalServerPort
    private int port;

    @Test
    public void getPlansUsed() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add(Constants.X_ACTIONED_BY, "loggedInUser");
        headers.add(Constants.X_CORRELATION_ID, "getPlansUsed");

        String requestJson = """                                       
                    {"plan_used":{"packet_id":"111"}}
                         """;

        HttpEntity<String> entity = new HttpEntity<>(requestJson, headers);

        ResponseEntity<GetPlansUsedResponse> response = restTemplate.exchange(
                    createURLWithPort("/plans-used"), HttpMethod.PUT, entity,
                    GetPlansUsedResponse.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + "/portal-internal/api/case-document/v1" + uri;
    }

}
