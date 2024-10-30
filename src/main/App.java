package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import main.executor.CommandExecutor;
import main.parser.CommandParser;


public class App {
    // build main method
    public static void main(String[] args) throws InterruptedException {
        // create a new buffer reader
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Enter a command: ");
        // input cmd string
        // String cmd;
        // String[] Args;
        String userInput;
        while (true) {
            System.out.print("> ");
            try {
                userInput = reader.readLine();
                if (userInput.equalsIgnoreCase("exit")) {   // if userInput == exit
                    System.out.println("Exitting CLI");
                    // make timeout of 1 second
                    Thread.sleep(500);
                    break;
                }
                CommandParser parser = new CommandParser();
                parser.parse(userInput);

                CommandExecutor executer = new CommandExecutor();
                executer.execute(parser);
            } 
            catch (IOException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

}
