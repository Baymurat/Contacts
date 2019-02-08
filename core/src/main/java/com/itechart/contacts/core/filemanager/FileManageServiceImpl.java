package com.itechart.contacts.core.filemanager;

import com.itechart.contacts.core.email.entity.MessageEntity;
import com.itechart.contacts.core.utils.error.CustomLogger;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.ArrayList;

@Service
public class FileManageServiceImpl implements FileManageService{
    private ResourceBundle resourceBundle = ResourceBundle.getBundle("application");
    private final String rootPath = resourceBundle.getString("file.upload-dir") + File.separator;
    private final String uploadFolder = File.separator + "upload_folder" + File.separator;

    public void uploadFile(long personsId, long attachId, MultipartFile file) throws IOException {
        String path = rootPath + personsId + uploadFolder + "attachments" + File.separator + attachId + File.separator;
        makeDirs(path);
        file.transferTo(new File(path + file.getOriginalFilename()));
    }

    public void savePhoto(long personsId, MultipartFile photo) throws IOException {
        String path = rootPath + personsId + uploadFolder + "photo" + File.separator;
        makeDirs(path);
        String photoName = photo.getOriginalFilename();
        String extension = photoName.split("\\.")[1];
        photo.transferTo(new File(path + "photo." + extension));
    }

    public String getPhoto(int personId) {
        String path = rootPath + personId + uploadFolder + "photo";
        File directory = new File(path);
        if (directory.exists() && directory.list()[0].contains("photo")) {
            File file = directory.listFiles()[0];
            try (FileInputStream fileInputStreamReader = new FileInputStream(file)){
                byte[] bytes = new byte[(int)file.length()];
                fileInputStreamReader.read(bytes);
                return new String(Base64.getEncoder().encode(bytes), StandardCharsets.UTF_8);
            } catch (IOException e) {
                CustomLogger.logger.error("Exception occurs in FileManageService", e);
            }
        }

        return null;
    }

    public File getFile(int personId, int attachId) {
        String path = rootPath + personId + uploadFolder + "attachments" + File.separator + attachId;
        File file = Objects.requireNonNull(new File(path).listFiles())[0];
        if (file.exists()) {
            return file;
        }
        return null;
    }

    public List<MessageEntity> getPatterns(String path) throws IOException {
        List<MessageEntity> messagePatternList = new ArrayList<>();
        File directory = new File(path);

        if (directory.exists() && directory.isDirectory()) {
                File[] files = directory.listFiles();
                if (files != null) {
                    for (File file : files) {
                        MessageEntity pattern = new MessageEntity();
                        BufferedReader reader = new BufferedReader(new FileReader(file));

                        String temp;
                        if ((temp = reader.readLine()) != null) {
                            pattern.setTheme(temp);
                        }

                        StringBuilder builder = new StringBuilder();
                        while ((temp = reader.readLine()) != null) {
                            builder.append(temp);
                        }

                        pattern.setContent(builder.toString());
                        messagePatternList.add(pattern);
                    }
                }
        }

        return messagePatternList;
    }

    public void deleteFiles(int personId, long attachId) {
        File userFolder = new File(rootPath + personId + uploadFolder + "attachments" + File.separator + attachId);
        recursiveDelete(userFolder);
    }

    public void deleteUsers(long[] persons) {
        for (long i : persons) {
            File file = new File(rootPath + i);
            recursiveDelete(file);
        }
    }

    private void recursiveDelete(File file) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File f : files) {
                recursiveDelete(f);
            }
        }
        file.delete();
    }

    private String getFileName(int personId, int attachId) {
        File userFolder = new File(rootPath + personId + uploadFolder + "attachments" + File.separator);
        File[] files = userFolder.listFiles();
            for (File f : files) {
                String fName = f.getName().split("\\.")[0];
                if (fName.equals(attachId + "")) {
                    return f.getName();
                }
            }

            return null;
    }

    private boolean makeDirs(String path) {
        File filePath = new File(path);
        return !filePath.exists() && filePath.mkdirs();
    }
}
