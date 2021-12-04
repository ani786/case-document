package com.mydocumentsref.api.portal.internal.casedocumentservice.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Collections;

import com.mydocumentsref.api.common.commonservice.util.Constants;
import com.mydocumentsref.api.portal.internal.casedocumentservice.config.IntegrationTest;
import com.mydocumentsref.api.portal.internal.casedocumentservice.services.output.GetFormattedTitleSearchResponse;
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
public class GetFormattedTitleSearchControllerIntegrationTest {

    @Autowired
    TestRestTemplate restTemplate = new TestRestTemplate();

    @LocalServerPort
    private int port;

    @Test
    public void getFormattedTitleSearch() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add(Constants.X_ACTIONED_BY, "loggedInUser");
        headers.add(Constants.X_CORRELATION_ID, "getFormattedTitleSearch");

        String requestJson = """                                       
                    {"user_code":"aelali","formatted_title_search":{"packet_id":"1","title_ref":"1/23424","search_type":"TitleSearch"}}
                      """;

        HttpEntity<String> entity = new HttpEntity<>(requestJson, headers);

        ResponseEntity<GetFormattedTitleSearchResponse> response = restTemplate.exchange(
                    createURLWithPort("/search-fmtd-title"), HttpMethod.PUT, entity,
                    GetFormattedTitleSearchResponse.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + "/portal-internal/api/case-document/v1" + uri;
    }

}
