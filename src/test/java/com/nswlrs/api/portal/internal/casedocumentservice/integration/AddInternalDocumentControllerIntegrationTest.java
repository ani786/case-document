package com.nswlrs.api.portal.internal.casedocumentservice.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Collections;

import com.nswlrs.api.common.commonservice.util.Constants;
import com.nswlrs.api.portal.internal.casedocumentservice.config.IntegrationTest;
import com.nswlrs.api.portal.internal.casedocumentservice.model.AddPlansUsedPacketImage;
import com.nswlrs.api.portal.internal.casedocumentservice.services.output.AddInternalDocumentResponse;
import com.nswlrs.api.portal.internal.casedocumentservice.services.output.AddPlanUsedResponse;
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
public class AddInternalDocumentControllerIntegrationTest {

    @Autowired
    TestRestTemplate restTemplate = new TestRestTemplate();

    @LocalServerPort
    private int port;

    @Test
    public void addInternalDocument() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add(Constants.X_ACTIONED_BY, "loggedInUser");
        headers.add(Constants.X_CORRELATION_ID, "addInternalDocument");

        String requestJson = """                                       
                  {"user_code":"ewang","add_internal_document":{"packet_id":"8","image_category":"imagCatVal","file_name":"fileNameVal","path":"pathVal","note":"noteVal"}}
                       """;
        HttpEntity<String> entity = new HttpEntity<>(requestJson, headers);

        ResponseEntity<AddInternalDocumentResponse> response = restTemplate.exchange(
                    createURLWithPort("/internal-document"), HttpMethod.POST, entity, AddInternalDocumentResponse.class);


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
