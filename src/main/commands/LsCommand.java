package main.commands;
import java.io.File;
import java.util.Arrays;

import main.executor.CommandExecutor;
import main.fileSystem.*;

public class LsCommand implements Command {
    private String msg = "";
    @Override
    public void execute(String[] args) {
        FileSystem fileSystem = FileSystem.getInstance();
        CommandExecutor executor = new CommandExecutor();
        // Null check for args
        if (args == null) {
            args = new String[0]; // Initialize args to an empty array to avoid null issues
        }
        
        boolean showAll = false;
        boolean reverseOrder = false;
        boolean isCommand = false;
        String directoryPath = fileSystem.getCurrentDirectory().toString(); // Default to current directory

        // ls -r > test.txt
        // Parse arguments to identify flags and directory path
        for (String arg : args) {
            if (arg.equalsIgnoreCase("-a")) {
                showAll = true;
            } else if (arg.equalsIgnoreCase("-r")) {
                reverseOrder = true;
            } else {
                // directoryPath = arg; // If it's not a flag, treat it as a path
                if (executor.isChainedCmd(arg)) {
                    isCommand = true;
                    break;
                }
                else {
                    directoryPath = arg;
                }
            }
        }

        File directory = new File(directoryPath);

        // Check if the directory exists
        if ((!directory.exists() || !directory.isDirectory()) && !isCommand) {
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
        } 
        else {
            for (File file : files) {
                printFileInfo(file, showAll);
            }
        }
        if (isCommand) {
            for (int i = 0; i < args.length; i++) {
                if (executor.isChainedCmd(args[i])) {
                    String[] remArgs = Arrays.copyOfRange(args, i+1, args.length);
                    executor.executeChainedCmd(args[i], remArgs, msg);
                    return;
                }
            }
        }
        else {
            System.out.println(msg);
        }
    }

    private void printFileInfo(File file, boolean showAll) {
        if (!showAll && file.isHidden()) return;

        if (file.isDirectory()) {
            msg += "directory: ";
        } else {
            msg += "file ";
        }
        msg += file.getName() + "\n";
    }
}
