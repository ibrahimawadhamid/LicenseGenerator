package com.ibrahimawadhamid.licensegenerator.handler;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CryptographyHandler {
    public CryptographyHandler() {
    }

    public static void encrypt(String path_filename_extension) throws IOException {
        try {
            Random seed_generator = new Random();
            File file = new File(path_filename_extension);
            FileInputStream fin = new FileInputStream(file);
            byte[] fileContent = new byte[(int)file.length()];
            fin.read(fileContent);
            int seed = seed_generator.nextInt();
            seed %= 255;
            byte[] enc_msg = new byte[fileContent.length];
            byte[] key = PRG.Generate((byte)seed, fileContent.length);

            for(int i = 0; i < enc_msg.length; ++i) {
                enc_msg[i] = (byte)(fileContent[i] ^ key[i]);
            }

            FileOutputStream fos = new FileOutputStream(path_filename_extension);
            fos.write(enc_msg);
            fos.write((byte)seed);
            fin.close();
            fos.close();
        } catch (FileNotFoundException var9) {
            Logger.getLogger(CryptographyHandler.class.getName()).log(Level.SEVERE, (String)null, var9);
        }

    }

    public static void decrypt(String path_filename_extension) throws IOException {
        try {
            File file = new File(path_filename_extension);
            FileInputStream fin = new FileInputStream(file);
            byte[] temp_fileContent = new byte[(int)file.length()];
            fin.read(temp_fileContent);
            int seed = temp_fileContent[temp_fileContent.length - 1];
            byte[] fileContent = new byte[(int)file.length() - 1];
            System.arraycopy(temp_fileContent, 0, fileContent, 0, fileContent.length);
            byte[] enc_msg = new byte[fileContent.length];
            byte[] key = PRG.Generate((byte)seed, fileContent.length);

            for(int i = 0; i < enc_msg.length; ++i) {
                enc_msg[i] = (byte)(fileContent[i] ^ key[i]);
            }

            FileOutputStream fos = new FileOutputStream(path_filename_extension);
            fos.write(enc_msg);
            fin.close();
            fos.close();
        } catch (FileNotFoundException var9) {
            Logger.getLogger(CryptographyHandler.class.getName()).log(Level.SEVERE, (String)null, var9);
        }

    }
}
