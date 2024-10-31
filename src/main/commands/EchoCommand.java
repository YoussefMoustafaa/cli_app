package main.commands;

import java.util.Arrays;

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
            for (int i = 0; i < args.length; i++) 
            {
                if (executor.isChainedCmd(args[i])) {
                    String[] remArgs = Arrays.copyOfRange(args, i+1, args.length);
                    executor.executeChainedCmd(args[i], remArgs, msg);
                    break;
                }
                msg += args[i];
            }
            System.out.println(msg);
        }
    }
}