package main.parser;
import java.util.Arrays;

/**
 * parser
 */
public class CommandParser {

    private String cmd;
    private String[] args;

    // build constructor
    public CommandParser() {
        this.cmd = "";
    }

    public void parse(String input) {
        String[] command = input.trim().split(" (?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
        this.cmd = command[0].toLowerCase();
        if (command.length > 1)
            this.args = Arrays.copyOfRange(command, 1, command.length);
    }

    public void parse(String[] Args) {
        this.cmd = Args[0];
        this.args = Arrays.copyOfRange(Args, 1, Args.length);
    }
    
    public void setCmd(String cmdString) {
        this.cmd = cmdString;
    }

    public void setArgs(String[] Args) {
        this.args = Args;
    }

    public String getCmd() {
        return cmd;
    }

    public String[] getArgs() {
        return args;
    }
}