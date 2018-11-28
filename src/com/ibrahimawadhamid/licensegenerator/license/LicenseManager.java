package com.ibrahimawadhamid.licensegenerator.license;


import com.ibrahimawadhamid.licensegenerator.handler.CryptographyHandler;
import com.ibrahimawadhamid.licensegenerator.handler.FileHandler;
import com.ibrahimawadhamid.licensegenerator.handler.IDGenerator;
import com.ibrahimawadhamid.licensegenerator.handler.KeyMaker;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LicenseManager {
    public LicenseManager() {
    }

    public boolean makeLicense(String pathFilenameExtension, int numberOfRuns) {
        License license = new License(numberOfRuns);

        try {
            this.saveLicenseFile(license, pathFilenameExtension);
            this.closeLicenseFile(pathFilenameExtension);
            return true;
        } catch (Exception var5) {
            return false;
        }
    }

    public boolean isLicenseValid(String pathFilenameExtension) {
        try {
            if (!FileHandler.isLicenseExist(pathFilenameExtension)) {
                return false;
            } else {
                License license = this.openLicenseFile(pathFilenameExtension);
                String motherboard_id = IDGenerator.getMotherboardSN();
                String harddisk_id = IDGenerator.getSerialNumber("C");
                if (motherboard_id.matches(license.getMotherboardID()) && harddisk_id.matches(license.getHardDiskID())) {
                    this.closeLicenseFile(pathFilenameExtension);
                    return true;
                } else {
                    this.closeLicenseFile(pathFilenameExtension);
                    return false;
                }
            }
        } catch (Exception var5) {
            Logger.getLogger(License.class.getName()).log(Level.SEVERE, (String)null, var5);
            return false;
        }
    }

    public boolean makeLicenseFree(String pathFilenameExtension, String pcKey) {
        License license = this.openLicenseFile(pathFilenameExtension);
        if (KeyMaker.getKey(license.getPcID()).matches(pcKey)) {
            license.setNumberOfRuns(-1);
            this.saveLicenseFile(license, pathFilenameExtension);
            this.closeLicenseFile(pathFilenameExtension);
            return true;
        } else {
            this.closeLicenseFile(pathFilenameExtension);
            return false;
        }
    }

    public void setNumberOfRuns(String pathFilenameExtension, int newNumberOfRuns) {
        License license = this.openLicenseFile(pathFilenameExtension);
        license.setNumberOfRuns(newNumberOfRuns);
        this.saveLicenseFile(license, pathFilenameExtension);
        this.closeLicenseFile(pathFilenameExtension);
    }

    public int getNumberOfRuns(String pathFilenameExtension) {
        License license = this.openLicenseFile(pathFilenameExtension);
        int numberOfRuns = license.getNumberOfRuns();
        this.closeLicenseFile(pathFilenameExtension);
        return numberOfRuns;
    }

    public String getPCID(String pathFilenameExtension) {
        License license = this.openLicenseFile(pathFilenameExtension);
        String pcID = license.getPcID();
        this.closeLicenseFile(pathFilenameExtension);
        return pcID;
    }

    private License openLicenseFile(String pathFilenameExtension) {
        try {
            CryptographyHandler.decrypt(pathFilenameExtension);
            File file = new File(pathFilenameExtension);
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
            License license = (License)in.readObject();
            in.close();
            return license;
        } catch (Exception var5) {
            Logger.getLogger(LicenseManager.class.getName()).log(Level.SEVERE, (String)null, var5);
            return null;
        }
    }

    private void closeLicenseFile(String pathFilenameExtension) {
        try {
            CryptographyHandler.encrypt(pathFilenameExtension);
        } catch (IOException var3) {
            Logger.getLogger(LicenseManager.class.getName()).log(Level.SEVERE, (String)null, var3);
        }

    }

    private void saveLicenseFile(License license, String pathFilenameExtension) {
        try {
            ObjectOutput out = new ObjectOutputStream(new FileOutputStream(pathFilenameExtension));
            out.writeObject(license);
            out.close();
        } catch (IOException var4) {
            Logger.getLogger(LicenseManager.class.getName()).log(Level.SEVERE, (String)null, var4);
        }

    }
}
