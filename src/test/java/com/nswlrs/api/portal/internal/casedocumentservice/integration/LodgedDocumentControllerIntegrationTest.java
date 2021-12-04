package com.mydocumentsref.api.portal.internal.casedocumentservice.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Collections;

import com.mydocumentsref.api.common.commonservice.util.Constants;
import com.mydocumentsref.api.portal.internal.casedocumentservice.config.IntegrationTest;
import com.mydocumentsref.api.portal.internal.casedocumentservice.services.output.LodgedDocumentResponse;
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
public class LodgedDocumentControllerIntegrationTest {

    @Autowired
    TestRestTemplate restTemplate = new TestRestTemplate();

    @LocalServerPort
    private int port;

    @Test
    public void getLodgeDocuments() {
        HttpHeaders headers = new HttpHeaders();
        headers.add(Constants.X_ACTIONED_BY, "loggedInUser");
        headers.add(Constants.X_CORRELATION_ID, "getLodgeDocuments");
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        ResponseEntity<LodgedDocumentResponse> response = restTemplate.exchange(
                    createURLWithPort("/lodged-documents/55"), HttpMethod.GET, new HttpEntity<>(headers),
                    LodgedDocumentResponse.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + "/portal-internal/api/case-document/v1" + uri;
    }
}
