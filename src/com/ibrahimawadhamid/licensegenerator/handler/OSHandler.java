package com.ibrahimawadhamid.licensegenerator.handler;

class OSHandler {
    public static boolean isWindows() {
        String os = System.getProperty("os.name").toLowerCase();
        return os.contains("win");
    }

    public static boolean isMac() {
        String os = System.getProperty("os.name").toLowerCase();
        return os.contains("mac");
    }

    public static boolean isUnix() {
        String os = System.getProperty("os.name").toLowerCase();
        return os.contains("nix") || os.contains("nux");
    }

    public static boolean isSolaris() {
        String os = System.getProperty("os.name").toLowerCase();
        return os.contains("sunos");
    }
}
