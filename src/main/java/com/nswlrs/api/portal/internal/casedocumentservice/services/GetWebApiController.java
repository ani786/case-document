package com.nswlrs.api.portal.internal.casedocumentservice.services;

import javax.validation.Valid;

import com.nswlrs.api.common.commonservice.capi.facade.WebApiFacade;
import com.nswlrs.api.common.commonservice.capi.facade.WebApiRequest;
import com.nswlrs.api.common.commonservice.capi.model.ApiResultPages;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The type Get web api controller.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
@Validated
@Slf4j
public class GetWebApiController {

    private final WebApiFacade webApiFacade;

    /**
     * Gets web.
     *
     * @param webApiRequest the web api request
     * @return the web
     */
    @PostMapping(path = "/webapi")
    public ResponseEntity<ApiResultPages> getWeb(@Valid @RequestBody final WebApiRequest webApiRequest) {
        log.info("webApiRequest  {}", webApiRequest);
        return new ResponseEntity<>(webApiFacade.runProcess(webApiRequest), HttpStatus.OK);
    }
}
