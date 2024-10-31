package test;

import main.commands.TouchCommand;
import org.junit.Before;
import org.junit.Test;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import static org.junit.Assert.*;

public class TouchCmdTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private TouchCommand touchCommand;

    @Before
    public void setUp() {
        System.setOut(new PrintStream(outContent));
        touchCommand = new TouchCommand();
    }

    @Test
    public void testCreateNewFile() {
        String fileName = "testFile.txt";
        File testFile = new File(fileName);

        // Ensure the file does not exist before testing
        if (testFile.exists()) {
            testFile.delete();
        }

        touchCommand.execute(new String[]{fileName});
        
        assertTrue("File should be created.", testFile.exists());
        assertTrue(outContent.toString().contains("File created: " + fileName));
        
        // Clean up
        testFile.delete();
    }

    @Test
    public void testFileAlreadyExists() {
        String fileName = "existingFile.txt";
        File existingFile = new File(fileName);

        try {
            existingFile.createNewFile(); // Create file if it doesn't exist
        } catch (Exception e) {
            fail("Setup failed: Unable to create the test file.");
        }

        touchCommand.execute(new String[]{fileName});
        
        assertTrue("File should still exist.", existingFile.exists());
        assertTrue(outContent.toString().contains("File already exists."));
        
        // Clean up
        existingFile.delete();
    }

    @Test
    public void testMultipleFileCreation() {
        String[] fileNames = {"file1.txt", "file2.txt", "file3.txt"};
        File[] files = new File[fileNames.length];

        for (int i = 0; i < fileNames.length; i++) {
            files[i] = new File(fileNames[i]);
            if (files[i].exists()) {
                files[i].delete();
            }
        }

        touchCommand.execute(fileNames);
        
        for (File file : files) {
            assertTrue("Each file should be created.", file.exists());
            file.delete(); // Clean up
        }
        
        String output = outContent.toString();
        assertTrue(output.contains("File created: file1.txt"));
        assertTrue(output.contains("File created: file2.txt"));
        assertTrue(output.contains("File created: file3.txt"));
    }

    @Test
    public void testInvalidFileNameHandling() {
        String invalidFileName = "/invalid/file/name.txt";
        
        touchCommand.execute(new String[]{invalidFileName});
        
        assertTrue("Should catch and print an error.", outContent.toString().contains("Error:"));
    }
}
