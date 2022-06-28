/*
*
* The City entity which is used to store city info.
*
*/

package com.dreamix.springcities.common.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

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
    @NonNull
    private String name;
    @NonNull
    @Column(columnDefinition="TEXT", length = 2048)
    private String photo;

}
