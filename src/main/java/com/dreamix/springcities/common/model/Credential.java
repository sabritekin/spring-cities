/*
 *
 * The Credential class which is used for authentication purposes.
 *
 */


package com.dreamix.springcities.common.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Credential {

    private String userName;
    private String password;

}
