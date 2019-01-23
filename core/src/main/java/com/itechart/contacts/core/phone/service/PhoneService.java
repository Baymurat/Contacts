package com.itechart.contacts.core.phone.service;

import com.itechart.contacts.core.phone.dto.PhoneDto;
import com.itechart.contacts.core.phone.dto.SavePhoneDto;


public interface PhoneService {

    PhoneDto create(SavePhoneDto savePhoneDto);

    PhoneDto update(long id, SavePhoneDto savePhone);

    PhoneDto getPhone(Long id);

    void delete(Long id);

    void deleteByContactId(Long id);

}
