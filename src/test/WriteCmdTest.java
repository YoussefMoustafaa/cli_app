package test;
import main.commands.WriteCommand;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

public class WriteCmdTest {
    private WriteCommand writeCommand;
    private final String testFilename = "testFile.txt";

    @Before
    public void setUp() {
        writeCommand = new WriteCommand();
    }

    @Test
    public void testBasicWriteToFile() {
        String[] args = {testFilename, "Hello", "World"};
        writeCommand.execute(args);

        assertFileContent("Hello World");
    }

    @Test
    public void testWriteWithInput() {
        writeCommand.setInput("Input line");
        String[] args = {testFilename, "New", "Content"};
        writeCommand.execute(args);

        assertFileContent("Input line\nNew Content");
    }

    @Test
    public void testNoFilenameProvided() {
        String[] args = {};
        writeCommand.execute(args);
        
        // Here we can check for an error message in the console output if needed
    }

    private void assertFileContent(String expectedContent) {
        try (BufferedReader reader = new BufferedReader(new FileReader(testFilename))) {
            StringBuilder content = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
            assertTrue("File content does not match expected content.",
                    expectedContent.trim().equals(content.toString().trim()));
        } catch (IOException e) {
            assertTrue("IOException occurred: " + e.getMessage(), false);
        } finally {
            new File(testFilename).delete(); // Clean up the test file after each test
        }
    }
}
