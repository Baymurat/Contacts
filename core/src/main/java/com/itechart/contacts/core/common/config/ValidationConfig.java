package com.itechart.contacts.core.common.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

import javax.validation.Validation;
import javax.validation.ValidatorFactory;

@Configuration
public class ValidationConfig {

    @Bean
    public ValidatorFactory validatorFactory() {
        return Validation.byDefaultProvider().configure().buildValidatorFactory();
    }

    @Bean
    @Autowired
    public MethodValidationPostProcessor validationProcessor(ValidatorFactory factory) {
        MethodValidationPostProcessor postProcessor = new MethodValidationPostProcessor();
        postProcessor.setValidatorFactory(factory);
        return postProcessor;
    }
}
