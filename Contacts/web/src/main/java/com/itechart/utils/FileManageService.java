package com.itechart.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.List;
import java.util.ResourceBundle;

public class FileManageService {
    private ResourceBundle resourceBundle = ResourceBundle.getBundle("application");
    private final String rootPath = resourceBundle.getString("file.upload-dir") + File.separator;
    private String uploadFolder = "upload_folder";

    public void uploadFiles(MultipartFile[] files, int[] attaches_id, int persons_id) {
        String path = rootPath + persons_id + File.separator + uploadFolder + File.separator;
        for (MultipartFile file : files) {
            if (!file.isEmpty()) {
                try {
                    String fileExtension = file.getOriginalFilename().split("\\.")[1];

                    File dir = new File(path);
                    if (!dir.exists()) {
                        dir.mkdirs();
                    }
                    //remove null below
                    File serveFile = new File(path + null + "." + fileExtension);
                    BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(serveFile));
                    byte[] bytes = file.getBytes();
                    outputStream.write(bytes);
                    outputStream.flush();
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void downloadFile(String person_id, String attach_id) {
        String path = rootPath;

    }

    public void deleteFiles(int person_id, List<Integer> attaches_id) {
        for (int i : attaches_id) {
            //name of deleting file required
            File file = new File(rootPath + person_id + uploadFolder + i);
            recursiveDelete(file);
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
}
