package com.example.lab1;

import lombok.Data;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Collection;


/*
The Model is a Map that can contain simple attributes,
but in the case of Form state that can change a good pattern to follow is
representing the Form state by a single object — the Command Object (aka. form backing Bean).
*/

@Data
public class RegistrationCommand {

    @Size(min = 3)
    private String name;

    @Size(min = 3)
    private String surname;

    @Email
    private String email;

    @Size(min = 3, max = 12)
    private String password;

    @Size(min = 3, max = 12)
    private String repeatpassword;

    private Boolean privacy;


}
