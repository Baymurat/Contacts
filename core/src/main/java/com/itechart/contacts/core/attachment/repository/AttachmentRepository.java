package com.itechart.contacts.core.attachment.repository;

import com.itechart.contacts.core.attachment.entity.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttachmentRepository extends JpaRepository<Attachment, Long> {

    Attachment findAttachmentByPersonId(Long id);

    void deleteAllByPersonId(Long id);
}
