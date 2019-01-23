package com.itechart.contacts.core.phone.entity;

import com.itechart.contacts.core.person.entity.Person;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Admin on 12.09.2018
 */
@Entity
@Getter
@Setter
@Table(name = "phones")
public class Phone implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(targetEntity = Person.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "persons_id")
    private Person person;

    @Column(name = "phonenumber")
    private int phoneNumber;

    @Column(name = "countrycode")
    private int codeOfCountry;

    @Column(name = "operatorcode")
    private int codeOfOperator;

    private String type;
    private String comments;

    public Phone() {}
}
