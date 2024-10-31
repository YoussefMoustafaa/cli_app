package main.commands;
import java.io.File;
import main.fileSystem.FileSystem;

import main.executor.CommandExecutor;

public class TouchCommand implements Command {
    // touch myfile.txt | echo "Hello World" > myfile.txt | cat myfile.txt
    @Override
    public void execute(String[] args) {
        CommandExecutor executor = new CommandExecutor();
        FileSystem fileSystem = FileSystem.getInstance();
        try {
            for (int i = 0; i < args.length; i++) {

                executor.executeChainedCmd(args[i], args, "");

                // if (args[i].equalsIgnoreCase("|"))  {
                //    PipeCommand pipeCommand = new PipeCommand();
                //    String[] remArgs = Arrays.copyOfRange(args, i + 1, args.length);
                //    pipeCommand.setInput("");
                //    pipeCommand.execute(remArgs);
                //    return;
                // }
                // else if (args[i].equalsIgnoreCase(">")) {
                //     WriteCommand writeCommand = new WriteCommand();
                //     String[] remArgs = Arrays.copyOfRange(args, i + 1, args.length);
                //     writeCommand.setInput("");
                //     writeCommand.execute(remArgs);
                //     return;
                // }
                // else if (args[i].equalsIgnoreCase(">>")) {
                //     AppendCommand appendCommand = new AppendCommand();
                //     String[] remArgs = Arrays.copyOfRange(args, i + 1, args.length);
                //     appendCommand.setInput("");
                //     appendCommand.execute(remArgs);
                //     return;
                // }

                // File newFile = new File(args[i]);
                File newFile = new File(fileSystem.getCurrentDirectory(), args[i]);
                if (newFile.createNewFile()) {
                    System.out.println("File created: " + newFile.getName());
                } else {
                    System.out.println("File already exists.");
                }
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
}
