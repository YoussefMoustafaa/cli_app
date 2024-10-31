package main.commands;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

import main.fileSystem.*;

import main.executor.CommandExecutor;

public class WriteCommand extends ChainedCommand {


    public WriteCommand() {
        this.input = "";
    }
    
    @Override
    public void execute(String[] args) {
        CommandExecutor executor = new CommandExecutor();
        FileSystem fileSystem = FileSystem.getInstance();
        // Check if a filename and content are provided as arguments
        if (args == null || args.length == 0) {
            System.out.println("Error: '>' command requires a filename and text to write.");
            return;
        }

        // The first argument is the filename, and the rest is the content
        String filename = args[0];
        StringBuilder content = new StringBuilder();

        // echo "hello" > test.txt

        if (this.input == null) {
            this.input = "";
        }
        // Include input if it's set
        if (!this.input.isEmpty()) {
            content.append(input).append("\n");
        }

        // // Append the rest of the args as the content
        // for (int i = 1; i < args.length; i++) {
        //     content.append(args[i]).append(" ");
        // }

        // Write content to file in append mode
        try (FileWriter writer = new FileWriter(fileSystem.getCurrentDirectory() + "/" + filename, true)) { // `true` enables append mode
            writer.write(content.toString().trim() + "\n");  // Trim extra space and add newline
            System.out.println("Success: Content written to " + filename);
            if (args.length > 1) {
                if (!executor.isChainedCmd(args[1])) {
                    System.out.println("Error: " + args[1] + " is not a command");
                } else {
                    String[] remArgs = Arrays.copyOfRange(args, 2, args.length);
                    executor.executeChainedCmd(args[1], remArgs, "");
                }
            }
        } catch (IOException e) {
            System.out.println("Error: Unable to write to file - " + e.getMessage());
        }
    }
}
