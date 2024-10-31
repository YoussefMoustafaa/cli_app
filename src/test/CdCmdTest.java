package test;

import main.commands.CdCommand;
import main.fileSystem.FileSystem;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.nio.file.Path;

import static org.junit.Assert.assertEquals;

public class CdCmdTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private CdCommand cdCommand;
    private FileSystem fileSystem;

    @Before
    public void setUp() {
        System.setOut(new PrintStream(outContent));
        cdCommand = new CdCommand();
        fileSystem = FileSystem.getInstance();

        // Set the current directory to a temporary test directory
        String tempDir = System.getProperty("java.io.tmpdir");
        fileSystem.setCurrentDirectory(new File(tempDir));
    }

    @Test
    public void testChangeToHomeDirectory() {
        cdCommand.execute(new String[]{"~"});
        
        Path homePath = Path.of(System.getProperty("user.home"));
        assertEquals(homePath, fileSystem.getCurrentDirectory().toPath());
    }

    /*@Test
    public void testChangeToExistingDirectory() {
        String newDir = "testDir";
        new File(fileSystem.getCurrentDirectory(), newDir).mkdir(); // Create a test directory

        cdCommand.execute(new String[]{newDir});
        
        assertEquals(new File(fileSystem.getCurrentDirectory(), newDir).getAbsolutePath(), fileSystem.getCurrentDirectory().getAbsolutePath());
        
        // Clean up
        new File(fileSystem.getCurrentDirectory(), newDir).delete();
    }*/
    @Test
    public void testChangeToExistingDirectory() {
        String newDir = "testDir";
        File newDirectory = new File(fileSystem.getCurrentDirectory(), newDir);
        newDirectory.mkdir(); // Create a test directory

        cdCommand.execute(new String[]{newDir});
    
        // Check that the current directory is now the new directory
        assertEquals(newDirectory.toPath(), fileSystem.getCurrentDirectory().toPath());
    
        // Clean up
        newDirectory.delete();
    }


    @Test
    public void testChangeToNonExistingDirectory() {
        String nonExistingDir = "nonExistingDir";
        
        cdCommand.execute(new String[]{nonExistingDir});
        
        assertEquals(Path.of(System.getProperty("java.io.tmpdir")), fileSystem.getCurrentDirectory().toPath());
    }

    @Test
    public void testChangeToParentDirectory() {
        String newDir = "testDir";
        new File(fileSystem.getCurrentDirectory(), newDir).mkdir(); // Create a test directory
        cdCommand.execute(new String[]{newDir}); // Change to testDir

        cdCommand.execute(new String[]{".."}); // Go back to the parent directory
        
        assertEquals(Path.of(System.getProperty("java.io.tmpdir")), fileSystem.getCurrentDirectory().toPath());
        
        // Clean up
        new File(fileSystem.getCurrentDirectory(), newDir).delete();
    }

    @Test
    public void testTooManyArguments() {
        cdCommand.execute(new String[]{"dir1", "dir2"});
        
        // Add an internal check to verify current directory remains unchanged
        assertEquals(Path.of(System.getProperty("java.io.tmpdir")), fileSystem.getCurrentDirectory().toPath());
    }

    @Before
    public void tearDown() {
        System.setOut(originalOut);  // Reset System.out to its original
    }
}
