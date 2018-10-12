package com.itechart.contacts.core.utils;

import java.io.*;
import java.util.List;
import java.util.ResourceBundle;

public class FileManageService {
    private ResourceBundle resourceBundle = ResourceBundle.getBundle("application");
    private final String rootPath = resourceBundle.getString("file.upload-dir") + File.separator;
    private final String uploadFolder = File.separator + "upload_folder" + File.separator;

    public void uploadFile(int persons_id, int attach_id, byte[] bytes, String fileExtension) {
        String path = rootPath + persons_id + uploadFolder;
            try {
                File dir = new File(path);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                File serveFile = new File(path + attach_id + "." + fileExtension);
                BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(serveFile));
                outputStream.write(bytes);
                outputStream.flush();
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    public File getFile(int personId, int attachId) {
        String path = rootPath + personId + uploadFolder + getFileName(personId, attachId);
        File file = new File(path);
        if (file.exists()) {
            return file;
        }
        return null;
    }
    /*
    VAR 1
    public InputStream downloadFile(int person_id, int attach_id) {
        String path = rootPath + person_id + uploadFolder + getFileName(person_id, attach_id);
        File file = new File(path);
        try {
            return new BufferedInputStream(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }*/

    /*public byte[] downloadFile(int person_id, int attach_id) {
        String path = rootPath + person_id + uploadFolder + getFileName(person_id, attach_id);
        File file = new File(path);
        file.renameTo()
        try {
            byte[] bytes = new byte[(int)file.length()];
            BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(file));
            bufferedInputStream.read(bytes);
            return bytes;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }*/

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
        File userFolder = new File(rootPath + person_id + uploadFolder);
        File[] files = userFolder.listFiles();
            for (File f : files) {
                String fName = f.getName().split("\\.")[0];
                if (fName.equals(attach_id + "")) {
                    return f.getName();
                }
            }

            return null;
    }
}
