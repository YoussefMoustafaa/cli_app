package main.commands;
public class EchoCommand implements Command {
    @Override
    public void execute(String[] args) {
        if (args == null || args.length == 0) 
        {
            System.out.println("No arguments provided.");
            return;
        }
        else 
        {
            for (String arg : args) 
            {
                System.out.println(arg);
            }
        }
    }
}