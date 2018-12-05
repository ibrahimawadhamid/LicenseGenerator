package com.ibrahimawadhamid.licensegenerator.license;

import com.ibrahimawadhamid.licensegenerator.handler.IDGenerator;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

class License implements Serializable {
    private int numberOfRuns;
    private String motherboardID;
    private String harddiskID;
    private String pcID;

    public License(int numberOfRuns) {
        try {
            this.numberOfRuns = numberOfRuns;
            this.motherboardID = IDGenerator.getMotherboardSN();
            this.harddiskID = IDGenerator.getSerialNumber("C");
            this.pcID = this.motherboardID + this.harddiskID;
        } catch (Exception var3) {
            Logger.getLogger(License.class.getName()).log(Level.SEVERE, null, var3);
        }

    }

    public int getNumberOfRuns() {
        return this.numberOfRuns;
    }

    public String getMotherboardID() {
        return this.motherboardID;
    }

    public String getHardDiskID() {
        return this.harddiskID;
    }

    public String getPcID() {
        return this.pcID;
    }

    public void setNumberOfRuns(int numberOfRuns) {
        this.numberOfRuns = numberOfRuns;
    }
}
