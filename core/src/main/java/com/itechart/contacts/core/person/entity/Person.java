package com.itechart.contacts.core.person.entity;

import com.itechart.contacts.core.attachment.entity.Attachment;
import com.itechart.contacts.core.phone.entity.Phone;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.sql.Date;

import java.util.List;

/**
 * Created by Admin on 11.09.2018
 */
@Entity
@Getter
@Setter
@Table(name = "persons")
public class Person implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "familystatus")
    private String familyStatus;

    @Column(name = "currentjob")
    private String currentJob;

    @Column(name = "street_house_apart")
    private String streetHouseApart;

    @Column(name = "p_index")
    private String index;

    @Column(name = "datebirth")
    private Date birthDate;

    @Column(name = "surname")
    private String surName;

    @Column(name = "middlename")
    private String middleName;

    @Column(name = "website")
    private String webSite;

    private String name;
    private String gender;
    private String citizenship;
    private String email;
    private String country;
    private String city;

    @OneToMany(mappedBy = "person", fetch = FetchType.LAZY)
    private List<Phone> phones;

    @OneToMany(mappedBy = "person", fetch = FetchType.LAZY/*, cascade = CascadeType.ALL, orphanRemoval = true*/)
    private List<Attachment> attachments;

    public Person() {
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", familyStatus='" + familyStatus + '\'' +
                ", currentJob='" + currentJob + '\'' +
                ", streetHouseApart='" + streetHouseApart + '\'' +
                ", index='" + index + '\'' +
                ", birthDate=" + birthDate +
                ", surName='" + surName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", webSite='" + webSite + '\'' +
                ", name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", citizenship='" + citizenship + '\'' +
                ", email='" + email + '\'' +
                ", country='" + country + '\'' +
                ", city='" + city + '\'' +
                /*", phones=" + phones +
                ", attachments=" + attachments +*/
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Person)) return false;
        Person person = (Person) o;
        return this.id == (person.getId()) &&
                this.familyStatus.equals(person.getFamilyStatus()) &&
                this.currentJob.equals(person.getCurrentJob()) &&
                this.streetHouseApart.equals(person.getStreetHouseApart()) &&
                this.index.equals(person.getIndex()) &&
                //this.birthDate.equals(person.getBirthDate()) &&
                this.surName.equals(person.getSurName()) &&
                this.middleName.equals(person.getMiddleName()) &&
                this.webSite.equals(person.getWebSite()) &&
                this.name.equals(person.getName()) &&
                this.gender.equals(person.getGender()) &&
                this.citizenship.equals(person.getCitizenship()) &&
                this.email.equals(person.getEmail()) &&
                this.country.equals(person.getCountry()) &&
                this.city.equals(person.getCity());
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + name.hashCode();
        result = 31 * result + surName.hashCode();
        result = 31 * result + middleName.hashCode();
        result = 31 * result + familyStatus.hashCode();
        result = 31 * result + currentJob.hashCode();
        result = 31 * result + streetHouseApart.hashCode();
        result = 31 * result + index.hashCode();
        result = 31 * result + birthDate.hashCode();
        result = 31 * result + webSite.hashCode();
        result = 31 * result + gender.hashCode();
        result = 31 * result + citizenship.hashCode();
        result = 31 * result + email.hashCode();
        result = 31 * result + country.hashCode();
        result = 31 * result + city.hashCode();

        return result;
    }
}