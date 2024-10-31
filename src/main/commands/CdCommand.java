package main.commands;
import java.nio.file.Path;
import java.util.Arrays;

import main.executor.CommandExecutor;
import main.fileSystem.FileSystem;

public class CdCommand implements Command {
    private Path currPath;

    public CdCommand() {
    //    this.currPath = path;
    }

    @Override
    public void execute(String[] args) {
        FileSystem fileSystem = FileSystem.getInstance();
        // System.out.println("Directory Path before applying changes: " + currPath.toAbsolutePath());
        currPath = fileSystem.getCurrentDirectory().toPath();
        if (args == null || args.length == 0 || args[0].equals("~")) {
            /* Go to home directory if the given command is one of:
             1) cd
             2) cd ~
            */

            currPath = Path.of(System.getProperty("user.home"));
            fileSystem.setCurrentDirectory(currPath.toFile());
            System.out.println("Current Directory Path: " + fileSystem.getCurrentDirectory());
            return;
        }
        // if (args.length > 1) {
        //     // cd doesn't take more than one argument

        //     System.out.println("cd: too many arguments");
        //     return;
        // }
        if (args[0].equalsIgnoreCase("..")) {
            // go backward in path by one directory

            if (currPath.getParent() != null) {
                currPath = currPath.getParent();
                fileSystem.setCurrentDirectory(currPath.toFile());
            }
            System.out.println("Current Directory Path: " + fileSystem.getCurrentDirectory());
            if (args.length > 1) {
                // cd doesn't take more than one argument
                CommandExecutor executor = new CommandExecutor();
                for (int i = 1; i < args.length; i++) {
                    if (executor.isChainedCmd(args[i])) {
                        String[] remArgs = Arrays.copyOfRange(args, i+1, args.length);
                        executor.executeChainedCmd(args[i], remArgs, "");
                    }
                }
                return;
            }
            return;
        }

        // Go to given directory or Path
        args[0] = removeQuotes(args[0]);
        Path newPath = currPath.resolve(args[0]);
//        System.out.println(args[0]);
//        System.out.println(newPath.toAbsolutePath());
        if (newPath.toFile().exists()) {
            String handleDots = "";
            for (int i = 0; i < args[0].length(); i++) {
                handleDots += ".";
            }
            if (args[0].equals(handleDots)) {
                System.out.println("cd: No such directory");
            } else {
                currPath = newPath;
                fileSystem.setCurrentDirectory(currPath.toFile());
                System.out.println("Current Directory Path: " + fileSystem.getCurrentDirectory());
            }
        }
        else {
            System.out.println("cd: No such directory");
        }
        if (args.length > 1) {
            // cd doesn't take more than one argument
            CommandExecutor executor = new CommandExecutor();
            for (int i = 1; i < args.length; i++) {
                if (executor.isChainedCmd(args[i])) {
                    String[] remArgs = Arrays.copyOfRange(args, i+1, args.length);
                    executor.executeChainedCmd(args[i], remArgs, "");
                }
            }
            return;
        }
    }


    String removeQuotes(String args) {
        return args.replace("\"", "");
    }

    public Path getCurrPath() {
        return currPath;
    }
}
