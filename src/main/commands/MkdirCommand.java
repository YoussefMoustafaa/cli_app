package main.commands;

import java.io.File;
import java.util.Arrays;

import main.executor.CommandExecutor;
import main.fileSystem.*;

public class MkdirCommand implements Command {
    @Override
    public void execute(String[] args) {
        FileSystem fileSystem = FileSystem.getInstance();
        CommandExecutor executor = new CommandExecutor();
        if (args == null || args.length == 0) {
            System.out.println("No directory name provided.");
            return;
        }
        for (int i = 0; i < args.length; i++) {
            if (executor.isChainedCmd(args[i])) {
                String[] remArgs = Arrays.copyOfRange(args, i+1, args.length);
                executor.executeChainedCmd(args[i], remArgs, "");
                return;
            }
            File dir = new File(fileSystem.getCurrentDirectory(),args[i]);
            if (!dir.exists()) 
            {
                if (dir.mkdirs()) 
                {
                    System.out.println("Directory created: " + args[i]);
                } else 
                {
                    System.out.println("Failed to create directory: " + args[i]);
                }
            } 
            else 
            {
                System.out.println("Directory already exists: " + args[i]);
            }
        }

    }
}
