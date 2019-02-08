package com.itechart.contacts.core.filemanager;

import com.itechart.contacts.core.email.entity.MessageEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface FileManageService {
    void uploadFile(long personsId, long attachId, MultipartFile file) throws IOException;

    void savePhoto(long personsId, MultipartFile photo) throws IOException;

    String getPhoto(int personId);

    File getFile(int personId, int attachId);

    List<MessageEntity> getPatterns(String path) throws IOException;

    void deleteFiles(int personId, long attachId);

    void deleteUsers(long[] persons);
}