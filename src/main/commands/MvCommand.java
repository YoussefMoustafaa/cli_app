package main.commands;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import main.fileSystem.*;

import main.executor.CommandExecutor;

public class MvCommand implements Command {
    @Override
    public void execute(String[] args) {
        CommandExecutor executor = new CommandExecutor();
        FileSystem fileSystem = FileSystem.getInstance();
        if (args.length > 2) {      // multiple files
            // List<String> sourceFiles = new ArrayList<>();
            // for (int i = 0; i < args.length; i++) {
            //     sourceFiles.add(args[i]);
            //     if (executor.isChainedCmd(args[i])) {
            //         moveFiles(sourceFiles, args[i-1]);
            //         return;
            //     }
            // }

            String destinationDirectory = args[args.length - 1];    // getting last arg which is the target dir
            Path destinationDirectoryPath = Paths.get(fileSystem.getCurrentDirectory() + "/" + destinationDirectory);
            if (!isDirectory(destinationDirectoryPath)) {       // if arg is not a dir, stop execution
                System.out.println("Error: target " + destinationDirectory + " is not a directory.");
                return;
            }


            for (int i = 0; i < args.length-1; i++) {
                Path sourceFilePath = Paths.get(fileSystem.getCurrentDirectory() + "/" + args[i]);
                if (!Files.exists(sourceFilePath)) {      // check if any arg doesn't exist, stop execution
                    System.out.println("Error: " + args[i] + " does not exist.");
                    continue;
                }

                destinationDirectoryPath = Paths.get(fileSystem.getCurrentDirectory() + "/" + destinationDirectory, args[i]);
                try {
                    Files.move(sourceFilePath, destinationDirectoryPath, StandardCopyOption.REPLACE_EXISTING);
                    System.out.println("Moved \'" + sourceFilePath + "\' to " + destinationDirectory);
                } catch (IOException e) {
                    System.out.println("Error: unable to move " + args[i] + " to " + destinationDirectory);
                }

            }
            return;
        }

        if (args.length < 2) {
            System.out.println("Usage: mv <source> <destination>");
            return;
        }

        Path sourcePath = Paths.get(fileSystem.getCurrentDirectory() + "/" + args[0]);
        Path destinationPath = Paths.get(fileSystem.getCurrentDirectory() + "/" + args[1]);      // for renaming

        String feedbackMessage = "Renamed";

        if (isDirectory(destinationPath)) {
            destinationPath = Paths.get(fileSystem.getCurrentDirectory() + "/" + args[1], args[0]);  // for moving
            feedbackMessage = "Moved";
        }
        
        if (!Files.exists(sourcePath)) {
            System.out.println("Source file \"" + args[0] + "\" does not exist");
            return;
        }

        try {
            Files.move(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
            System.out.println(feedbackMessage + " \'" + sourcePath + "\' to \'" + destinationPath + "\'");
        } catch (IOException e) {
            System.err.println("Error occured: " + e.getMessage());
        }
    }

    boolean isDirectory(Path path) {
        return Files.isDirectory(path);
    }


    // void moveFiles(List<String> sourceFiles, String destinationDirectory) {
    //     FileSystem fileSystem = FileSystem.getInstance();
    //     Path destinationDirectoryPath = Paths.get(fileSystem.getCurrentDirectory() + "/" + destinationDirectory);
    //     if (!isDirectory(destinationDirectoryPath)) {       // if arg is not a dir, stop execution
    //         System.out.println("Error: target " + destinationDirectory + " is not a directory.");
    //         return;
    //     }
    //     for (int i = 0; i < sourceFiles.size()-1; i++) {
    //     }
    // }

}
