package com.itechart.contacts.core.person.repository;

import com.itechart.contacts.core.person.entity.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long>, PersonCustomRepository, QuerydslPredicateExecutor<Person> {

    List<Person> findAllByBirthDate(Date date);

    Page<Person> findAllByUserId(long id, Pageable pageable);

    Person findByIdAndUserId(long personId, long userId);
}
