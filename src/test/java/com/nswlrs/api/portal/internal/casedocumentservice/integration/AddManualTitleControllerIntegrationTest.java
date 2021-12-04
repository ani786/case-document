package com.nswlrs.api.portal.internal.casedocumentservice.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Collections;

import com.nswlrs.api.common.commonservice.util.Constants;
import com.nswlrs.api.portal.internal.casedocumentservice.config.IntegrationTest;
import com.nswlrs.api.portal.internal.casedocumentservice.services.output.AddManualTitleResponse;
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
public class AddManualTitleControllerIntegrationTest {

    @Autowired
    TestRestTemplate restTemplate = new TestRestTemplate();

    @LocalServerPort
    private int port;

    @Test
    public void addManualTitle() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add(Constants.X_ACTIONED_BY, "loggedInUser");
        headers.add(Constants.X_CORRELATION_ID, "addManualTitle");

        String requestJson = """                                       
                    {"user_code":"ewang","add_manual_title":{"packet_id":"8","title_ref":"12345-123"}}
                     """;
        HttpEntity<String> entity = new HttpEntity<>(requestJson, headers);

        ResponseEntity<AddManualTitleResponse> response = restTemplate.exchange(
                    createURLWithPort("/manual-title"), HttpMethod.POST, entity, AddManualTitleResponse.class);

        if (response.getBody().getRecordsInserted() > 0 || response.getBody().getRecordUpdated() > 0) {
            assertEquals(201, response.getStatusCodeValue());
        } else if (StringUtils.isNotEmpty(response.getBody().getResponseErrorCode())) {
            assertEquals(422, response.getStatusCodeValue());
        } else {
            assertEquals(200, response.getStatusCodeValue());

        }
    }

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + "/portal-internal/api/case-document/v1" + uri;
    }

}
