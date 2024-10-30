package main.commands;

// implement >> commmand
public class AppendCommand implements Command {

    private String input;

    public AppendCommand() {
        input = "";
    }

    @Override
    public void execute(String[] args) {
        
    }

    public void setInput(String inputString) {
        input = inputString;
    }
}
