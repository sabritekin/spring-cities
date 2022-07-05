/*
 *
 * The Credential class which is used for authentication purposes.
 *
 */


package com.dreamix.springcities.common.security.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CredentialsDTO {

    private String userName;
    private String password;

}
