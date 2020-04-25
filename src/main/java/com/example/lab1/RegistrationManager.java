package com.example.lab1;

import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

/*
@Component, così da renderne disponibile un’istanza
come singleton che possa essere iniettata dove occorre.
*/
@Component
public class RegistrationManager extends ConcurrentHashMap<String,
        RegistrationDetails> {
}
