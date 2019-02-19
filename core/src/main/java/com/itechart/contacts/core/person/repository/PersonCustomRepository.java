package com.itechart.contacts.core.person.repository;

import com.itechart.contacts.core.person.dto.PersonFilter;
import com.itechart.contacts.core.person.dto.PersonPreviewDto;
import com.itechart.contacts.core.person.entity.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface PersonCustomRepository  {

    Page<Person> search(PersonFilter personFilter, Pageable pageable);
}
