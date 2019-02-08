package com.itechart.contacts.core.phone.service;

import com.itechart.contacts.core.person.repository.PersonRepository;
import com.itechart.contacts.core.phone.dto.PhoneDto;
import com.itechart.contacts.core.phone.dto.SavePhoneDto;
import com.itechart.contacts.core.phone.entity.Phone;
import com.itechart.contacts.core.phone.repository.PhoneRepository;
import com.itechart.contacts.core.utils.ObjectMapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class PhoneServiceImpl implements PhoneService {

    private PhoneRepository phoneRepository;
    private PersonRepository personRepository;

    @Autowired
    public PhoneServiceImpl(PhoneRepository phoneRepository, PersonRepository personRepository) {
        this.phoneRepository = phoneRepository;
        this.personRepository = personRepository;
    }

    public PhoneDto create(SavePhoneDto savePhoneDto) {
        Phone phone = ObjectMapperUtils.map(savePhoneDto, Phone.class);
        phone.setPerson(personRepository.getOne(savePhoneDto.getPersonId()));
        phone = phoneRepository.save(phone);
        return ObjectMapperUtils.map(phone, PhoneDto.class);
    }

    public PhoneDto getPhone(Long id) {
        Phone phone = phoneRepository.findById(id).orElseThrow(IllegalStateException::new);
        return ObjectMapperUtils.map(phone, PhoneDto.class);
    }

    public void delete(Long id) {
        phoneRepository.deleteById(id);
    }

    @Transactional
    public void deleteByContactId(Long id) {
        phoneRepository.deleteAllByPersonId(id);
    }

    @Transactional
    public PhoneDto update(long id, SavePhoneDto savePhone) {
        Phone phone =  phoneRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        ObjectMapperUtils.map(savePhone, phone);
        return ObjectMapperUtils.map(phone, PhoneDto.class);
    }
}
