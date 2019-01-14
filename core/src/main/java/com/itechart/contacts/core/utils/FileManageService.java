package com.itechart.contacts.core.utils;

import com.itechart.contacts.core.entities.MessagePattern;
import com.itechart.contacts.core.utils.error.CustomLogger;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class FileManageService {
    private ResourceBundle resourceBundle = ResourceBundle.getBundle("application");
    private final String rootPath = resourceBundle.getString("file.upload-dir") + File.separator;
    private final String uploadFolder = File.separator + "upload_folder" + File.separator;

    public void uploadFile(int persons_id, int attach_id, byte[] bytes, String fileExtension) {
        String path = rootPath + persons_id + uploadFolder + "attachments" + File.separator;
        String fileName = path + attach_id + "." + fileExtension;
        writeFile(path, fileName, bytes);
    }

    public void savePhoto(int persons_id, byte[] bytes, String fileExtension) {
        String path = rootPath + persons_id + uploadFolder + "photo" + File.separator;
        String fileName = path + "photo." + fileExtension;
        writeFile(path, fileName, bytes);
    }

    public File getPhoto(int personId) {
        String path = rootPath + personId + uploadFolder + "photo";
        File directory = new File(path);
        if (directory.exists() && directory.list()[0].contains("photo")) {
            return directory.listFiles()[0];
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

    public List<MessagePattern> getPatterns(String path) throws IOException {
        List<MessagePattern> messagePatternList = new ArrayList<>();
        File directory = new File(path);

        if (directory.exists() && directory.isDirectory()) {
                File[] files = directory.listFiles();
                if (files != null) {
                    for (File file : files) {
                        MessagePattern pattern = new MessagePattern();
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

    public void deleteFiles(int person_id, List<Integer> attaches_id) {
        File userFolder = new File(rootPath + person_id + uploadFolder);
        File[] files = userFolder.listFiles();

        for (int i : attaches_id) {
            for (File f : files) {
                String fName = f.getName().split("\\.")[0];
                if (fName.equals(i + "")) {
                    f.delete();
                }
            }
        }
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

    private String getFileName(int person_id, int attach_id) {
        File userFolder = new File(rootPath + person_id + uploadFolder + "attachments" + File.separator);
        File[] files = userFolder.listFiles();
            for (File f : files) {
                String fName = f.getName().split("\\.")[0];
                if (fName.equals(attach_id + "")) {
                    return f.getName();
                }
            }

            return null;
    }

    private void writeFile(String path, String fileName, byte[] bytes) {
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
    }
}