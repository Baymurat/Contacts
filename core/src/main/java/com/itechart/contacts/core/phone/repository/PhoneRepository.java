package com.itechart.contacts.core.phone.repository;

import com.itechart.contacts.core.phone.entity.Phone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhoneRepository extends JpaRepository<Phone, Long> {

    void deleteAllByPersonId(Long id);
}
