package com.ibrahimawadhamid.licensegenerator.handler;

import java.io.File;

public class FileHandler {

    public static boolean isLicenseExist(String pathFilenameExtension) {
        File file = new File(pathFilenameExtension);
        return file.exists();
    }
}
