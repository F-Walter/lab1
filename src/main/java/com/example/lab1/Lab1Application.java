package com.example.lab1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@SpringBootApplication
public class Lab1Application {

    //"An application context delegates the message resolution to a bean with the exact name messageSource.
    // ReloadableResourceBundleMessageSource is the most common MessageSource implementation that resolves
    // messages from resource bundles for different locales: "


    public static void main(String[] args) {
        SpringApplication.run(Lab1Application.class, args);
    }

}
