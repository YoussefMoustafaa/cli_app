package main.commands;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class CatCommand implements Command {

    private String input;

    public CatCommand() {
        this.input = "";
    }

    public void setInput(String inputString) {
        this.input = inputString;
    }

    @Override
    public void execute(String[] args) {
        if (args == null || args.length == 0) {
            System.out.println("No files provided.");
            return;
        }
        for (String fileName : args) {
            try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }
            } catch (IOException e) {
                System.out.println("Error reading file: " + fileName);
                e.printStackTrace();
            }
        }
    }
}
