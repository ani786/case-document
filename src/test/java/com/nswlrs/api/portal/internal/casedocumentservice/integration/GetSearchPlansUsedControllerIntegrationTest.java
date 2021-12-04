package com.mydocumentsref.api.portal.internal.casedocumentservice.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Collections;

import com.mydocumentsref.api.common.commonservice.util.Constants;
import com.mydocumentsref.api.portal.internal.casedocumentservice.config.IntegrationTest;
import com.mydocumentsref.api.portal.internal.casedocumentservice.services.output.GetSearchPlansUsedResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@IntegrationTest
@ActiveProfiles("dev")
public class GetSearchPlansUsedControllerIntegrationTest {

    @Autowired
    TestRestTemplate restTemplate = new TestRestTemplate();

    @LocalServerPort
    private int port;

    @Test
    public void getSearchPlansUsed() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add(Constants.X_ACTIONED_BY, "loggedInUser");
        headers.add(Constants.X_CORRELATION_ID, "getSearchPlansUsed");

        String requestJson = """                                       
                    {"search_plan_used":{"packet_id":"228","plan_number":"DP123456"}}
                        """;

        HttpEntity<String> entity = new HttpEntity<>(requestJson, headers);

        ResponseEntity<GetSearchPlansUsedResponse> response = restTemplate.exchange(
                    createURLWithPort("/search-plans-used"), HttpMethod.PUT, entity,
                    GetSearchPlansUsedResponse.class);

        if (StringUtils.isNotEmpty(response.getBody().getSearchPlansUsed().get(0).getResponseErrorCode())) {
            assertEquals(422, response.getStatusCodeValue());
        } else {
            assertEquals(200, response.getStatusCodeValue());

        }
    }

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + "/portal-internal/api/case-document/v1" + uri;
    }

}
