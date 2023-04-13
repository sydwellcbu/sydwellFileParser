/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eviro.assessment.grad001.sydwellNgwenya.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class AccountProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private long id;
    private String name;
    private String surname;
    private String httpImageLink;

    public AccountProfile(String name, String surname, String httpImageLink) {
        this.name = name;
        this.surname = surname;
        this.httpImageLink = httpImageLink;
    }

}
