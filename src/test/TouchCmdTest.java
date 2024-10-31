package test;

import static org.junit.Assert.assertTrue;
import java.io.File;
import java.nio.file.Paths;
// import path
import java.nio.file.Path;
import main.fileSystem.FileSystem;

import org.junit.Before;
import org.junit.Test;

import main.commands.TouchCommand;

public class TouchCmdTest {

    private FileSystem fileSystem;
    private TouchCommand touchCommand;
    private String testFileName;

    @Before
    public void setup() {
        fileSystem = FileSystem.getInstance();
        String pathString = System.getProperty("java.io.tmpdir") + "/testDir";
        Path path = Paths.get(pathString);
        touchCommand = new TouchCommand();
        testFileName = "testFile.txt";

        // Ensure test directory is clean
        File testDir = new File(fileSystem.getCurrentDirectory().toString());
        if (!testDir.exists()) {
            testDir.mkdir();
        } else {
            for (File file : testDir.listFiles()) {
                file.delete();
            }
        }
    }

    @Test
    public void testCreateFile() {
        File testFile = new File(testFileName);

        if (testFile.exists()) {
            testFile.delete();
        }

        touchCommand.execute(new String[]{testFileName});

        assertTrue("File should have been created.", testFile.exists());

        testFile.delete();
    }

    @Test
    public void testFileAlreadyExists() {

        String filename = "existingFile.txt";

        // First Creation
        touchCommand.execute(new String[]{filename});
        File existingFile = new File(fileSystem.getCurrentDirectory(), filename);
        assertTrue("File should be created initially.", existingFile.exists());

        touchCommand.execute(new String[]{filename});
        assertTrue("File should still exist without issues after re-running touch command.", existingFile.exists());
    }
}
