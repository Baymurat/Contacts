package com.itechart.contacts.core.attachment.service;

import com.itechart.contacts.core.attachment.dto.AttachmentDto;
import com.itechart.contacts.core.attachment.dto.SaveAttachmentDto;
import com.itechart.contacts.core.attachment.entity.Attachment;
import com.itechart.contacts.core.attachment.repository.AttachmentRepository;
import com.itechart.contacts.core.person.repository.PersonRepository;
import com.itechart.contacts.core.utils.ObjectMapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class AttachmentServiceImpl implements AttachmentService {

    private AttachmentRepository attachmentRepository;
    private PersonRepository personRepository;

    @Autowired
    public AttachmentServiceImpl(AttachmentRepository attachmentRepository, PersonRepository personRepository) {
        this.attachmentRepository = attachmentRepository;
        this.personRepository = personRepository;
    }

    public AttachmentDto create(SaveAttachmentDto saveAttachment) {
        Attachment attachment = ObjectMapperUtils.map(saveAttachment, Attachment.class);
        attachment.setPerson(personRepository.getOne(saveAttachment.getPersonId()));
        attachment = attachmentRepository.save(attachment);
        return ObjectMapperUtils.map(attachment, AttachmentDto.class);
    }

    public void delete(Long id) {
        attachmentRepository.deleteById(id);
    }

    @Transactional
    public void deleteByContactId(Long id) {
        attachmentRepository.deleteAllByPersonId(id);
    }

    @Transactional
    public AttachmentDto update(long id, SaveAttachmentDto saveAttachmentDto) {
        Attachment attachment = attachmentRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        ObjectMapperUtils.map(saveAttachmentDto, attachment);
        return ObjectMapperUtils.map(attachment, AttachmentDto.class);
    }

    public AttachmentDto getAttachment(Long id) {
        Attachment attachment = attachmentRepository.findById(id).orElseThrow(IllegalStateException::new);
        return ObjectMapperUtils.map(attachment, AttachmentDto.class);
    }
}
