package com.itechart.contacts.core.phone.service;

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

    @Autowired
    public PhoneServiceImpl(PhoneRepository phoneRepository) {
        this.phoneRepository = phoneRepository;
    }

    public PhoneDto create(SavePhoneDto savePhoneDto) {
        Phone phone = ObjectMapperUtils.map(savePhoneDto, Phone.class);
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
