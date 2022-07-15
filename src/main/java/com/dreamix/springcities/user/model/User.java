/*
 *
 * The User entity which is used to store user info.
 *
 */

package com.dreamix.springcities.user.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Data
@Entity
@Table(name = "users")
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    private Long id;
    @NonNull
    @NotBlank
    private String userName;
    @NonNull
    @NotBlank
    private String password;
    @NonNull
    @NotBlank
    private String role;

    public User(String userName, String password, String role) {
        this.userName = userName;
        this.password = password;
        this.role = role;
    }

}
