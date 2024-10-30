package main.commands;
public class PwdCommand implements Command {
    @Override
    public void execute(String[] args) {
        String currentDirectory = System.getProperty("user.dir");
        System.out.println(currentDirectory);
    }
}
