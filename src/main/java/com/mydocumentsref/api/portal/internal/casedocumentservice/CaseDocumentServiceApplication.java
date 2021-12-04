package com.mydocumentsref.api.portal.internal.casedocumentservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

/**
 * The type Case document service application.
 */
@SpringBootApplication
@ComponentScan(basePackages = {
            "com.mydocumentsref.api"}, excludeFilters = @ComponentScan.Filter(type = FilterType.REGEX, pattern = "com.nswlrs" +
            ".api.common.commonservice.external.*"))
public class CaseDocumentServiceApplication {

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(CaseDocumentServiceApplication.class, args);
    }

}
