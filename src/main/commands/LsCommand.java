package main.commands;
import java.io.File;

public class LsCommand implements Command {
    @Override
    public void execute(String[] args) {
        // Null check for args
        if (args == null) {
            args = new String[0]; // Initialize args to an empty array to avoid null issues
        }
        
        boolean showAll = false;
        boolean reverseOrder = false;
        String directoryPath = System.getProperty("user.dir"); // Default to current directory

        // Parse arguments to identify flags and directory path
        for (String arg : args) {
            if (arg.equals("-a")) {
                showAll = true;
            } else if (arg.equals("-r")) {
                reverseOrder = true;
            } else {
                directoryPath = arg; // If it's not a flag, treat it as a path
            }
        }

        File directory = new File(directoryPath);

        // Check if the directory exists
        if (!directory.exists() || !directory.isDirectory()) {
            System.out.println("Directory does not exist: " + directoryPath);
            return;
        }

        // Retrieve files in the directory
        File[] files = directory.listFiles();
        if (files == null || files.length == 0) {
            System.out.println("The directory is empty.");
            return;
        }

        // Display files, possibly with -a and/or -r options
        if (reverseOrder) {
            for (int i = files.length - 1; i >= 0; i--) {
                printFileInfo(files[i], showAll);
            }
        } else {
            for (File file : files) {
                printFileInfo(file, showAll);
            }
        }
    }

    private void printFileInfo(File file, boolean showAll) {
        if (!showAll && file.isHidden()) return;

        if (file.isDirectory()) {
            System.out.print("directory: ");
        } else {
            System.out.print("file: ");
        }
        System.out.println(file.getName());
    }
}
