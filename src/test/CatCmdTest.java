package test;

import static org.junit.Assert.assertTrue;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import main.commands.CatCommand;
import main.fileSystem.*;

public class CatCmdTest {

    FileSystem fileSystem = FileSystem.getInstance();
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @After
    public void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    public void testCatCommandSingleFile() throws Exception {
        CatCommand catCommand = new CatCommand();
        String testFile = "testfile.txt";
        createTestFile(testFile, "Hello, World!");

        catCommand.execute(new String[]{testFile});
        assertTrue(outContent.toString().contains("Hello, World!"));

        deleteTestFile(testFile);
    }

    @Test
    public void testCatCommandMultipleFiles() throws Exception {
        CatCommand catCommand = new CatCommand();
        String testFile1 = "testfile1.txt";
        String testFile2 = "testfile2.txt";
        createTestFile(testFile1, "First File");
        createTestFile(testFile2, "Second File");

        catCommand.execute(new String[]{testFile1, testFile2});
        assertTrue(outContent.toString().contains("First File"));
        assertTrue(outContent.toString().contains("Second File"));

        deleteTestFile(testFile1);
        deleteTestFile(testFile2);
    }

    private void createTestFile(String fileName, String content) throws Exception {
        File file = new File(fileSystem.getCurrentDirectory(),fileName);
        try (PrintStream out = new PrintStream(file)) {
            out.print(content);
        }
    }

    private void deleteTestFile(String fileName) {
        File file = new File(fileSystem.getCurrentDirectory(),fileName);
        file.delete();
    }
}