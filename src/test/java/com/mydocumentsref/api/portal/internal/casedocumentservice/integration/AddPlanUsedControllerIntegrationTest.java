package com.mydocumentsref.api.portal.internal.casedocumentservice.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Collections;

import com.mydocumentsref.api.common.commonservice.util.Constants;
import com.mydocumentsref.api.portal.internal.casedocumentservice.config.IntegrationTest;
import com.mydocumentsref.api.portal.internal.casedocumentservice.model.AddPlansUsedPacketImage;
import com.mydocumentsref.api.portal.internal.casedocumentservice.services.output.AddPlanUsedResponse;
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
public class AddPlanUsedControllerIntegrationTest {

    @Autowired
    TestRestTemplate restTemplate = new TestRestTemplate();

    @LocalServerPort
    private int port;

    @Test
    public void addPlanUsed() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add(Constants.X_ACTIONED_BY, "loggedInUser");
        headers.add(Constants.X_CORRELATION_ID, "addPlanUsed");

        String requestJson = """                                       
                    {"packet_id":1,"user_code":"psmith","plan_number":"DP123456","plan_images":[{"image_category":"88b","image_subtype":"B","document_name":"SECTION 88B INSTRUMENT"},{"image_category":"Plan","image_subtype":"P","document_name":"PLAN"}]}
                       """;
        HttpEntity<String> entity = new HttpEntity<>(requestJson, headers);

        ResponseEntity<AddPlanUsedResponse> response = restTemplate.exchange(
                    createURLWithPort("/plan-used"), HttpMethod.POST, entity, AddPlanUsedResponse.class);

        for (AddPlansUsedPacketImage addPlansUsedPacketImage : response.getBody().getPlans().get(0).getPlan()) {
            if (addPlansUsedPacketImage.getRecordsInserted() > 0 || addPlansUsedPacketImage.getRecordUpdated() > 0) {
                assertEquals(201, response.getStatusCodeValue());

            } else if (StringUtils.isNotEmpty(addPlansUsedPacketImage.getResponseErrorCode())) {
                assertEquals(422, response.getStatusCodeValue());
            } else {
                assertEquals(200, response.getStatusCodeValue());

            }

        }
    }

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + "/portal-internal/api/case-document/v1" + uri;
    }

}
