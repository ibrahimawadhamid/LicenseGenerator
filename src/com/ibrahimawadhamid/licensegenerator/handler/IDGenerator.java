package com.ibrahimawadhamid.licensegenerator.handler;

import java.io.*;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IDGenerator {

    public static String getMotherboardSN() {
        StringBuilder result = new StringBuilder();

        try {
            File file = File.createTempFile("temp_license", ".vbs");
            file.deleteOnExit();
            FileWriter fw = new FileWriter(file);
            String vbs = "Set objWMIService = GetObject(\"winmgmts:\\\\.\\root\\cimv2\")\nSet colItems = objWMIService.ExecQuery _ \n   (\"Select * from Win32_BaseBoard\") \nFor Each objItem in colItems \n    Wscript.Echo objItem.SerialNumber \n    exit for  ' do the first cpu only! \nNext \n";
            fw.write(vbs);
            fw.close();
            Process p = Runtime.getRuntime().exec("cscript //NoLogo " + file.getPath());

            try (BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()))) {

                String sCurrentLine;

                while ((sCurrentLine = br.readLine()) != null) {
                    result.append(sCurrentLine);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (Exception var7) {
            Logger.getLogger(CryptographyHandler.class.getName()).log(Level.SEVERE, null, var7);
        }

        return result.toString().trim();
    }

    public static String getSerialNumber(String drive) {
        StringBuilder result = new StringBuilder();

        try {
            File file = File.createTempFile("realhowto", ".vbs");
            file.deleteOnExit();
            FileWriter fw = new FileWriter(file);
            String vbs = "Set objFSO = CreateObject(\"Scripting.FileSystemObject\")\nSet colDrives = objFSO.Drives\nSet objDrive = colDrives.item(\"" + drive + "\")\n" + "Wscript.Echo objDrive.SerialNumber";
            fw.write(vbs);
            fw.close();
            Process p = Runtime.getRuntime().exec("cscript //NoLogo " + file.getPath());


            try (BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()))) {

                String sCurrentLine;

                while ((sCurrentLine = br.readLine()) != null) {
                    result.append(sCurrentLine);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (Exception var8) {
            Logger.getLogger(CryptographyHandler.class.getName()).log(Level.SEVERE, null, var8);

        }

        return result.toString().trim();
    }

    public static String get_mac_address_windows() throws SocketException {
        String mac_address = "";

        StringBuffer lStringBuffer;
        for (Enumeration interfaces = NetworkInterface.getNetworkInterfaces(); interfaces.hasMoreElements(); mac_address = mac_address + lStringBuffer) {
            NetworkInterface nif = (NetworkInterface) interfaces.nextElement();
            byte[] lBytes = nif.getHardwareAddress();
            lStringBuffer = new StringBuffer();
            if (lBytes != null) {

                for (byte b : lBytes) {
                    lStringBuffer.append(String.format("%1$02X ", b));
                }
            }
        }

        return mac_address;
    }

    public static String get_mac_address_unix() throws IOException {
        StringBuilder mac_address = new StringBuilder();
        String command = "ipconfig /all";
        Process p = Runtime.getRuntime().exec(command);
        BufferedReader inn = new BufferedReader(new InputStreamReader(p.getInputStream()));
        Pattern pattern = Pattern.compile(".*Physical Addres.*: (.*)");

        while (true) {
            String line = inn.readLine();
            if (line == null) {
                return mac_address.toString();
            }

            Matcher mm = pattern.matcher(line);
            if (mm.matches()) {
                mac_address.append(mm.group(1));
            }
        }
    }
}
