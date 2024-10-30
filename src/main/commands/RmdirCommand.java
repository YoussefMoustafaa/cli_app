package main.commands;
import java.io.File;

public class RmdirCommand implements Command {
    @Override
    public void execute(String[] args) {
        if (args == null || args.length == 0) {
            System.out.println("Please enter the directory name : ");
            return;
        }

        String directoryName = args[0];
        File targetDirectory = new File(directoryName);

        // Check if the directory exists
        if (!targetDirectory.exists()) {
            System.out.println("Directory does not exist.");
            return;
        }

        // Check if the path is actually a directory
        if (!targetDirectory.isDirectory()) {
            System.out.println("Error: The specified path is not a directory.");
            return;
        }

        // Check if the directory is empty and attempt deletion if so
        String[] files = targetDirectory.list();
        if (files != null && files.length > 0) {
            System.out.println("Error: The directory is not empty. Use 'rm' for non-empty directories.");
        } else {
            if (targetDirectory.delete()) {
                System.out.println("Success: " + targetDirectory + " directory was deleted.");
            } else {
                System.out.println("Unable to delete the directory.");
            }
        }
    }
}