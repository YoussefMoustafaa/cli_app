package test;

import main.commands.LsCommand;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class LsCmdTest {
    private LsCommand lsCommand;
    private final String testDirName = "testDir";
    private final String subDirName = "testDir/subDir";
    private final String hiddenFileName = ".hiddenFile";
    private final String visibleFileName = "visibleFile.txt";
    private ByteArrayOutputStream outContent;

    @Before
    public void setUp() throws IOException {
        lsCommand = new LsCommand();
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        // Create test directory and files
        new File(testDirName).mkdir();
        new File(subDirName).mkdir();

        // Create visible and hidden files
        new File(testDirName, hiddenFileName).createNewFile();
        new File(testDirName, visibleFileName).createNewFile();
    }

    @Test
    public void testListVisibleFilesOnly() {
        String[] args = {testDirName}; // No "-a" flag, so hidden files should not be listed
        lsCommand.execute(args);

        String output = outContent.toString();
    
        // Check that the output contains the visible file and does not contain the hidden file
        assertTrue("Output should contain the visible file.", output.contains("file: visibleFile.txt"));
        assertFalse("Output should not contain the hidden file.", output.contains("file: .hiddenFile"));
}


    @Test
    public void testListAllFilesWithAFlag() {
        String[] args = {testDirName, "-a"};
        lsCommand.execute(args);

        String output = outContent.toString();
        assertTrue("Output should contain the visible file.", output.contains("file: visibleFile.txt"));
        assertTrue("Output should contain the hidden file.", output.contains("file: .hiddenFile"));
    }

    @Test
    public void testReverseOrderWithRFlag() {
        String[] args = {testDirName, "-r"};
        lsCommand.execute(args);

        String output = outContent.toString();
        int visibleIndex = output.indexOf("file: visibleFile.txt");
        int hiddenIndex = output.indexOf("file: .hiddenFile");

        assertTrue("The visible file should be listed after the hidden file when reversed.", visibleIndex > hiddenIndex);
    }
    /*@Test
    public void testReverseOrderWithRFlag() {
        String[] args = {testDirName, "-r"};
        lsCommand.execute(args);

        String output = outContent.toString().trim();
        String[] lines = output.split("\\R"); // Split by line breaks

        // Ensure that we have at least two lines in the output
        assertTrue("Output should contain at least two files.", lines.length >= 2);

        // Check that the last two lines are in the expected reversed order
        assertEquals("file: .hiddenFile", lines[0]);
        assertEquals("file: visibleFile.txt", lines[1]);
    }*/

    @Test
    public void testNonExistentDirectory() {
        String[] args = {"nonExistentDir"};
        lsCommand.execute(args);

        String output = outContent.toString();
        assertTrue("Output should indicate that the directory does not exist.", output.contains("Directory does not exist"));
    }

    @After
    public void tearDown() {
        System.setOut(System.out);
        deleteDirectory(new File(testDirName));
    }

    private void deleteDirectory(File dir) {
        if (dir.exists()) {
            File[] files = dir.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isDirectory()) {
                        deleteDirectory(file);
                    } else {
                        file.delete();
                    }
                }
            }
            dir.delete();
        }
    }
}
