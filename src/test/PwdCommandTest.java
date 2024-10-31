package test;

import main.commands.PwdCommand;
import main.fileSystem.FileSystem;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;

public class PwdCommandTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private PwdCommand pwdCommand;
    private FileSystem fileSystem;

    @Before
    public void setUp() {
        System.setOut(new PrintStream(outContent));
        pwdCommand = new PwdCommand();
        fileSystem = FileSystem.getInstance();

        // Set the current directory to a temporary test directory
        String tempDir = System.getProperty("java.io.tmpdir");
        fileSystem.setCurrentDirectory(new File(tempDir));
    }

    @Test
    public void testPrintWorkingDirectory() {
        // Execute the PwdCommand
        pwdCommand.execute(new String[]{});

        // Verify the output
        String expectedOutput = fileSystem.getCurrentDirectory().toString() + System.lineSeparator();
        String output = outContent.toString();
        System.out.println("Current Directory Output: " + output);
        
        if (output.equals(expectedOutput)) {
            System.out.println("Correct current directory printed.");
        } else {
            System.out.println("Output does not match the expected current directory.");
        }
    }

    @Before
    public void tearDown() {
        System.setOut(originalOut);  // Reset System.out to its original
    }
}
