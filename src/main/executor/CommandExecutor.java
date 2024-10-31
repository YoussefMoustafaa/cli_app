package main.executor;
import java.util.Map;

import main.commands.AppendCommand;
import main.commands.CatCommand;
import main.commands.CdCommand;
import main.commands.ChainedCommand;
import main.commands.Command;
import main.commands.EchoCommand;
import main.commands.HelpCommand;
import main.commands.LsCommand;
import main.commands.MkdirCommand;
import main.commands.MvCommand;
import main.commands.PipeCommand;
import main.commands.PwdCommand;
import main.commands.RmCommand;
import main.commands.RmdirCommand;
import main.commands.TouchCommand;
import main.commands.WriteCommand;
import main.parser.CommandParser;

import java.util.Arrays;
import java.util.HashMap;


public class CommandExecutor {

    private final Map<String, Command> commands;
    private final Map<String, Command> chainedCommands;

    public CommandExecutor() {
        this.commands = new HashMap<>();
        commands.put("echo", new EchoCommand());
        commands.put("pwd", new PwdCommand());
        commands.put("ls", new LsCommand());
        commands.put("cd", new CdCommand());
        commands.put("cat", new CatCommand());
        commands.put("mkdir", new MkdirCommand());
        commands.put("mv", new MvCommand());
        commands.put("rm", new RmCommand());
        commands.put("rmdir", new RmdirCommand());
        commands.put("touch", new TouchCommand());
        commands.put("help", new HelpCommand());
        commands.put("|", new PipeCommand());
        commands.put(">", new WriteCommand());
        commands.put(">>", new AppendCommand());
        this.chainedCommands = new HashMap<>();
        chainedCommands.put("|", new PipeCommand());
        chainedCommands.put(">", new WriteCommand());
        chainedCommands.put(">>", new AppendCommand());
    }

    // touch myfile.txt | echo "Hello World" > myfile.txt | cat myfile.txt
    public void execute(CommandParser commandParser) {
        
        String cmd = commandParser.getCmd();

        Command command = commands.get(cmd);

        if (command != null) {
            if (cmd == "|" || cmd == ">" || cmd == ">>") {
                
            }
            command.execute(commandParser.getArgs());
        } else {
            System.out.println("Unknown command: " + cmd);
        }

    }

    public void executeChainedCmd(String cmd, String[] Args, String inputString) {
        Command command = chainedCommands.get(cmd);
        if (command != null && command instanceof ChainedCommand) {
                ChainedCommand chainedCommand = (ChainedCommand) command;
    
                String[] remArgs = Arrays.copyOfRange(Args, 2, Args.length);
                chainedCommand.setInput(inputString);
                chainedCommand.execute(remArgs);
        }
    }

    public boolean isChainedCmd(String cmd) {
        return chainedCommands.containsKey(cmd);
    }
}
