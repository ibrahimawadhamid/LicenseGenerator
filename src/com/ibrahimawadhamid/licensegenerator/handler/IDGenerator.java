package com.ibrahimawadhamid.licensegenerator.handler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IDGenerator {
    public IDGenerator() {
    }

    public static String getMotherboardSN() {
        String result = "";

        try {
            File file = File.createTempFile("temp_license", ".vbs");
            file.deleteOnExit();
            FileWriter fw = new FileWriter(file);
            String vbs = "Set objWMIService = GetObject(\"winmgmts:\\\\.\\root\\cimv2\")\nSet colItems = objWMIService.ExecQuery _ \n   (\"Select * from Win32_BaseBoard\") \nFor Each objItem in colItems \n    Wscript.Echo objItem.SerialNumber \n    exit for  ' do the first cpu only! \nNext \n";
            fw.write(vbs);
            fw.close();
            Process p = Runtime.getRuntime().exec("cscript //NoLogo " + file.getPath());

            BufferedReader input;
            String line;
            for(input = new BufferedReader(new InputStreamReader(p.getInputStream())); (line = input.readLine()) != null; result = result + line) {
                ;
            }

            input.close();
        } catch (Exception var7) {
            ;
        }

        return result.trim();
    }

    public static String getSerialNumber(String drive) {
        String result = "";

        try {
            File file = File.createTempFile("realhowto", ".vbs");
            file.deleteOnExit();
            FileWriter fw = new FileWriter(file);
            String vbs = "Set objFSO = CreateObject(\"Scripting.FileSystemObject\")\nSet colDrives = objFSO.Drives\nSet objDrive = colDrives.item(\"" + drive + "\")\n" + "Wscript.Echo objDrive.SerialNumber";
            fw.write(vbs);
            fw.close();
            Process p = Runtime.getRuntime().exec("cscript //NoLogo " + file.getPath());

            BufferedReader input;
            String line;
            for(input = new BufferedReader(new InputStreamReader(p.getInputStream())); (line = input.readLine()) != null; result = result + line) {
                ;
            }

            input.close();
        } catch (Exception var8) {
            ;
        }

        return result.trim();
    }

    public static String get_mac_address_windows() throws SocketException {
        String mac_address = "";

        StringBuffer lStringBuffer;
        for(Enumeration interfaces = NetworkInterface.getNetworkInterfaces(); interfaces.hasMoreElements(); mac_address = mac_address + lStringBuffer) {
            NetworkInterface nif = (NetworkInterface)interfaces.nextElement();
            byte[] lBytes = nif.getHardwareAddress();
            lStringBuffer = new StringBuffer();
            if (lBytes != null) {
                byte[] arr$ = lBytes;
                int len$ = lBytes.length;

                for(int i$ = 0; i$ < len$; ++i$) {
                    byte b = arr$[i$];
                    lStringBuffer.append(String.format("%1$02X ", new Byte(b)));
                }
            }
        }

        return mac_address;
    }

    public static String get_mac_address_unix() throws SocketException, IOException {
        String mac_address = "";
        String command = "ipconfig /all";
        Process p = Runtime.getRuntime().exec(command);
        BufferedReader inn = new BufferedReader(new InputStreamReader(p.getInputStream()));
        Pattern pattern = Pattern.compile(".*Physical Addres.*: (.*)");

        while(true) {
            String line = inn.readLine();
            if (line == null) {
                return mac_address;
            }

            Matcher mm = pattern.matcher(line);
            if (mm.matches()) {
                mac_address = mac_address + mm.group(1);
            }
        }
    }
}
