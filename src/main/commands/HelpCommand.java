package main.commands;

import java.util.Arrays;

import main.executor.CommandExecutor;

public class HelpCommand implements Command {
    @Override
    public void execute(String[] args) {
        CommandExecutor executor = new CommandExecutor();
        String help = "  Available commands:\n" +
                "    1. System commands: \n" +
                "       - pwd              : Print current directory\n" +
                "       - cd <directory>   : Change directory\n" +
                "       - echo             : Print its arguments\n" +
                "       - ls               : List directory contents\n" +
                "       - ls -a            : List directory contents including hidden files\n" +
                "       - ls -r            : List directory contents in reverse order\n" +
                "       - mkdir <dirName>  : Create a new directory\n" +
                "       - rmdir <dirName>  : Remove a directory\n" +
                "       - touch <fileName> : Create a new empty file\n" +
                "       - mv <src> <dest>  : Move or rename a file\n" +
                "       - rm <fileName>    : Remove a file\n" +
                "       - cat <fileName>   : Display file contents\n" +
                "       - > <fileName>     : Redirect output to a file\n" +
                "       - >> <fileName>    : Append output to a file\n" +
                "       - |                : Combine between commands\n" +
                "\n    2. Internal commands:\n" +
                "       - exit: Exit the CLI.\n" +
                "       - help: Display this help message.\n";

        if (args == null || args.length == 0) {
            System.out.println(help);
            return;
        }
        for (int i = 0; i < args.length;) {
            if (executor.isChainedCmd(args[i])) {
                String[] remArgs = Arrays.copyOfRange(args, i+1, args.length);
                executor.executeChainedCmd(args[i], remArgs, help);
                return;
            }
            else {
                System.err.println("Error: " + args[i] + " is not a command");
                return;
            }
        }
    }
}
