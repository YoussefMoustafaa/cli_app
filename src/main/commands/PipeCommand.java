package main.commands;

import main.executor.CommandExecutor;
import main.parser.CommandParser;

// implement | command
public class PipeCommand extends ChainedCommand {
    // touch myfile.txt | echo "Hello World" > myfile.txt | cat myfile.txt
    @Override
    public void execute(String[] args) {

        String cmd = args[0];

        if (cmd.equalsIgnoreCase("|") || cmd.equalsIgnoreCase(">") || cmd.equalsIgnoreCase(">>")) {
            System.out.println("Invalid command: " + cmd);
            return;
        }

        if (cmd == "cat") {
            CatCommand catCommand = new CatCommand();
            catCommand.execute(args);
            catCommand.setInput(input);
            return;
        }

        CommandParser parser = new CommandParser();
        parser.parse(args);

        CommandExecutor executor = new CommandExecutor();
        executor.execute(parser);

    }
}
