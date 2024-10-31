package test;

import main.commands.PipeCommand;
import main.executor.CommandExecutor;
import main.parser.CommandParser;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;

public class PipeCommandTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private PipeCommand pipeCommand;
    private CommandExecutor commandExecutor;

    @Before
    public void setUp() {
        System.setOut(new PrintStream(outContent));
        pipeCommand = new PipeCommand();
        commandExecutor = new CommandExecutor();
    }

    @Test
    public void testValidPipeCommand() {
        // This test simulates the command: echo "Hello World" | cat
        String[] commands = {"echo", "Hello World", "|", "cat"};

        // Execute the piped command
        pipeCommand.execute(commands);
        
        // Check the output
        String output = outContent.toString();
        System.out.println("Output of valid pipe command: " + output);
        // Manually verify if "Hello World" is present
        if (output.contains("Hello World")) {
            System.out.println("Valid pipe command executed successfully.");
        } else {
            System.out.println("Failed to execute valid pipe command.");
        }
    }

    @Test
    public void testInvalidPipeCommand() {
        // This test simulates an invalid command: |
        String[] commands = {"|", "echo", "Hello World"};

        // Execute the piped command
        pipeCommand.execute(commands);
        
        // Check the output
        String output = outContent.toString();
        System.out.println("Output of invalid pipe command: " + output);
        // Manually verify if the invalid command message is present
        if (output.contains("Invalid command: |")) {
            System.out.println("Invalid command handled correctly.");
        } else {
            System.out.println("Invalid command handling failed.");
        }
    }

    @Test
    public void testPipeWithRedirection() {
        // This test simulates a command with redirection: echo "Hello World" > myfile.txt
        String filePath = "myfile.txt";
        String[] commands = {"echo", "Hello World", ">", filePath};

        // Execute the piped command
        pipeCommand.execute(commands);
        
        // Check if the file was created
        File file = new File(filePath);
        if (file.exists()) {
            System.out.println("File was created successfully.");
            // Optionally read the file and check its content
            // Add logic here to read the file if needed
            System.out.println("File content length: " + file.length());
        } else {
            System.out.println("File was not created.");
        }
        
        // Clean up the created file
        file.delete();
    }

    @Before
    public void tearDown() {
        System.setOut(originalOut);  // Reset System.out to its original
    }
}
