package main.commands;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import main.executor.CommandExecutor;


public class CatCommand implements Command {
    @Override
    public void execute(String[] args) {
        CommandExecutor executor = new CommandExecutor();
        if (args == null || args.length == 0) {
            System.out.println("No files provided.");
            return;
        }
        for (int i = 0; i < args.length; i++) {
            try (BufferedReader reader = new BufferedReader(new FileReader(args[i]))) {
                executor.executeChainedCmd(args[i], args, "");
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }
            } catch (IOException e) {
                System.out.println("Error reading file: " + args[i]);
                e.printStackTrace();
            }
        }
    }
}
