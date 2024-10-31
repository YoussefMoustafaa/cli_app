package main.commands;
import main.fileSystem.*;
public class PwdCommand implements Command {
    @Override
    public void execute(String[] args) {
        FileSystem fileSystem = FileSystem.getInstance();
        String currentDirectory = fileSystem.getCurrentDirectory().toString();
        System.out.println(currentDirectory);
    }
}
