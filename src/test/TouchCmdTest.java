package test;

import static org.junit.Assert.assertTrue;
import java.io.File;
import java.nio.file.Paths;

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
        // Path path = Paths.get(System.getProperty("java.io.tmpdir") + "/testDir");
        // fileSystem.setCurrentDirectory(System.getProperty("java.io.tmpdir") + "/testDir");
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

        touchCommand.execute(new String[]{filename});
        // File existingFile = new File()
    }
}
