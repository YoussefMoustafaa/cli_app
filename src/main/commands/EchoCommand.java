package main.commands;

import main.executor.CommandExecutor;

public class EchoCommand implements Command {
    @Override
    public void execute(String[] args) {
        String msg = "";
        CommandExecutor executor = new CommandExecutor();
        if (args == null || args.length == 0) 
        {
            System.out.println("No arguments provided.");
            return;
        }
        else 
        {
            for (String arg : args) 
            {
                if (executor.executeChainedCmd(arg, args, msg)) {
                    return;
                }
                msg += arg;
            }
            System.out.println(msg);
        }
    }
}