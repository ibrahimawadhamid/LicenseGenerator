package com.ibrahimawadhamid.licensegenerator.handler;

public class KeyMaker {
    public static String getKey(String pcID) {
        return Integer.toString(pcID.hashCode());
    }
}
