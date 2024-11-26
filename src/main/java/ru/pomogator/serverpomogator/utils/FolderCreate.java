package ru.pomogator.serverpomogator.utils;

import java.io.File;

public class FolderCreate {
    private final static String[] listFolder = {"files", "files/news", "files/user", "files/document", "files/webinar",  "files/reviews"};

    static public void createFolder(String folderPath) {
        File directory = new File(folderPath);
        if (!directory.exists()) {
            directory.mkdir();
        }
    }

    static public void intialFolder() {
        for (var folder : listFolder) {
            createFolder(folder);
        }
    }
}