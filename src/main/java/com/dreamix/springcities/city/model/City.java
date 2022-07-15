/*
*
* The City entity which is used to store city info.
*
*/

package com.dreamix.springcities.city.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "cities")
public class City {

    @Id
    @NonNull
    private Long id;

    @NotBlank
    @Size(max = 255)
    @NonNull
    private String name;

    @NotBlank
    @Size(max = 2048)
    @NonNull
    @Column(columnDefinition="TEXT", length = 2048)
    private String photo;

}
