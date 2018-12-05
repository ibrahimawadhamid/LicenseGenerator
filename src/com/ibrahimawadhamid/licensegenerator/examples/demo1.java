package com.ibrahimawadhamid.licensegenerator.examples;

import com.ibrahimawadhamid.licensegenerator.handler.KeyMaker;
import com.ibrahimawadhamid.licensegenerator.license.LicenseManager;

public class demo1 {

    public static void main(String[] args) {
        LicenseManager manager = new LicenseManager();
        String licenseFile = "license.dat";
        manager.makeLicense(licenseFile, 10);
        boolean validLicense = manager.isLicenseValid(licenseFile);
        if (validLicense) {
            System.out.println("Valid License File");
        } else {
            System.out.println("Not A Valid License File");
        }

        manager.setNumberOfRuns(licenseFile, 5);
        String pcID = manager.getPCID(licenseFile);
        String pcKey = KeyMaker.getKey(pcID);
        manager.makeLicenseFree(licenseFile, pcKey);
    }
}
