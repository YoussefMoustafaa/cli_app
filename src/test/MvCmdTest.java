package test;

import main.commands.MvCommand;
import org.junit.Before;
import org.junit.Test;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.*;

public class MvCmdTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private MvCommand mvCommand;

    @Before
    public void setUp() {
        System.setOut(new PrintStream(outContent));
        mvCommand = new MvCommand();
    }

    @Test
    public void testMoveSingleFileToDirectory() throws IOException {
        File sourceFile = new File("testFile.txt");
        File targetDir = new File("targetDir");
        
        // Setup: create source file and target directory
        sourceFile.createNewFile();
        targetDir.mkdir();
        
        mvCommand.execute(new String[]{sourceFile.getName(), targetDir.getName()});
        
        Path movedFilePath = Paths.get(targetDir.getName(), sourceFile.getName());
        assertTrue("File should be moved to the target directory.", Files.exists(movedFilePath));
        assertFalse("Source file should no longer exist.", sourceFile.exists());
        assertTrue(outContent.toString().contains("Moved"));

        // Clean up
        Files.deleteIfExists(movedFilePath);
        targetDir.delete();
    }

    @Test
    public void testRenameFile() throws IOException {
        File sourceFile = new File("testFile.txt");
        File renamedFile = new File("renamedFile.txt");
        
        // Setup: create the source file
        sourceFile.createNewFile();

        mvCommand.execute(new String[]{sourceFile.getName(), renamedFile.getName()});
        
        assertTrue("File should be renamed.", renamedFile.exists());
        assertFalse("Original file should no longer exist.", sourceFile.exists());
        assertTrue(outContent.toString().contains("Renamed"));

        // Clean up
        renamedFile.delete();
    }

    @Test
    public void testMoveMultipleFilesToDirectory() throws IOException {
        File file1 = new File("file1.txt");
        File file2 = new File("file2.txt");
        File targetDir = new File("multiTargetDir");
        
        // Setup: create files and target directory
        file1.createNewFile();
        file2.createNewFile();
        targetDir.mkdir();

        mvCommand.execute(new String[]{file1.getName(), file2.getName(), targetDir.getName()});
        
        Path movedFile1Path = Paths.get(targetDir.getName(), file1.getName());
        Path movedFile2Path = Paths.get(targetDir.getName(), file2.getName());
        
        assertTrue("First file should be moved.", Files.exists(movedFile1Path));
        assertTrue("Second file should be moved.", Files.exists(movedFile2Path));
        assertFalse("Source file1 should no longer exist.", file1.exists());
        assertFalse("Source file2 should no longer exist.", file2.exists());

        // Clean up
        Files.deleteIfExists(movedFile1Path);
        Files.deleteIfExists(movedFile2Path);
        targetDir.delete();
    }

    @Test
    public void testSourceFileDoesNotExist() {
        String nonExistentFile = "nonExistentFile.txt";
        File targetDir = new File("targetDir");
        
        // Setup: create the target directory only
        targetDir.mkdir();

        mvCommand.execute(new String[]{nonExistentFile, targetDir.getName()});
        
        assertTrue(outContent.toString().contains("Source file \"" + nonExistentFile + "\" does not exist"));

        // Clean up
        targetDir.delete();
    }

    @Test
    public void testDestinationNotDirectoryForMultipleFiles() throws IOException {
        File file1 = new File("file1.txt");
        File file2 = new File("file2.txt");
        File notADirectory = new File("notADirectory.txt");

        // Setup: create files and a non-directory file
        file1.createNewFile();
        file2.createNewFile();
        notADirectory.createNewFile();

        mvCommand.execute(new String[]{file1.getName(), file2.getName(), notADirectory.getName()});
        
        assertTrue(outContent.toString().contains("Error: target " + notADirectory.getName() + " is not a directory."));

        // Clean up
        file1.delete();
        file2.delete();
        notADirectory.delete();
    }
}
