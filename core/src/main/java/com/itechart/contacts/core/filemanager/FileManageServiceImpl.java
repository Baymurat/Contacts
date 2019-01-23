package com.itechart.contacts.core.filemanager;

import com.itechart.contacts.core.email.entity.MessageEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.ResourceBundle;

@Service
public class FileManageServiceImpl implements FileManageService{
    private ResourceBundle resourceBundle = ResourceBundle.getBundle("application");
    private final String rootPath = resourceBundle.getString("file.upload-dir") + File.separator;
    private final String uploadFolder = File.separator + "upload_folder" + File.separator;

    public void uploadFile(long personsId, long attachId, MultipartFile file) throws IOException {
        String path = rootPath + personsId + uploadFolder + "attachments" + File.separator + attachId + File.separator;
        file.transferTo(new File(path + file.getOriginalFilename()));
    }

    public void savePhoto(long personsId, MultipartFile photo) throws IOException {
        String path = rootPath + personsId + uploadFolder + "photo" + File.separator;
        photo.transferTo(new File(path + photo.getOriginalFilename()));
    }

    public String getPhoto(int personId) {
        String encodedfile = null;
        String path = rootPath + personId + uploadFolder + "photo";
        File directory = new File(path);
        if (directory.exists() && directory.list()[0].contains("photo")) {
            try {
                File file = directory.listFiles()[0];
                FileInputStream fileInputStreamReader = new FileInputStream(file);
                byte[] bytes = new byte[(int)file.length()];
                fileInputStreamReader.read(bytes);
                return new String(Base64.getEncoder().encode(bytes), StandardCharsets.UTF_8);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    public File getFile(int personId, int attachId) {
        String path = rootPath + personId + uploadFolder + "attachments" + File.separator + getFileName(personId, attachId);
        File file = new File(path);
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

    public void deleteUsers(int[] persons) {
        for (int i : persons) {
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

    /*private void writeFile(String path, String fileName, byte[] bytes) {
        try {
            File dir = new File(path);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            File serveFile = new File(fileName);
            BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(serveFile));
            outputStream.write(bytes);
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            CustomLogger.logger.error("Error during write into the file.", e);
        }
    }*/
}
