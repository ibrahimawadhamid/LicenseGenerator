package com.ibrahimawadhamid.licensegenerator.handler;

import java.util.Random;

class PRG {

    public static byte[] Generate(byte seed, int key_length) {
        Random random_generator = new Random((long)seed);
        byte[] key = new byte[key_length];

        for(int i = 0; i < key_length; ++i) {
            key[i] = (byte)(random_generator.nextInt() % 255);
        }

        return key;
    }
}
