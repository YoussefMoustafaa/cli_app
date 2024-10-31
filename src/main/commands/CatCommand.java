package main.commands;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import main.fileSystem.*;
import java.util.Arrays;

import main.executor.CommandExecutor;


public class CatCommand implements Command {
    @Override
    public void execute(String[] args) {
        FileSystem fileSystem = FileSystem.getInstance();
        CommandExecutor executor = new CommandExecutor();
        String msg = "";
        if (args == null || args.length == 0) {
            System.out.println("No files provided.");
            return;
        }

        for (int i = 0; i < args.length; i++) {
            File file = new File(fileSystem.getCurrentDirectory(), args[i]);
            
            if (executor.isChainedCmd(args[i])) {
                if (i == 0) {
                    System.out.println("Error: Invalid use of chained command.");
                    return;
                }
                String[] remArgs = Arrays.copyOfRange(args, i+1, args.length);
                executor.executeChainedCmd(args[i], remArgs, msg.toString());
                return;
            }
                
            if (!file.exists()) {
                System.out.println("Error: File does not exist: " + args[i]);
                continue;
            }
            
            if (!file.canRead()) {
                System.out.println("Error: Cannot read file (check permissions): " + args[i]);
                continue;
            }
            try (BufferedReader reader = new BufferedReader(new FileReader(fileSystem.getCurrentDirectory() + "/" + args[i]))) {
                if (executor.isChainedCmd(args[i]) && i == 0) {
                    System.out.println("Error reading file: " + args[i]);
                    return;
                }
                if (executor.isChainedCmd(args[i])) {
                    String[] remArgs = Arrays.copyOfRange(args, i+1, args.length);
                    executor.executeChainedCmd(args[i], remArgs, msg);
                    return;
                }
                String line;
                while ((line = reader.readLine()) != null) {
                    msg += line + "\n";
                }
            } catch (IOException e) {
                System.out.println("Error reading file: " + args[i]);
                e.printStackTrace();
            }
            System.out.println(msg);
        }
    }
}


// package main.commands;

// import java.io.BufferedReader;
// import java.io.FileReader;
// import java.io.IOException;
// import main.fileSystem.*;

// public class CatCommand implements Command {

//     FileSystem fileSystem = FileSystem.getInstance();

//     @Override
//     public void execute(String[] args) {
//         if (args == null || args.length == 0) {
//             System.out.println("No files provided.");
//             return;
//         }
//         for (String fileName : args) {
//             try (BufferedReader reader = new BufferedReader(new FileReader(fileSystem.getCurrentDirectory() + "/" + fileName))) {
//                 String line;
//                 while ((line = reader.readLine()) != null) {
//                     System.out.println(line);
//                 }
//             } catch (IOException e) {
//                 System.out.println("Error reading file: " + fileName);
//                 e.printStackTrace();
//             }
//         }
//     }
// }