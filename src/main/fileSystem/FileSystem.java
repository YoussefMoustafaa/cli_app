package main.fileSystem;

import java.io.File;

public class FileSystem {
    private static FileSystem instance;
    private File currentDirectory;

    private FileSystem() {
        // Initialize current directory to the user's home directory
        currentDirectory = new File(System.getProperty("user.home"));
    }

    public static FileSystem getInstance() {
        if (instance == null) {
            instance = new FileSystem();
        }
        return instance;
    }

    public File getCurrentDirectory() {
        return currentDirectory;
    }

    public void setCurrentDirectory(File newDirectory) {
        this.currentDirectory = newDirectory;
    }
}
