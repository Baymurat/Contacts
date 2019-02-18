package com.itechart.contacts.core.attachment.entity;


import com.itechart.contacts.core.person.entity.Person;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.Size;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Size(min = 3, max = 60)
    @Column(name = "filename")
    private String fileName;

    @Column(name = "loaddate")
    private Date loadDate;

    private String comments;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "persons_id")
    private Person person;


    public Attachment() {
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (!(o instanceof Attachment)) {
            return false;
        }

        Attachment a = (Attachment) o;

        return a.getComments().equals(this.comments) &&
                a.getFileName().equals(this.fileName)
                //a.getId() ==  this.id &&
                //a.getPerson() == this.person &&
                //a.getLoadDate().equals(this.getLoadDate());
        ;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + fileName.hashCode();
        result = 31 * result + loadDate.hashCode();
        result = 31 * result + comments.hashCode();
        return result;
    }
}
