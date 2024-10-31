package test;
import main.commands.RmCommand;
import main.fileSystem.FileSystem;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.io.File;
import java.io.IOException;

import static org.junit.Assert.*;

public class RmCmdTest {
    private RmCommand rmCommand;
    private FileSystem fileSystem;
    private File testDir;
    
    @Before
    public void setUp() throws IOException {
        rmCommand = new RmCommand();
        fileSystem = FileSystem.getInstance();
        
        // Set up a temporary test directory and files
        testDir = new File("testDir");
        testDir.mkdir();
        fileSystem.setCurrentDirectory(testDir);
        
        // Create some test files
        new File(testDir, "file1.txt").createNewFile();
        new File(testDir, "file2.txt").createNewFile();
        new File(testDir, "dir1").mkdir(); // A directory for testing non-file cases
    }
    
    @After
    public void tearDown() {
        // Clean up by deleting the test directory and contents
        for (File file : testDir.listFiles()) {
            file.delete();
        }
        testDir.delete();
    }

    @Test
    public void testRemoveExistingFile() {
        rmCommand.execute(new String[]{"file1.txt"});
        assertFalse("File1 should be deleted.", new File(testDir, "file1.txt").exists());
    }

    @Test
    public void testRemoveNonExistingFile() {
        rmCommand.execute(new String[]{"nonexistent.txt"});
        assertTrue("File2 should still exist.", new File(testDir, "file2.txt").exists());
    }

    @Test
    public void testRemoveDirectory() {
        rmCommand.execute(new String[]{"dir1"});
        assertTrue("Directory should not be deleted by RmCommand.", new File(testDir, "dir1").exists());
    }

    @Test
    public void testMissingOperand() {
        // Capture system output to check for correct message
        rmCommand.execute(new String[]{});
    }
}