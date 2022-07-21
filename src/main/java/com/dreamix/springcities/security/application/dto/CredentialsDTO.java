/*
 *
 * The Credential class which is used for authentication purposes.
 *
 */


package com.dreamix.springcities.security.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CredentialsDTO {

    @NotBlank
    @Size(min = 8, max = 16)
    private String userName;
    @NotBlank
    @Size(min = 8, max = 16)
    private String password;

}
