package main.commands;

import main.executor.CommandExecutor;
import main.parser.CommandParser;

// implement | command
public class PipeCommand extends ChainedCommand {
    // touch myfile.txt | echo "Hello World" > myfile.txt | cat myfile.txt
    @Override
    public void execute(String[] args) {

        CommandExecutor executor = new CommandExecutor();
        String cmd = args[0];

        if (cmd.equalsIgnoreCase("|") || cmd.equalsIgnoreCase(">") || cmd.equalsIgnoreCase(">>")) {
            System.out.println("Invalid command: " + cmd);
            return;
        }

        if (cmd == "cat") {
            executor.executeChainedCmd(cmd, args, this.input);
            return;
        }

        CommandParser parser = new CommandParser();
        parser.parse(args);

        executor.execute(parser);

    }
}
