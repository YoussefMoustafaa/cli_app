package test;

import main.commands.LsCommand;
import main.fileSystem.FileSystem;
import org.junit.Before;
import org.junit.Test;
import java.io.IOException;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;

public class LsCommandTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private LsCommand lsCommand;
    private FileSystem fileSystem;

    @Before
    public void setUp() {
        System.setOut(new PrintStream(outContent));
        lsCommand = new LsCommand();
        fileSystem = FileSystem.getInstance();

        // Set the current directory to a temporary test directory
        String tempDir = System.getProperty("java.io.tmpdir");
        fileSystem.setCurrentDirectory(new File(tempDir));
    }

    @Test
    public void testListFilesWithShowAllFlag() {
        String newDir = "testDir";
        File dir = new File(fileSystem.getCurrentDirectory(), newDir);
        dir.mkdir(); // Create a test directory

        // Create a hidden file inside the new directory
        File hiddenFile = new File(dir, ".hiddenFile");
        try {
            hiddenFile.createNewFile(); // Create a hidden file
        } catch (IOException e) {
            System.out.println("Error creating hidden file: " + e.getMessage());
        }

        // Execute command with show all flag
        lsCommand.execute(new String[]{"-a", newDir});

        // Check output
        String output = outContent.toString();
        if (!output.contains("hiddenFile") || !output.contains("directory: testDir")) {
            System.out.println("Test failed: Output does not contain expected files.");
        }

        // Clean up
        hiddenFile.delete(); // Delete hidden file
        dir.delete(); // Delete test directory
    }

    @Test
    public void testListFilesInEmptyDirectory() {
        String newDir = "emptyDir";
        File dir = new File(fileSystem.getCurrentDirectory(), newDir);
        dir.mkdir(); // Create a test directory

        // Execute command
        lsCommand.execute(new String[]{newDir});

        // Check output
        String output = outContent.toString();
        if (!output.contains("The directory is empty.")) {
            System.out.println("Test failed: Expected 'The directory is empty.' message.");
        }

        // Clean up
        dir.delete(); // Delete test directory
    }

    @Test
    public void testListNonExistentDirectory() {
        lsCommand.execute(new String[]{"nonExistentDir"});

        // Check output
        String output = outContent.toString();
        if (!output.contains("Directory does not exist: nonExistentDir")) {
            System.out.println("Test failed: Expected 'Directory does not exist' message.");
        }
    }

    @Before
    public void tearDown() {
        System.setOut(originalOut);  // Reset System.out to its original
    }
}
