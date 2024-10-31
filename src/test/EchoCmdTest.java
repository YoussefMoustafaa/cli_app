package test;

import main.commands.EchoCommand;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class EchoCmdTest {
    private EchoCommand echoCommand;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @Before
    public void setUp() {
        echoCommand = new EchoCommand();
        System.setOut(new PrintStream(outContent)); // Redirect System.out to capture output
    }

    @After
    public void tearDown() {
        System.setOut(originalOut); // Reset System.out to its original
        System.out.println("Captured Output: " + outContent.toString()); // Print captured output after each test
        outContent.reset(); // Clear the output stream for the next test
    }

    @Test
    public void testNoArguments() {
        echoCommand.execute(new String[]{}); // No arguments provided
    }

    @Test
    public void testSingleArgument() {
        echoCommand.execute(new String[]{"Hello"}); // Single word
    }

    @Test
    public void testMultipleArguments() {
        echoCommand.execute(new String[]{"Hello", "world!"}); // Multiple words
    }

    @Test
    public void testChainedCommand() {
        echoCommand.execute(new String[]{"Hello", "|", "someChainedCommand"}); // Contains a chained command
    }
}