package test;

import main.commands.AppendCommand;
import main.fileSystem.FileSystem;
import org.junit.Before;
import org.junit.Test;

import java.io.*;

public class AppendCmdTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private AppendCommand AppendCommand;
    private FileSystem fileSystem;

    @Before
    public void setUp() {
        System.setOut(new PrintStream(outContent));
        AppendCommand = new AppendCommand();
        fileSystem = FileSystem.getInstance();

        // Set the current directory to a temporary test directory
        String tempDir = System.getProperty("java.io.tmpdir");
        fileSystem.setCurrentDirectory(new File(tempDir));
    }

    @Test
    public void testWriteToFile() {
        String filename = "testfile.txt";
        String[] args = {filename, "Hello World"};

        // Execute the write command
        AppendCommand.execute(args);

        // Verify the file was created and check its content
        File file = new File(fileSystem.getCurrentDirectory(), filename);
        if (file.exists()) {
            System.out.println("File created successfully: " + filename);
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String content = reader.readLine();
                System.out.println("Content of the file: " + content);
            } catch (IOException e) {
                System.out.println("Error reading the file: " + e.getMessage());
            }
        } else {
            System.out.println("File was not created.");
        }

        // Clean up the created file
        file.delete();
    }

    @Test
    public void testNoArguments() {
        // Execute the write command without any arguments
        AppendCommand.execute(new String[]{});

        // Check the output for the error message
        String output = outContent.toString();
        System.out.println("Output of no arguments test: " + output);
        if (output.contains("Error: '>' command requires a filename and text to write.")) {
            System.out.println("Correct error message displayed for no arguments.");
        } else {
            System.out.println("Error message for no arguments not displayed.");
        }
    }

    @Test
    public void testWriteToFileWithEmptyContent() {
        String filename = "emptyfile.txt";
        String[] args = {filename};

        // Execute the write command
        AppendCommand.execute(args);

        // Verify the file was created and check its content
        File file = new File(fileSystem.getCurrentDirectory(), filename);
        if (file.exists()) {
            System.out.println("File created successfully: " + filename);
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String content = reader.readLine();
                System.out.println("Content of the file: '" + content + "'");
                // The content should be empty
                if (content == null || content.isEmpty()) {
                    System.out.println("File is empty as expected.");
                } else {
                    System.out.println("File should be empty, but it has content: " + content);
                }
            } catch (IOException e) {
                System.out.println("Error reading the file: " + e.getMessage());
            }
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