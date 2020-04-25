package com.example.lab1;

import lombok.Builder;
import lombok.Value;

import java.util.Date;


/*  @Value annotation is @Data + immutable (cool beans:-). */
// @Builder per implementare il pattern corrispondente

@Value
@Builder
public class RegistrationDetails {
    private String name;

    private String surname;

    private String email;

    private String password;

    private Boolean privacy;

    private Date registrationDate;

}