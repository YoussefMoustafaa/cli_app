package main.commands;

public abstract class ChainedCommand implements Command {
    protected String input;

    public void setInput(String inputString) {
        this.input = inputString;
    }
}
