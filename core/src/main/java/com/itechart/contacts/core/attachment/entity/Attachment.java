package com.itechart.contacts.core.attachment.entity;


import com.itechart.contacts.core.person.entity.Person;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

/**
 * Created by Admin on 12.09.2018
 */
@Entity
@Getter
@Setter
@Table(name = "attachments")
public class Attachment implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "filename")
    private String fileName;

    @Column(name = "loaddate")
    private Date loadDate;

    private String comments;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "persons_id")
    private Person person;

    public Attachment() {}
}
