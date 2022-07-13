/*
*
* The City entity which is used to store city info.
*
*/

package com.dreamix.springcities.city.model;

import com.dreamix.springcities.user.model.User;
import lombok.*;
import org.springframework.data.jpa.domain.AbstractAuditable;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cities")
public class City {

    @Id
    private Long id;
    @NotBlank
    @Max(255)
    private String name;
    @NotBlank
    @Max(2048)
    @Column(columnDefinition="TEXT", length = 2048)
    private String photo;

}
