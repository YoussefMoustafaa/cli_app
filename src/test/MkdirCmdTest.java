package test;

import static org.junit.Assert.assertTrue;
import java.io.File;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import main.commands.MkdirCommand;
import main.fileSystem.*;

public class MkdirCmdTest {

    FileSystem fileSystem = FileSystem.getInstance();

    @Before
    @After
    public void cleanUp() {
        // Clean up any test directories
        deleteTestDir("testdir1");
        deleteTestDir("testdir2");
    }

    @Test
    public void testMkdirSingleDirectory() {
        MkdirCommand mkdirCommand = new MkdirCommand();
        String[] args = {"testdir1"};
        
        mkdirCommand.execute(args);
        
        assertTrue(new File(fileSystem.getCurrentDirectory() + "/testdir1").exists());
    }

    @Test
    public void testMkdirMultipleDirectories() {
        MkdirCommand mkdirCommand = new MkdirCommand();
        String[] args = {"testdir1", "testdir2"};
        
        mkdirCommand.execute(args);
        
        assertTrue(new File(fileSystem.getCurrentDirectory() + "/testdir1").exists());
        assertTrue(new File(fileSystem.getCurrentDirectory() + "/testdir2").exists());
    }

    private void deleteTestDir(String dirName) {
        File dir = new File(dirName);
        if (dir.exists()) {
            dir.delete();
        }
    }
}