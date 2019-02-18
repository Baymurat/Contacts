package com.itechart.contacts.core.phone.entity;

import com.itechart.contacts.core.person.entity.Person;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * Created by Admin on 12.09.2018
 */
@Entity
@Getter
@Setter
@Table(name = "phones")
public class Phone implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

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

    public Phone() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Phone)) return false;
        Phone phone = (Phone) o;
        return getPhoneNumber() == phone.getPhoneNumber() &&
                getCodeOfCountry() == phone.getCodeOfCountry() &&
                getCodeOfOperator() == phone.getCodeOfOperator() &&
                //getId() == (phone.getId()) &&
                //getPerson().equals(phone.getPerson()) &&
                getType().equals(phone.getType()) &&
                getComments().equals(phone.getComments());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getPerson(), getPhoneNumber(), getCodeOfCountry(), getCodeOfOperator(), getType(), getComments());
    }

}
