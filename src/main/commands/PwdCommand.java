package main.commands;
import java.util.Arrays;

import main.executor.CommandExecutor;
import main.fileSystem.*;
public class PwdCommand implements Command {
    @Override
    public void execute(String[] args) {
        CommandExecutor executor = new CommandExecutor();
        FileSystem fileSystem = FileSystem.getInstance();
        String currentDirectory = fileSystem.getCurrentDirectory().toString();
        if (args == null || args.length == 0) {
            System.out.println(currentDirectory);
        } else {
            if (executor.isChainedCmd(args[0])) {
                String[] remArgs = Arrays.copyOfRange(args, 1, args.length);
                executor.executeChainedCmd(args[0], remArgs, currentDirectory.toString());
            }
            else {
                System.out.println("Error: " + args[0] + " is not a command.");
            }
        }
    }
}
