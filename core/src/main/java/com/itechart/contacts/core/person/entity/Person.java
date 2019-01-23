package com.itechart.contacts.core.person.entity;

import com.itechart.contacts.core.attachment.entity.Attachment;
import com.itechart.contacts.core.phone.entity.Phone;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Created by Admin on 11.09.2018
 */
@Entity
@Getter
@Setter
@Table(name = "persons")
public class Person implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "familystatus")
    private String familyStatus;

    @Column(name = "currentjob")
    private String currentJob;

    @Column(name = "street_house_apart")
    private String streetHouseApart;

    @Column(name = "p_index")
    private String index;

    @Column(name = "datebirth")
    private String birthDate;

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

    @OneToMany(targetEntity = Phone.class, fetch = FetchType.LAZY)
    private List<Phone> phones;

    @OneToMany(targetEntity = Attachment.class, fetch = FetchType.LAZY)
    private List<Attachment> attachments;

    public Person() {}
}