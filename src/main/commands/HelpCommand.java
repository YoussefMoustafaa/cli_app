package main.commands;

public class HelpCommand implements Command {
    @Override
    public void execute(String[] args) {
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
        System.out.println(help);
    }
}
