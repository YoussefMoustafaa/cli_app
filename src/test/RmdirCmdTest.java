package test;

import main.commands.RmdirCommand;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.io.File;
import java.io.IOException;
import static org.junit.Assert.assertTrue;

public class RmdirCmdTest {
    private RmdirCommand rmdirCommand;
    private final String testDirName = "testDir";
    private final String nonEmptyDirName = "nonEmptyDir";

    @Before
    public void setUp() {
        rmdirCommand = new RmdirCommand();
        
        // Ensure test directories are set up correctly before each test
        new File(testDirName).mkdir();  // Create an empty test directory

        // Create a non-empty directory
        File nonEmptyDir = new File(nonEmptyDirName);
        nonEmptyDir.mkdir();
        try {
            new File(nonEmptyDir, "file.txt").createNewFile(); // Add a file to make it non-empty
        } catch (IOException e) {
            System.out.println("Setup Error: Could not create a file in nonEmptyDir.");
        }
    }

    @Test
    public void testDeleteExistingDirectory() {
        String[] args = {testDirName};
        rmdirCommand.execute(args);

        assertTrue("Directory should be deleted successfully.", !new File(testDirName).exists());
    }

    @Test
    public void testDeleteNonEmptyDirectory() {
        String[] args = {nonEmptyDirName};
        rmdirCommand.execute(args);

        assertTrue("Non-empty directory should not be deleted.", new File(nonEmptyDirName).exists());
    }

    @Test
    public void testDeleteNonExistentDirectory() {
        String[] args = {"nonExistentDir"};
        rmdirCommand.execute(args);

        // Expectation: the command should handle this gracefully without error
        assertTrue("Non-existent directory should not exist.", !new File("nonExistentDir").exists());
    }

    @After
    public void cleanupTestDirectories() {
        // Clean up both directories after tests run
        deleteDirectory(new File(testDirName));
        deleteDirectory(new File(nonEmptyDirName));
    }

    private void deleteDirectory(File dir) {
        if (dir.exists()) {
            File[] files = dir.listFiles();
            if (files != null) {
                for (File file : files) {
                    file.delete();
                }
            }
            dir.delete();
        }
    }
}
