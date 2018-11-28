package com.ibrahimawadhamid.licensegenerator.handler;

import java.io.File;

public class FileHandler {
    public FileHandler() {
    }

    public static boolean isLicenseExist(String pathFilenameExtension) {
        File file = new File(pathFilenameExtension);
        boolean exists = file.exists();
        return exists;
    }
}
