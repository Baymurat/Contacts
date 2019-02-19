package com.itechart.contacts.core.person.repository;

import com.itechart.contacts.core.person.dto.PersonFilter;
import com.itechart.contacts.core.person.entity.Person;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.Collections;
import java.util.List;

import static com.itechart.contacts.core.person.entity.QPerson.person;
import static com.itechart.contacts.core.phone.entity.QPhone.phone;


public class PersonCustomRepositoryImpl extends QuerydslRepositorySupport implements PersonCustomRepository {
    /**
     * Creates a new {@link QuerydslRepositorySupport} instance for the given domain type.
     */
    public PersonCustomRepositoryImpl() {
        super(Person.class);
    }


    @Override
    public Page<Person> search(PersonFilter personFilter, Pageable pageable) {

        BooleanBuilder where = new BooleanBuilder();

        if (StringUtils.isNotBlank(personFilter.getFirstAndLastName())) {
            where.and(
                    person.name.contains(personFilter.getFirstAndLastName())
                            .or(person.surName.contains(personFilter.getFirstAndLastName()))
            );
        }
        if (StringUtils.isNotBlank(personFilter.getCurrentJob())) {
            where.and(person.currentJob.contains(personFilter.getCurrentJob()));
        }
        if (personFilter.getPhoneNumber() != 0) {
            where.and(phone.codeOfCountry.stringValue().contains(String.valueOf(personFilter.getPhoneNumber()))
                    .or(phone.codeOfOperator.stringValue().contains(String.valueOf(personFilter.getPhoneNumber()))
                            .or(phone.phoneNumber.stringValue().contains(String.valueOf(personFilter.getPhoneNumber())))));
        }

        JPQLQuery<Person> query = from(person)
                .where(where)
                .select(person)
                .leftJoin(phone)
                .on(person.id.eq(phone.person.id))
                .groupBy(person.id)
                .orderBy(person.name.asc());

        long total = query.fetchCount();
        List<Person> items = Collections.emptyList();
        if (total > 0) {
            getQuerydsl().applyPagination(pageable, query);
            items = query.fetch();
        }
        return new PageImpl<>(items, pageable, total);
    }
}
