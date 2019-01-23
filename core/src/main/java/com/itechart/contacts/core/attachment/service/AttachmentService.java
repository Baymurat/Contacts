package com.itechart.contacts.core.attachment.service;

import com.itechart.contacts.core.attachment.dto.AttachmentDto;
import com.itechart.contacts.core.attachment.dto.SaveAttachmentDto;

public interface AttachmentService {

    AttachmentDto create(SaveAttachmentDto saveAttachment);

    AttachmentDto update(long id, SaveAttachmentDto saveAttachment);

    AttachmentDto getAttachment(Long id);

    void delete(Long id);

    void deleteByContactId(Long id);


}